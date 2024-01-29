import { defineStore } from 'pinia';
import { useErrorStore } from '@/stores/error';
import { useVoiceStore } from '@/stores/voice';
import { useAuthStore } from './auth';

export const useAutomationStore = defineStore({
    id: 'automation',
    state: () => {
        return {
            eventSource: null as EventSource | null,
        }
    },
    actions: {
        createEventSource() {

          const token = useAuthStore().token;
          const url = `http://localhost:8080/api/appliance/sse-event?token=${encodeURIComponent(token)}`;

          this.eventSource = new EventSource(url);

            // this.eventSource = new EventSource('http://localhost:8080/api/appliance/sse-event');

            // this.eventSource.addEventListener('beforeOpen', () => {
            //   const xhr = (this.eventSource as any)._xhr as XMLHttpRequest;
            //   xhr.setRequestHeader('Authorization', `Bearer ${useAuthStore().token}`);
            // });

      
            this.eventSource.onmessage = (event: MessageEvent<any>) => {
              const message = event.data;
              console.log(message);
              if (message === 'Event completed') {
                useVoiceStore().updated = 'curtains'
              }
            };

            this.eventSource.addEventListener('error', (errorEvent: Event) => {
              console.error('SSE error:', errorEvent);
            });
      
            this.eventSource.onerror = (error) => {
              console.error('SSE error:', error);
            };
      
            (this.eventSource as any).onclose = () => {
              console.log('SSE connection closed.');
            };
          },
      
          closeEventSource() {
            if (this.eventSource) {
              this.eventSource.close();
              this.eventSource = null;
            }
          },
    },
});