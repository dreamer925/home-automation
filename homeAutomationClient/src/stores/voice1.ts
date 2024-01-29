// import { defineStore } from 'pinia';
// import { useAuthStore } from '@/stores/auth';
// import { useErrorStore } from '@/stores/error';

// interface VoiceResponse {
//   intent: string;
//   location?: string;
//   state?: string;
//   color?: string;
//   device?: string;
//   open1?: string;
//   time1?: string;
//   open2?: string;
//   time2?: string;
// }

// export const useVoiceStore = defineStore({
//     id: 'voice',
//     state: () => {
//         return {
//             token: useAuthStore().token,
//             stat: 'Click to give a voice command' as string,
//             mediaRecorder: null as MediaRecorder | null,
//             chunks: [] as Blob[],
//             res: {} as VoiceResponse,
//             command: ''
//         }
//     },
//     actions: {
//       async startRecording() {
//         this.stat = 'Recording...';
//         navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
//           this.mediaRecorder = new MediaRecorder(stream);
//           this.command = 'start';
//           this.mediaRecorder.addEventListener('dataavailable', (event) => {
//             this.chunks.push(event.data);
//           });
    
//           this.mediaRecorder.addEventListener('stop', async () => {
//             this.stat = 'Processing...';

//             const blob = new Blob(this.chunks, { type: 'audio/wav' });
//             this.chunks = [];
//             const formData = new FormData();
//             formData.append('audio', blob, 'audio.wav');
//             formData.append('Content-Type', 'audio/wav');
//             console.log(formData);
    
//             fetch('http://localhost:8080/api/voice', {
//               method: 'POST',
//               headers: {
//                 Authorization: `Bearer ${this.token}`,
//               },
//               body: formData,
//             })
//               .then(async (response) => {
//                 if (response.status === 200){
//                   const res1 = await response.json();
//                 const res: VoiceResponse = {
//                   intent: res1.intent,
//                   location: res1.location,
//                   state: res1.state,
//                   color: res1.color,
//                   device: res1.device,
//                   open1: res1.open1,
//                   time1: res1.time1,
//                   open2: res1.open2,
//                   time2: res1.time2,
//                 };
//                 this.res = res;
//                 // this.res = res1.data;
//                 console.log(res1);
//                 console.log(this.res);
//                 this.command = 'ready';
//                 console.log(this.command);
//                 } else {
//                   const res  = await response.json();
//                   const errorStore = useErrorStore();
//                   const errorResponse = res.data; // Assuming the error response is returned as an object containing status and message fields
//                   const errorMessage = errorResponse.message;
//                   errorStore.setError(errorMessage);
//                 }
//               })
//               .then((result) => {
//                 console.log(result);
//                 this.stat = 'Click to give a voice command';
//               })
//               .catch((error) => {
//                 console.error(error);
//                 this.stat = 'Error';
//               });
//           });
//           this.mediaRecorder.start();
//           setTimeout(() => {
//             if (this.mediaRecorder !== null) {
//               this.mediaRecorder.stop();
//             }
//           }, 5000);
//         });
//       },
//       stopRecording() {
//         if (this.mediaRecorder !== null) {
//             this.mediaRecorder.stop();
//           }
//         //   this.stat = 'Processing...';

//         //   const blob = new Blob(this.chunks, { type: 'audio/wav' });
//         //   this.chunks = [];
//         //   const formData = new FormData();
//         //   formData.append('audio', blob, 'audio.wav');
//         //   formData.append('Content-Type', 'audio/wav');
//         //   console.log(formData);
  
//         //   fetch('http://localhost:8080/api/voice', {
//         //     method: 'POST',
//         //     headers: {
//         //       Authorization: `Bearer ${this.token}`,
//         //     },
//         //     body: formData,
//         // })
//         //     .then(async (response) => {
//         //       this.command = 'ready';
//         //       this.res = await response.json();
//         //     })
//         //     .then((result) => {
//         //       console.log(result);
//         //       this.stat = 'Click to give a voice command';
//         //     })
//         //     .catch((error) => {
//         //     console.error(error);
//         //     this.stat = 'Error';
//         //     });
//       },
//     },
// });