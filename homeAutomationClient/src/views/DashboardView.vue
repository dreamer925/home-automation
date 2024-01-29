<script setup lang="ts">
import { onMounted } from 'vue';
import { ref } from 'vue';
import { useCrudStore } from '@/stores/crud';


const curtains = ref({
    id: 1,  
    openTime: 'n',
    closeTime: '',
    state: false,
})

onMounted(async () => {
  const script = document.createElement('script');
    script.src = 'src/assets/weather.js';
    script.src = 'https://app1.weatherwidget.org/js/?id=ww_4161a744cc672';
    script.async = true;
    document.body.appendChild(script);

    await useCrudStore().getCurtains();
    curtains.value = useCrudStore().curtains;
    console.log(curtains.value);
});
</script>

<template>
    <div class="grid grid-cols-2 gap-20">
      <div>
        <div class="rounded-2xl" id="ww_4161a744cc672" v='1.3' loc='auto' a='{"t":"horizontal","lang":"en","sl_lpl":1,"ids":[],"font":"Arial","sl_ics":"one_a","sl_sot":"celsius","cl_bkg":"image","cl_font":"#FFFFFF","cl_cloud":"#FFFFFF","cl_persp":"#81D4FA","cl_sun":"#FFC107","cl_moon":"#FFC107","cl_thund":"#FF5722"}'>
          <a href="https://weatherwidget.org/" id="ww_4161a744cc672_u" target="_blank">
            Weather widgets for websites
          </a>
        </div>
      </div>
      <div class="p-10 bg-indigo-400 m-5 rounded-2xl">
        <p class="text-2xl mb-2 text-slate-700">Sensor data</p>
        <div class="h-1 bg-slate-600 mb-2"></div>
        <p class="text-indigo-100 text-lg"><span class="text-slate-900">Temperature:</span> 30 degrees</p>
        <p class="text-indigo-100 text-lg"><span class="text-slate-900">Illuminance level:</span> 400 lux</p>
      </div>

      <div v-if="curtains.openTime != null && curtains.openTime != 'n'" class="p-10 bg-indigo-400 m-5 rounded-2xl">
        <p class="text-2xl mb-2 text-slate-700">Current tasks</p>
        <div class="h-1 bg-slate-600 mb-2"></div>
        <p class="text-slate-900 text-lg">• The curtains are now opening at {{ curtains.openTime }} and closing at {{ curtains.closeTime }}.</p>
      </div>
  </div>
</template>