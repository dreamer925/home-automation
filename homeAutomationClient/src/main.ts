import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import './assets/css/tailwind.css'
// import VueGeolocation from 'vue-browser-geolocation';

const app = createApp(App)

// app.use(VueGeolocation);

app.use(createPinia())
app.use(router)

app.mount('#app')
