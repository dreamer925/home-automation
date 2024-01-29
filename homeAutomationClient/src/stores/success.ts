import { defineStore } from 'pinia';
  
  export const useSuccessStore = defineStore({
    id: 'success',
    state: () => {
      return {
        message: null as string | null,
      };
    },
    actions: {
      setSuccessMessage(success: string) {
        console.log('successStore')
        this.message = success;
        setTimeout(() => {
          this.clearSuccessMessage();
        }, 10000); // Duration
      },
      clearSuccessMessage() {
        this.message = null;
      },
    },
  });