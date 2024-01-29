<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useVoiceStore } from '@/stores/voice'
import ErrorAlert from './components/ErrorAlert.vue'
import SuccessAlert from './components/SuccessAlert.vue'

const auth = useAuthStore();
const voice = useVoiceStore();

function logout() {
  auth.logout();
}

</script>

<template>
  <div class="min-h-screen scroll-smooth bg-slate-700">
    <header><!-- class="p-7"> -->
      <nav v-if="!auth.user" class="fixed top-0 left-0 right-0 flex flex-row list-none p-4 bg-slate-800 text-white z-20">
        <a href="/"><img class="scale-75 ml-4" alt="Home Automation" src="#"></a>
        <div v-if="!auth.user">
          <RouterLink to="/" class="ml-4 my-auto">About</RouterLink>
          <RouterLink to="/login" custom v-slot="{ navigate }">
            <button class="absolute right-40 my-auto mx-10 py-1 px-8 border-blue-light border-x border-y bg-indigo-50 hover:bg-indigo-400 hover:border-blue-dark hover:text-white 
            text-gray-950 rounded-3xl" @click="navigate" role="link">
              Login
            </button>
          </RouterLink>
          <RouterLink to="/signup" custom v-slot="{ navigate }">
            <button class="absolute right-0 my-auto mx-10 py-1 px-8 border-blue-light border-x border-y bg-indigo-50 hover:bg-indigo-400 hover:border-blue-dark hover:text-white 
            text-gray-950 rounded-3xl" @click="navigate" role="link">
              Sign up
            </button>
          </RouterLink>
        </div>
      </nav>
      
      <aside v-else id="separator-sidebar" class="fixed top-0 left-0 z-40 w-56 h-screen transition-transform -translate-x-full sm:translate-x-0" aria-label="Sidebar">
        <div class="h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-slate-800">
            <ul class="space-y-2 font-medium">
              <li class="pb-3">
                <p class="flex items-center p-2 rounded-lg text-indigo-200 text-lg tracking-wider font-serif font-bold">
                  <svg aria-hidden="true" class="flex-shrink-0 w-6 h-6 mr-2 text-indigo-400"  fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path d="M8.25 21v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21m0 0h4.5V3.545M12.75 21h7.5V10.75M2.25 21h1.5m18 0h-18M2.25 9l4.5-1.636M18.75 3l-1.5.545m0 6.205l3 1m1.5.5l-1.5-.5M6.75 7.364V3h-3v18m3-13.636l10.5-3.819" stroke-linecap="round" stroke-linejoin="round"></path>
                  </svg>
                  Hi, {{auth.name}}
                </p>
              </li>
              <li>
                <RouterLink to="/home/dashboard" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group" exact>
                  <svg aria-hidden="true" class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-indigo-200 group-hover:text-gray-900 dark:group-hover:text-white" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M5 3a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2V5a2 2 0 00-2-2H5zM5 11a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2v-2a2 2 0 00-2-2H5zM11 5a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V5zM11 13a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"></path></svg>
                  <span class="ml-3">Dashboard</span>
                </RouterLink>
              </li>
              <li>
                <RouterLink to="/home/devices" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                  <svg aria-hidden="true" class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-indigo-200 group-hover:text-gray-900 dark:group-hover:text-white" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path d="M21 7.5l-2.25-1.313M21 7.5v2.25m0-2.25l-2.25 1.313M3 7.5l2.25-1.313M3 7.5l2.25 1.313M3 7.5v2.25m9 3l2.25-1.313M12 12.75l-2.25-1.313M12 12.75V15m0 6.75l2.25-1.313M12 21.75V19.5m0 2.25l-2.25-1.313m0-16.875L12 2.25l2.25 1.313M21 14.25v2.25l-2.25 1.313m-13.5 0L3 16.5v-2.25" stroke-linecap="round" stroke-linejoin="round"></path>
                    </svg>
                    <span class="flex-1 ml-3 whitespace-nowrap">Devices</span>
                </RouterLink>
              </li>
              <li>
                  <a href="/home/settings" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                    <svg aria-hidden="true" class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-indigo-200 group-hover:text-gray-900 dark:group-hover:text-white" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M5 4a1 1 0 00-2 0v7.268a2 2 0 000 3.464V16a1 1 0 102 0v-1.268a2 2 0 000-3.464V4zM11 4a1 1 0 10-2 0v1.268a2 2 0 000 3.464V16a1 1 0 102 0V8.732a2 2 0 000-3.464V4zM16 3a1 1 0 011 1v7.268a2 2 0 010 3.464V16a1 1 0 11-2 0v-1.268a2 2 0 010-3.464V4a1 1 0 011-1z"></path></svg>
                    <span class="flex-1 ml-3 whitespace-nowrap">Settings</span>
                  </a>
              </li>
              <li>
                <button v-if="voice.stat === 'Click to give a voice command'" class="rounded-full bg-indigo-400 p-5 py-8 ml-14 mt-16" @click="voice.startRecording()">Record</button>
                <button v-else-if="voice.stat === 'Recording...'" class="rounded-full bg-indigo-400 p-7 py-8 ml-14 mt-16" @click="voice.stopRecording()">Stop</button>
                <button v-else-if="voice.stat === 'Processing...'" class="rounded-full bg-indigo-400 p-5 py-10 ml-14 mt-16" @click="">Thinking..</button>
                <button v-else class="rounded-full bg-indigo-400 p-4 py-9 ml-14 mt-16" @click="$router.go(0)">Try again</button>
                <p class="text-indigo-50 text-sm font-light mt-3 ml-2">{{ voice.stat }}</p>
              </li>
            </ul>
            <ul class="fixed bottom-5 w-40 pt-4 mt-4 space-y-2 font-medium border-t border-gray-200 dark:border-indigo-400">
              <li>
                <RouterLink to="/" class="flex items-center p-2 text-gray-900 transition duration-75 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 dark:text-white group">
                    <svg aria-hidden="true" class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-indigo-400 group-hover:text-gray-900 dark:group-hover:text-white" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M9 2a1 1 0 000 2h2a1 1 0 100-2H9z"></path><path fill-rule="evenodd" d="M4 5a2 2 0 012-2 3 3 0 003 3h2a3 3 0 003-3 2 2 0 012 2v11a2 2 0 01-2 2H6a2 2 0 01-2-2V5zm3 4a1 1 0 000 2h.01a1 1 0 100-2H7zm3 0a1 1 0 000 2h3a1 1 0 100-2h-3zm-3 4a1 1 0 100 2h.01a1 1 0 100-2H7zm3 0a1 1 0 100 2h3a1 1 0 100-2h-3z" clip-rule="evenodd"></path></svg>
                    <span class="ml-3">About</span>
                </RouterLink>
              </li>
              <li>
                  <a @click="logout" href="#" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700">
                    <svg aria-hidden="true" class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-indigo-400 group-hover:text-gray-900 dark:group-hover:text-white" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 3a1 1 0 00-1 1v12a1 1 0 102 0V4a1 1 0 00-1-1zm10.293 9.293a1 1 0 001.414 1.414l3-3a1 1 0 000-1.414l-3-3a1 1 0 10-1.414 1.414L14.586 9H7a1 1 0 100 2h7.586l-1.293 1.293z" clip-rule="evenodd"></path></svg>
                    <span class="flex-1 ml-3 whitespace-nowrap">Sign out</span>
                  </a>
              </li>
            </ul>
        </div>
      </aside>
    </header>

    <main class="p-8">
      <ErrorAlert class="fixed top-16 left-3/4 mx-auto z-10" />
      <SuccessAlert class="fixed top-16 left-3/4 mx-auto z-10" />
      
      <div v-if="!auth.user" class="p-7">
        <RouterView />
      </div>
      <div v-else class="p-4 sm:ml-56">
        <RouterView />
      </div>
    </main>
  </div>
</template>
