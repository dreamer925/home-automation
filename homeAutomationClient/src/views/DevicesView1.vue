<!-- <script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { onMounted } from 'vue';
import { useVoiceStore } from '@/stores/voice'

const voice = useVoiceStore();
const isCheckedBedroom = ref(false);
const isCheckedKitchen = ref(false);
const isCheckedBathroom = ref(false);

const colorBedroom = ref('none');
const colorKitchen = ref('none');
const colorBathroom = ref('none');

const isCheckedAC = ref(false);
const isCheckedСurtains = ref(false);
const isCheckedFloor = ref(false);
const isCheckedTV = ref(false);
const go = ref(false);
const goV = computed(() => go.value);

watch(() => voice.command, (newVal) => {
  if (newVal === 'ready') {
    console.log('go = true')
    go.value = true;
  }
});

    // setInterval(() => {
    //   if (voice.command === 'ready') {
    //     console.log('Command is ready!')
    //   }
    // }, 500)

watch(() => go.value, (newVal) => {
  if (newVal === true) {
// const state = computed(() => {
//       if(goV.value === true){
  const res = voice.res;
  console.log("if");
  if(res.intent === 'color'){
    console.log("color");
    if(res.location === 'kitchen'){
      console.log("kitch");
      isCheckedKitchen.value = true;
      colorKitchen.value = res.color ? res.color : 'white';
      go.value = false;
    }else if(res.location === 'bathroom'){
      isCheckedBathroom.value = true;
      colorBathroom.value = res.color ? res.color : 'white';
      go.value = false;
    }else if(res.location === 'bedroom'){
      isCheckedBedroom.value = true;
      colorBedroom.value = res.color ? res.color : 'white';
      go.value = false;
    }else{
      isCheckedKitchen.value = true;
      isCheckedBathroom.value = true;
      isCheckedBedroom.value = true;
      colorBathroom.value = res.color ? res.color : 'white';
      colorBedroom.value = res.color ? res.color : 'white';
      colorKitchen.value = res.color ? res.color : 'white';
      go.value = false;
    }
  }else if(res.intent === 'light'){
    if(res.location === 'kitchen'){
      if(res.state === 'on'){
        isCheckedKitchen.value = true;
        go.value = false;
      }else{
        isCheckedKitchen.value = false;
        colorKitchen.value = 'none';
        go.value = false;
      }
    }else if(res.location === 'bathroom'){
      if(res.state === 'on'){
        isCheckedBathroom.value = true;
        go.value = false;
      }else{
        isCheckedBathroom.value = false;
        colorBathroom.value = 'none';
        go.value = false;
      }
    }else if(res.location === 'bedroom'){
      if(res.state === 'on'){
        isCheckedBedroom.value = true;
        go.value = false;
      }else{
        isCheckedBedroom.value = false;
        colorBedroom.value = 'none';
        go.value = false;
      }
    }else{
      if(res.state === 'on'){
        isCheckedKitchen.value = true;
        isCheckedBathroom.value = true;
        isCheckedBedroom.value = true;
        go.value = false;
      }else{
        isCheckedKitchen.value = false;
        isCheckedBathroom.value = false;
        isCheckedBedroom.value = false;
        colorKitchen.value = 'none';
        colorBathroom.value = 'none';
        colorBedroom.value = 'none';
        go.value = false;
      }
    }
  }else if(res.intent === 'device'){
    if(res.device === 'underfloor heating'){
      if(res.state === 'on'){
        isCheckedFloor.value = true;
        go.value = false;
      }else{
        isCheckedFloor.value = false;
        go.value = false;
      }
    }else if(res.device === 'AC'){
      if(res.state === 'on'){
        isCheckedAC.value = true;
        go.value = false;
      }else{
        isCheckedAC.value = false;
        go.value = false;
      }
    }else if(res.device === 'TV'){
      if(res.state === 'on'){
        isCheckedTV.value = true;
        go.value = false;
      }else{
        isCheckedTV.value = false;
        go.value = false;
      }
    }else {
      if(res.state === 'on'){
        isCheckedСurtains.value = true;
        go.value = false;
      }else{
        isCheckedСurtains.value = false;
        go.value = false;
      }
    }
  }else if(res.intent === 'automation'){
    if(res.open1 === 'open'){
      //res.time1
      go.value = false;
    }else if(res.open1 === 'close'){
      //res.time1
      go.value = false;
    }
    if(res.open2 === 'open'){
      //res.time12
      go.value = false;
    }else if(res.open2 === 'close'){
      //res.time2
      go.value = false;
    }
  }
}
    });

    const isCheckedBedroomValue = computed(() => isCheckedBedroom.value);
    const isCheckedKitchenValue = computed(() => isCheckedKitchen.value);
const isCheckedBathroomValue = computed(() => isCheckedBathroom.value);

const colorBedroomValue = computed(() => colorBedroom.value);
const colorKitchenValue = computed(() => colorKitchen.value);
const colorBathroomValue = computed(() => colorBathroom.value);

const isCheckedACValue = computed(() => isCheckedAC.value);
const isCheckedСurtainsValue = computed(() => isCheckedСurtains.value);
const isCheckedFloorValue = computed(() => isCheckedFloor.value);
const isCheckedTVValue = computed(() => isCheckedTV.value);

</script>

<template>
  <div class="bg-slate-600 rounded-3xl p-5">

    <p class="ml-6 pt-2 text-xl text-indigo-50">Lights</p>
    <div class="grid grid-cols-3 gap-20">
      <div class="p-8 bg-indigo-200 m-5 rounded-3xl">
        <svg class="h-14 w-14 float-left text-slate-700 mr-4" aria-hidden="true" :fill="colorBedroomValue" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 18v-5.25m0 0a6.01 6.01 0 001.5-.189m-1.5.189a6.01 6.01 0 01-1.5-.189m3.75 7.478a12.06 12.06 0 01-4.5 0m3.75 2.383a14.406 14.406 0 01-3 0M14.25 18v-.192c0-.983.658-1.823 1.508-2.316a7.5 7.5 0 10-7.517 0c.85.493 1.509 1.333 1.509 2.316V18" stroke-linecap="round" stroke-linejoin="round"></path>
        </svg>
        <p>Room: bedroom</p>
        <label class="relative inline-flex items-center cursor-pointer mt-2">
          <input type="checkbox" v-bind:checked="isCheckedBedroomValue" value="" class="sr-only peer">
          <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-indigo-600 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-indigo-400"></div>
        </label>
      </div>
      <div class="p-8 bg-indigo-200 m-5 rounded-3xl">
        <svg class="h-14 w-14 float-left mr-4 text-slate-700" aria-hidden="true" :fill="colorKitchenValue" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 18v-5.25m0 0a6.01 6.01 0 001.5-.189m-1.5.189a6.01 6.01 0 01-1.5-.189m3.75 7.478a12.06 12.06 0 01-4.5 0m3.75 2.383a14.406 14.406 0 01-3 0M14.25 18v-.192c0-.983.658-1.823 1.508-2.316a7.5 7.5 0 10-7.517 0c.85.493 1.509 1.333 1.509 2.316V18" stroke-linecap="round" stroke-linejoin="round"></path>
        </svg>
        <p>Room: kitchen</p>
        <label class="relative inline-flex items-center cursor-pointer mt-2">
          <input type="checkbox" v-bind:checked="isCheckedKitchenValue" value="" class="sr-only peer">
          <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-indigo-600 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-indigo-400"></div>
        </label>
      </div>
      <div class="p-10 bg-indigo-200 m-5 rounded-3xl">
        <svg class="h-14 w-14 float-left mr-4 text-slate-700" aria-hidden="true" :fill="colorBathroomValue" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 18v-5.25m0 0a6.01 6.01 0 001.5-.189m-1.5.189a6.01 6.01 0 01-1.5-.189m3.75 7.478a12.06 12.06 0 01-4.5 0m3.75 2.383a14.406 14.406 0 01-3 0M14.25 18v-.192c0-.983.658-1.823 1.508-2.316a7.5 7.5 0 10-7.517 0c.85.493 1.509 1.333 1.509 2.316V18" stroke-linecap="round" stroke-linejoin="round"></path>
        </svg>
        <p>Room: bathroom</p>
        <label class="relative inline-flex items-center cursor-pointer mt-2">
          <input type="checkbox" v-bind:checked="isCheckedBathroomValue" value="" class="sr-only peer">
          <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-indigo-600 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-indigo-400"></div>
        </label>
      </div>
    </div>
  </div>
  <div class="bg-slate-600 mt-10 rounded-3xl p-3">

    <p class="ml-6 pt-2 text-xl text-indigo-50">Devices</p>
    <div class="grid grid-cols-4 gap-8">
      <div class="p-5 bg-indigo-200 m-5 rounded-3xl">
        <svg aria-hidden="true" class="h-14 w-14 float-left mr-4" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path d="M6 20.25h12m-7.5-3v3m3-3v3m-10.125-3h17.25c.621 0 1.125-.504 1.125-1.125V4.875c0-.621-.504-1.125-1.125-1.125H3.375c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125z" stroke-linecap="round" stroke-linejoin="round"></path>
        </svg>
        <p>TV</p>
        <label class="relative inline-flex items-center cursor-pointer mt-2">
          <input type="checkbox" :checked="isCheckedTVValue" value="" class="sr-only peer">
          <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-indigo-600 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-indigo-400"></div>
        </label>
      </div>
      <div class="p-5 bg-indigo-200 m-5 rounded-3xl">
        <img class="h-14 w-14 float-left mr-4" src="data:image/png;base64,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">
        <p>AC</p>
        <label class="relative inline-flex items-center cursor-pointer mt-2">
          <input type="checkbox" :checked="isCheckedACValue" value="" class="sr-only peer">
          <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-indigo-600 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-indigo-400"></div>
        </label>
      </div>
      <div class="p-5 bg-indigo-200 m-5 rounded-3xl">
        <img class="h-14 w-14 float-left mr-4" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEIAAABCCAYAAADjVADoAAAACXBIWXMAAAsTAAALEwEAmpwYAAADKElEQVR4nO2bW4hOURTHf75paiYNmZFLTUYS0kxClAflwWWm3G+JB0njRRQvEkWoUaQIRZHLPFBqeEHyQBQluRUy5GEGuY1hMDHGp137QV/fmda3z/5m1jnn+9X/+Zz17+y1915rHShQQAPjgckZKiNhpIB2IJ2hSSSMOVlM6AYGkSBKgUdZjLhFgigHrmYxwWg5EWEYMB2Y6aDFwD7gc4AJ14F+RMCAJruG03nQM6AC5VQAL/NkgNGlqCTIQ3kIvgO4YJdMZHgvCOw8MCXL4ShTE+wyU58LMikC/gqMmE0CaBEYcQOY77ib9JbGhjVibx4TZW/rfpgjfBnwUEEQPhN1tasZA4EjwFcFgfjQNUJSDFQCo5TK5IFGgRF/gAHEnFrhV2GMC1VIWQAsU6qVwB2BCZ32Bpwz44C7Cta2L511MWEE8EHBy/vSK2CoixGnFLy8D5kd7zgwGEfaBA85CYxWsGsEabitkeb9rlFLAngtMOIesFrBrtGTJoa99e5UsL596QFQ42pECXBbQRC+ZGqmVa5mlAK7gTcKAvGh03igv60xatQQ4JjACLMTxp46gRHddkd0IgVMs7vDOqVaH9BBy9RzVxOqY1ac2ehiQhXwUcHL+1Kj6ynzjPAB7TYJaVSL7beuCHOoaheYsJWYUyS8aywiAbwQGPEWOGhL/xrVAKy1IwnObFGQ4HzpS5ivtxi4oiAIX/oNTA1jxmbgCfBLQTBhdZmYUwzsFxjxI4od+VyZKzCiK0zprgSYB2yyCVSjtgPNwoqaEzOE4wFRkWkE5UyNXVPpmOiw69fQJHzATwV3iiB9ssOs5q7hzHeBCXvinoWLhLOVJonGnqcCI0zvY5eCXaMnLbX1Vmc2KEhwvvQOmOVqRAo4pyAIX+q0857OZqwBblpX25SqQ2jGRRLADoERZieMPQuFw2TOfY2+wHSuVgHbhJWoA0CrwIjHRIQxdno/X/+H1BMBluSQ9Fx0Igqn4DpbKwgK4psdBstVzbYqZQ5V6ikP6KiZ5XHUjjgmgoaAQmsieib/H9haA261iWJkFhO6wjZkokhlltuimXcoUIC+5R9Ptl1Ud2ptXAAAAABJRU5ErkJggg==">
        <p>Floor</p>
        <label class="relative inline-flex items-center cursor-pointer mt-2">
          <input type="checkbox" :checked="isCheckedFloorValue" value="" class="sr-only peer">
          <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-indigo-600 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-indigo-400"></div>
        </label>
      </div>
      <div class="p-5 bg-indigo-200 m-5 rounded-3xl">
        <img class="h-14 w-14 float-left mr-4" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAACXBIWXMAAAsTAAALEwEAmpwYAAADzklEQVR4nOWbTWgTQRTH/4lIUVFRafGjCuagFwUpRRA8KCp+IVjBQ2sQxB5EoVSLiEVwCoq9eCgeREFFBMFD9eIX1IpoBEEvQqmIWsHPQ7VaUSq1tfJgVh7DJtmdnc1+zMCQkOx7/5dfsm/fvMkCQBZAHsARyyZ95gzkkwlL5y4CIGIQSFRTqAAKADpTPgulAAikf4i4AMjK/PM/GdkGIK8mI9sAHGO6J2wE0MZ0T9sIYD/TPWsjgJ1M94aNAFYx3ac2AqhluoM2AsgA+Ma0F9gGgMYjpr0VFgLoYtonbQTQwLSf2AhgFoAxqU2Pc2wDQOMx02+GhQBamf69NAHYAuACgHoP9cC41KfHHFIAYB2AUemz38PxNyu4MAodwBIAQ8znHQ82m9nxVBzNREIBzAbwkvn7AGC+x+7QC2bXgQQCmAygl/n6BaDOh30js/0BoBoJA3CO+aFktsOnPf0K+pgPSqCJAdDKfNBs1/Szgfn4C2ANEgBgI4A/zMe1gF3ebubrNYDpiDGAZQCGmT1VdVUBA6xVlsmXEFMA1QAGmO1bADWGgmxSTqnmuAGoUraYKGsvN7wxcoX5HzWYD4wAuMxsxjSbGeU2RqYAeMaOoeJqBWIA4Kjy8zxkKhCXsRDAJ6V3SHknMgANbOFC86LJQIoM+sBf2LFfAaw2qSs8BkJV3U927H1Z/RkLpMRYCeC7UmVuN6UrPAQyD8A7dtyAgVLV76lHv4SPSqHUpfElaAG4qySjpS7HdMr3vCZSneRLK81XSg56AGCxR3tXXeEhkH75PlV8613eP6hcErUC8dFHvK1AoFOzBcAkHV3hIZB6uTjZVKTz4zQ2J+SiyMsIUoFm5fa603RxZp/sLURWChd8lMImFmF1ygrSmbThsq1IgWUMQI0sf3VLYZN9iDbli3Amnbp7FRChlMLDGgWK6VbcXABnAIy4gGgqpSs0AjmvlMKUB/yOsLrR1IJ7rgBoLKUrNALhBRFdARAjAHlZI/D+RLaUrtAIpEOWpKcCBBoGAPrTxW/mt9clKVdsYySKHPCZ+aTTYIYXXZESANeVVWOxHaZUAtjNfI2XaZ6kDsA0pWdQbmstdQCOMz/vJRBrAEyVVyPHT15HVyQYwD7m402Yq8Ewhgld3jQ9oKsrEgogx+xHZJ/AKgCHmX13EF2RUAC3NHeOUgEgo2T/nG0AFjHbIZ+70akAsJbZPgyqKxIIYA+zvRpUV7AXeip4725PAAAtyn8SAukK9kJU0y+AdpO6+RgA8HvfYN6kbkY+iepeXurY+v1PkRMz3WMQSPcf6iIob7ZONXQAAAAASUVORK5CYII=">
        <p>Сurtains</p>
        <label class="relative inline-flex items-center cursor-pointer mt-2">
          <input type="checkbox" :checked="isCheckedСurtainsValue" value="" class="sr-only peer">
          <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-indigo-600 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-indigo-400"></div>
        </label>
      </div>
    </div>
  </div>
</template> -->
