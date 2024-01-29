import { defineStore } from 'pinia';
import { useAuthStore } from '@/stores/auth';
import { useErrorStore } from '@/stores/error';

export const useVoiceStore = defineStore({
    id: 'voice',
    state: () => {
        return {
            token: useAuthStore().token,
            stat: 'Click to give a voice command' as string,
            mediaRecorder: null as MediaRecorder | null,
            chunks: [] as Blob[],
            res: '' as string,
            command: '',
            updated: ''
        }
    },
    actions: {
      async startRecording() {
        this.stat = 'Recording...';
        navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
          this.mediaRecorder = new MediaRecorder(stream);
          this.command = 'start';
          this.mediaRecorder.addEventListener('dataavailable', (event) => {
            this.chunks.push(event.data);
          });
    
          this.mediaRecorder.addEventListener('stop', async () => {
            this.stat = 'Processing...';

            const blob = new Blob(this.chunks, { type: 'audio/wav' });
            this.chunks = [];
            const formData = new FormData();
            formData.append('audio', blob, 'audio.wav');
            formData.append('Content-Type', 'audio/wav');
            console.log(formData);
            this.updated = '';
    
            fetch('http://localhost:8080/api/voice', {
              method: 'POST',
              headers: {
                Authorization: `Bearer ${this.token}`,
              },
              body: formData,
            })
              .then(async (response) => {
                if (response.status === 200){
                  const res1 = await response.text();
                  console.log(res1);
                  this.updated = res1;

                  this.command = 'ready';
                  console.log(this.command);
                } else {
                    const errorMessage = await response.text();
                    console.log(errorMessage);
                    
                    const errorStore = useErrorStore();
                    errorStore.setError(errorMessage);
                }
              })
              .then((result) => {
                console.log(result);
                this.stat = 'Click to give a voice command';
              })
              .catch((error) => {
                console.error(error);
                this.stat = 'Error';
              });
          });
          this.mediaRecorder.start();
          setTimeout(() => {
            if (this.mediaRecorder !== null) {
              this.mediaRecorder.stop();
            }
          }, 10000);
        });
      },
      stopRecording() {
        if (this.mediaRecorder !== null) {
            this.mediaRecorder.stop();
          }
      },
    },
});