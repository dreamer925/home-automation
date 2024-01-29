import { defineStore } from 'pinia';
  
  export const useErrorStore = defineStore({
    id: 'error',
    state: () => {
      return {
        error: null as string | null,
      };
    },
    actions: {
      setError(error: string) {
        console.log('errorStore')
        this.error = error;
        setTimeout(() => {
          this.clearError();
        }, 10000); // Duration
      },
      clearError() {
        this.error = null;
      },
    },
  });