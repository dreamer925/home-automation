import { defineStore } from 'pinia';
import { useAuthStore } from '@/stores/auth';
import { useErrorStore } from '@/stores/error';
import { useSuccessStore } from '@/stores/success';
import { HttpStatusCode } from 'axios'
import moment from 'moment';

interface Lights {
    id: number;  
    room: string;
    color: string;
    state: boolean;
}

interface Devices {
    id: number;  
    type: string;
    state: boolean;
}

interface Curtains {
    id: number;  
    openTime: string;
    closeTime: string;
    state: boolean;
}

export const useCrudStore = defineStore({
    id: 'crud',
    state: () => {
      return {
        content: false,
        error: '',
        lights: [] as Array<Lights>,
        devices: [] as Array<Devices>,
        curtains: {
            id: 1,  
            openTime: 'n',
            closeTime: '',
            state: false,
        } as Curtains,
      }
    },
    getters: {
      token() {
          const auth = useAuthStore();
          return auth.token
      }
    },
    actions: {
        async getLights() {
            const token = this.token;

            console.log('getLights');

            const response = await fetch('http://localhost:8080/api/appliance/lights', {
                // method: 'GET',
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (response.status === 200) {

                const res  = await response.json();

                this.lights = res;
                // const hasLights = true;

                console.log(res);
                
            } else if (response.status === HttpStatusCode.NoContent) {
                console.log('No lights')
                this.lights = [];
                // hasLights.value = false;
            } else {
                console.log('lights error');
                const errorMessage = await response.text();
                console.log(errorMessage);
                
                const errorStore = useErrorStore();
                errorStore.setError(errorMessage);
            }

        },
        async getDevices() {
            const token = this.token;

            console.log('getDevices');

            const response = await fetch('http://localhost:8080/api/appliance/devices', {
                // method: 'GET',
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (response.status === 200) {

                const res  = await response.json();

                this.devices = res;
                // const hasLights = true;

                console.log(res);
                
            } else if (response.status === HttpStatusCode.NoContent) {
                console.log('No devices')
                this.devices = [];
                // hasLights.value = false;
            } else {
                console.log('Devices error');
                const errorMessage = await response.text();
                console.log(errorMessage);
                
                const errorStore = useErrorStore();
                errorStore.setError(errorMessage);
            }

        },
        async getCurtains() {
            const token = this.token;

            console.log('getCurtains');

            const response = await fetch('http://localhost:8080/api/appliance/curtains', {
                // method: 'GET',
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (response.status === 200) {

                const res  = await response.json();

                this.curtains = res;

                if(this.curtains.openTime === null){
                    this.curtains.openTime = '';
                    this.curtains.closeTime = '';
                } else {
                  // 18:00:00
                  const open = this.curtains.openTime;
                  const close = this.curtains.closeTime;
                  this.curtains.openTime = moment(open, 'HH:mm:ss').format('h:mm A');
                  this.curtains.closeTime = moment(close, 'HH:mm:ss').format('h:mm A');
                }

                // const hasLights = true;

                console.log(res);
                
            } else if (response.status === HttpStatusCode.NoContent) {
                console.log('No curtains')
                this.curtains = {
                    id: 1,  
                    openTime: 'n',
                    closeTime: '',
                    state: false,
                };
                // hasLights.value = false;
            } else {
                console.log('Curtains error');
                const errorMessage = await response.text();
                console.log(errorMessage);
                
                const errorStore = useErrorStore();
                errorStore.setError(errorMessage);
            }

        },
      async addLights(room: string) {
              const token = this.token;
  
              console.log(room);
  
              const response = await fetch('http://localhost:8080/api/appliance/lights', {
                  method: 'POST',
                  headers: {
                      Authorization: `Bearer ${token}`,
                      'Content-Type': 'application/json'
                  },
                  body: JSON.stringify({ room })
                });
          
                if (response.status === HttpStatusCode.Created) {
                  this.content = true;
                  const successMessage = "New light source added successfully";
                    console.log(successMessage);
                    
                    const successStore = useSuccessStore();
                    successStore.setSuccessMessage(successMessage);
                } else if (response.status === HttpStatusCode.BadRequest) {
                  console.log('Lights BadRequest');
                    this.content = false;
                    this.error = 'BadRequest'
                    const errorMessage = await response.text();
                    console.log(errorMessage);
                    
                    const errorStore = useErrorStore();
                    errorStore.setError(errorMessage);
                } else {
                  console.log('Lights error');
                  this.error = response.statusText;
                  const errorMessage = await response.text();
                    console.log(errorMessage);
                    
                    const errorStore = useErrorStore();
                    errorStore.setError(errorMessage);
                }
  
      },
      async addDevice(type: string) {
        const token = this.token;

        console.log(type);

        const response = await fetch('http://localhost:8080/api/appliance/devices', {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ type })
          });
    
          if (response.status === HttpStatusCode.Created) {
            this.content = true;
            const successMessage = "New device added successfully";
            console.log(successMessage);
            
            const successStore = useSuccessStore();
            successStore.setSuccessMessage(successMessage);
          } else if (response.status === HttpStatusCode.BadRequest) {
            console.log('Device BadRequest');
              this.content = false;
              this.error = 'BadRequest'
              const errorMessage = await response.text();
              console.log(errorMessage);
              
              const errorStore = useErrorStore();
              errorStore.setError(errorMessage);
          } else {
            console.log('Device error');
            this.error = response.statusText;
            const errorMessage = await response.text();
              console.log(errorMessage);
              
              const errorStore = useErrorStore();
              errorStore.setError(errorMessage);
          }

        },
        async addCurtains() {
            const token = this.token;
    
            console.log('curtains');
    
            const response = await fetch('http://localhost:8080/api/appliance/curtains', {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                // body: JSON.stringify({  })
              });
        
              if (response.status === HttpStatusCode.Created) {
                this.content = true;
                const successMessage = "New device added successfully";
                console.log(successMessage);
                
                const successStore = useSuccessStore();
                successStore.setSuccessMessage(successMessage);
              } else if (response.status === HttpStatusCode.BadRequest) {
                console.log('Curtains BadRequest');
                  this.content = false;
                  this.error = 'BadRequest'
                  const errorMessage = await response.text();
                  console.log(errorMessage);
                  
                  const errorStore = useErrorStore();
                  errorStore.setError(errorMessage);
              } else {
                console.log('Curtains error');
                this.error = response.statusText;
                const errorMessage = await response.text();
                  console.log(errorMessage);
                  
                  const errorStore = useErrorStore();
                  errorStore.setError(errorMessage);
              }
    
        },
        async addAutomation(openTime: string, closeTime: string) {
            const token = this.token;
    
            console.log(openTime + ' till ' + closeTime);
    
            const response = await fetch('http://localhost:8080/api/appliance/automation', {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ openTime, closeTime })
              });
        
              if (response.status === HttpStatusCode.Created) {
                this.content = true;
                const successMessage = "Automation added successfully";
                console.log(successMessage);
                
                const successStore = useSuccessStore();
                successStore.setSuccessMessage(successMessage);
              } else if (response.status === HttpStatusCode.BadRequest) {
                console.log('Automation BadRequest');
                  this.content = false;
                  this.error = 'BadRequest'
                  const errorMessage = await response.text();
                  console.log(errorMessage);
                  
                  const errorStore = useErrorStore();
                  errorStore.setError(errorMessage);
              } else {
                console.log('Curtains error');
                this.error = response.statusText;
                const errorMessage = await response.text();
                  console.log(errorMessage);
                  
                  const errorStore = useErrorStore();
                  errorStore.setError(errorMessage);
              }
    
        },
        async changeState(id: number, type:string, value: boolean) {
            const token = this.token;

            console.log('changeState');
            console.log(id + ' ' + type + ' ' + value); 

            const response = await fetch('http://localhost:8080/api/appliance/state', {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id, type, value })
              });
        
              if (response.status === HttpStatusCode.Created) {
                this.content = true;
                  console.log('changeState done');
              } else {
                console.log('Lights error');
                this.error = response.statusText;
                const errorMessage = await response.text();
                  console.log(errorMessage);
                  
                  const errorStore = useErrorStore();
                  errorStore.setError(errorMessage);
              }

        },
        delete() {
            
        }
    }
  });