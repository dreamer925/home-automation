<script setup lang="ts">
import { useCrudStore } from '@/stores/crud';
import { useAuthStore } from '@/stores/auth';
import { useVoiceStore } from '@/stores/voice';
import { ref, watch } from 'vue';
import { onMounted } from 'vue';


const token = useAuthStore().token;

const room = ref('');
const type = ref('');

const curtains = ref({
    id: 1,  
    openTime: 'n',
    closeTime: '',
    state: false,
})

function addLights() {
    console.log('Submit Lights');
    useCrudStore().addLights(room.value);
    console.log(room.value);
    };

function addDevices() {
    console.log('Submit Devices')
    if(type.value === 'curtains'){
      useCrudStore().addCurtains();
      setTimeout(() => {
        getCurtains();
      console.log('getCurtains!');
    }, 2000);
    } else {
      useCrudStore().addDevice(type.value);
    }
    };

    function addAutomation() {
    console.log('add Automation')
    useCrudStore().addAutomation(curtains.value.openTime, curtains.value.closeTime);

    setTimeout(() => {
      getCurtains();
      console.log('getCurtains!');
    }, 2000);

    };

async function getCurtains() {
  await useCrudStore().getCurtains();
  curtains.value = useCrudStore().curtains;
  console.log(curtains.value);
}

watch(() => useVoiceStore().updated, (newVal) => {
      if (newVal === 'automation') {
        console.log('curtains update')
        getCurtains();
      }
    });

onMounted(async () => {
  getCurtains();
});
</script>  

<template>
  <div class="grid grid-cols-2 gap-10">
    <div class="bg-slate-600 rounded-3xl p-5">
      <p class="ml-6 pt-2 text-xl text-indigo-50">Add Lights</p>
      <form @submit.prevent="addLights" class="p-8 bg-indigo-200 m-5 rounded-3xl">
                  
        <div>
          <svg aria-hidden="true" class="h-6 w-6 float-left text-indigo-600 mt-2" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 4.5v15m7.5-7.5h-15" stroke-linecap="round" stroke-linejoin="round"></path>
          </svg>
          <svg aria-hidden="true" class="h-10 w-10 text-indigo-600 float-left mb-2 mr-2" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 18v-5.25m0 0a6.01 6.01 0 001.5-.189m-1.5.189a6.01 6.01 0 01-1.5-.189m3.75 7.478a12.06 12.06 0 01-4.5 0m3.75 2.383a14.406 14.406 0 01-3 0M14.25 18v-.192c0-.983.658-1.823 1.508-2.316a7.5 7.5 0 10-7.517 0c.85.493 1.509 1.333 1.509 2.316V18" stroke-linecap="round" stroke-linejoin="round"></path>
          </svg>
            <label for="room" class="block pt-2 mb-2 text-lg font-medium text-slate-600">
              Which room is the light bulb in?
            </label>
                <select id="room" v-model="room" class="bg-indigo-300 border border-gray-400 text-slate-700 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5" required>
                    <option disabled value="">Please select one</option>
                    <option>kitchen</option>
                    <option>bedroom</option>
                    <option>bathroom</option>
                    <option>closet</option>
                    <option>living room</option>
                    <option>pantry</option>
                </select>
        </div>
                      
        <button type="submit" class="text-white bg-gradient-to-r from-indigo-300 to-indigo-600 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-cyan-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mt-4">
            Add
        </button>
      </form>
    </div>

    <div class="bg-slate-600 rounded-3xl p-5">
      <p class="ml-6 pt-2 text-xl text-indigo-50">Add device</p>
      <form @submit.prevent="addDevices" class="p-8 bg-indigo-200 m-5 rounded-3xl">
                  
                  <div>
                    <svg aria-hidden="true" class="h-6 w-6 float-left text-indigo-600 mt-2" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path d="M12 4.5v15m7.5-7.5h-15" stroke-linecap="round" stroke-linejoin="round"></path>
                    </svg>
                    <svg aria-hidden="true" class="h-10 w-10 text-indigo-600 float-left mb-2 mr-2" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path d="M21.75 17.25v-.228a4.5 4.5 0 00-.12-1.03l-2.268-9.64a3.375 3.375 0 00-3.285-2.602H7.923a3.375 3.375 0 00-3.285 2.602l-2.268 9.64a4.5 4.5 0 00-.12 1.03v.228m19.5 0a3 3 0 01-3 3H5.25a3 3 0 01-3-3m19.5 0a3 3 0 00-3-3H5.25a3 3 0 00-3 3m16.5 0h.008v.008h-.008v-.008zm-3 0h.008v.008h-.008v-.008z" stroke-linecap="round" stroke-linejoin="round"></path>
                    </svg>
                      <label for="room" class="block pt-2 mb-2 text-lg font-medium text-slate-600">
                        Which room is the device in?
                      </label>
                          <select id="room" v-model="type" class="bg-indigo-300 border border-gray-400 text-slate-700 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5" required>
                              <option disabled value="">Please select one</option>
                              <option>TV</option>
                              <option>AC</option>
                              <option>underfloor heating</option>
                              <option>curtains</option>
                          </select>
                  </div>
                                
                  <button type="submit" class="text-white bg-gradient-to-r from-indigo-300 to-indigo-600 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-cyan-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mt-4">
                      Add
                  </button>
                </form>
    </div>
  </div>

  <div class="bg-slate-600 rounded-3xl p-5 mt-10">
    <p class="ml-6 pt-2 text-xl text-indigo-50">Manage automation</p>
    
    <div v-if="curtains.openTime != null && curtains.openTime != 'n'" class="p-8 bg-indigo-200 m-5 rounded-3xl">
      <img class="h-8 w-8 text-indigo-600 float-left mb-2 mr-2" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAACXBIWXMAAAsTAAALEwEAmpwYAAADzklEQVR4nOWbTWgTQRTH/4lIUVFRafGjCuagFwUpRRA8KCp+IVjBQ2sQxB5EoVSLiEVwCoq9eCgeREFFBMFD9eIX1IpoBEEvQqmIWsHPQ7VaUSq1tfJgVh7DJtmdnc1+zMCQkOx7/5dfsm/fvMkCQBZAHsARyyZ95gzkkwlL5y4CIGIQSFRTqAAKADpTPgulAAikf4i4AMjK/PM/GdkGIK8mI9sAHGO6J2wE0MZ0T9sIYD/TPWsjgJ1M94aNAFYx3ac2AqhluoM2AsgA+Ma0F9gGgMYjpr0VFgLoYtonbQTQwLSf2AhgFoAxqU2Pc2wDQOMx02+GhQBamf69NAHYAuACgHoP9cC41KfHHFIAYB2AUemz38PxNyu4MAodwBIAQ8znHQ82m9nxVBzNREIBzAbwkvn7AGC+x+7QC2bXgQQCmAygl/n6BaDOh30js/0BoBoJA3CO+aFktsOnPf0K+pgPSqCJAdDKfNBs1/Szgfn4C2ANEgBgI4A/zMe1gF3ebubrNYDpiDGAZQCGmT1VdVUBA6xVlsmXEFMA1QAGmO1bADWGgmxSTqnmuAGoUraYKGsvN7wxcoX5HzWYD4wAuMxsxjSbGeU2RqYAeMaOoeJqBWIA4Kjy8zxkKhCXsRDAJ6V3SHknMgANbOFC86LJQIoM+sBf2LFfAaw2qSs8BkJV3U927H1Z/RkLpMRYCeC7UmVuN6UrPAQyD8A7dtyAgVLV76lHv4SPSqHUpfElaAG4qySjpS7HdMr3vCZSneRLK81XSg56AGCxR3tXXeEhkH75PlV8613eP6hcErUC8dFHvK1AoFOzBcAkHV3hIZB6uTjZVKTz4zQ2J+SiyMsIUoFm5fa603RxZp/sLURWChd8lMImFmF1ygrSmbThsq1IgWUMQI0sf3VLYZN9iDbli3Amnbp7FRChlMLDGgWK6VbcXABnAIy4gGgqpSs0AjmvlMKUB/yOsLrR1IJ7rgBoLKUrNALhBRFdARAjAHlZI/D+RLaUrtAIpEOWpKcCBBoGAPrTxW/mt9clKVdsYySKHPCZ+aTTYIYXXZESANeVVWOxHaZUAtjNfI2XaZ6kDsA0pWdQbmstdQCOMz/vJRBrAEyVVyPHT15HVyQYwD7m402Yq8Ewhgld3jQ9oKsrEgogx+xHZJ/AKgCHmX13EF2RUAC3NHeOUgEgo2T/nG0AFjHbIZ+70akAsJbZPgyqKxIIYA+zvRpUV7AXeip4725PAAAtyn8SAukK9kJU0y+AdpO6+RgA8HvfYN6kbkY+iepeXurY+v1PkRMz3WMQSPcf6iIob7ZONXQAAAAASUVORK5CYII=">
      <p class="block pt-2 mb-2 text-xl font-medium text-slate-950">The curtains are now opening at {{ curtains.openTime }} and closing at {{ curtains.closeTime }}.</p>
      <form @submit.prevent="addAutomation" class="bg-indigo-200 m-5 rounded-3xl">
        <div class="grid grid-cols-2 gap-10">
          <div>
          <label for="openTime" class="block pt-2 mb-2 text-lg font-medium text-slate-600">
            Open at:
          </label>
          <div class="relative">
            <input type="text" id="openTime" v-model="curtains.openTime" class="bg-indigo-300 border border-gray-400 text-slate-700 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 placeholder:text-slate-500 block w-full pl-10 p-2.5" placeholder="h:mm AM/PM">
            <!-- <div class="format-suggestion text-red-600 ml-2 text-sm">Format: h:mm AM/PM</div> -->
          </div>
        </div>
        <div>
          <label for="openTime" class="block pt-2 mb-2 text-lg font-medium text-slate-600">
            Close at:
          </label>
          <div class="relative">
            <input type="text" id="openTime" v-model="curtains.closeTime" class="bg-indigo-300 border border-gray-400 text-slate-700 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 placeholder:text-slate-500 block w-full pl-10 p-2.5" placeholder="h:mm AM/PM">
            <!-- <div class="format-suggestion text-red-600 ml-2 text-sm">Format: h:mm AM/PM</div> -->
          </div>
        </div>
        </div>

        <button type="submit" class="text-white bg-gradient-to-r from-indigo-300 to-indigo-600 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-cyan-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mt-4">
                      Change
                  </button>
      </form>
    </div>
    <div v-else class="">
      <p class="ml-6 pt-2 m-2 text-indigo-200">You need to have curtains to manage automation</p>
    </div>
  </div>
</template>