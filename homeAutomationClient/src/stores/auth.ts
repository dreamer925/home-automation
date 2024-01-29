import { defineStore } from 'pinia';
import router from '@/router';
import { useErrorStore } from '@/stores/error';

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => {
    return {
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')!) : null,
      token: localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token')!) : null,
      name: localStorage.getItem('name') ? JSON.parse(localStorage.getItem('name')!) : null,

      returnUrl: '/home/dashboard'
    }
  },
  actions: {
    async login(username: string, password: string) {
      try{
        const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
      });

      if (response.status === 200) {

        const res  = await response.json();

        const token = await res.token;
        const name = await res.name;

        localStorage.setItem('user', JSON.stringify(username));
        localStorage.setItem('token', JSON.stringify(token));
        localStorage.setItem('name', JSON.stringify(name));

        this.user = username;
        this.token = token;
        this.name = name;

        router.push(this.returnUrl || '/');
      } else {
        const errorMessage = await response.text();
        console.log(errorMessage);

        const errorStore = useErrorStore();
        errorStore.setError(errorMessage);
        // throw new Error('Invalid credentials');
      }
      } catch (error) {
        console.log("Error")
        console.log((error as Error))
        if ((error as Error).message) {
          console.log((error as Error).message);
        }
      }
    },
    async signup(name: string, email: string, password: string) {
      console.log(JSON.stringify({ name, email, password }));
        const response = await fetch('http://localhost:8080/api/auth/signup', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ name, email, password })
        });
  
        if (response.status === 200) {
  
          router.push('/signup/confirmRegistration');

        } else {
          const errorMessage = await response.text();
          console.log(errorMessage);
          
          const errorStore = useErrorStore();
          errorStore.setError(errorMessage);
        }
  
      },
    logout() {
      this.user = '';
      this.token = '';
      this.name = '';

      localStorage.removeItem('user');
      localStorage.removeItem('token');
      localStorage.removeItem('name');

      router.push('/login');
    }
  }
});