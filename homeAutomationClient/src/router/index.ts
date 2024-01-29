import { createRouter, createWebHistory } from 'vue-router'
import AboutView from '../views/AboutView.vue'
import DevicesView from '../views/DevicesView.vue'
import DashboardView from '../views/DashboardView.vue'
import SettingsView from '../views/SettingsView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/RegisterView.vue'
import ConfirmRegistrationView from '../views/ConfirmRegistrationView.vue'
import { useAuthStore } from '@/stores/auth'
import { useVoiceStore } from '@/stores/voice'
import { useErrorStore } from '@/stores/error'
import { useSuccessStore } from '@/stores/success'
import { useAutomationStore } from '@/stores/automation'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'About',
      component: AboutView
    },
    {
      path: '/home/dashboard',
      name: 'Dashboard',
      component: DashboardView
    },
    {
      path: '/home/devices',
      name: 'Devices',
      component: DevicesView
    },
    {
      path: '/home/settings',
      name: 'Settings',
      component: SettingsView
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    },
    {
      path: '/signup',
      name: 'Signup',
      component: SignupView
    },
    {
      path: '/signup/confirmRegistration',
      name: 'ConfirmRegistration',
      component: ConfirmRegistrationView
    }
  ]
});

router.beforeEach(async (to) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/', '/login', '/signup', '/signup/confirmRegistration'];
  const authRequired = !publicPages.includes(to.path);
  // setActivePinia(createPinia());
  const auth = useAuthStore();
  const voice = useVoiceStore();
  const error = useErrorStore();
  const success = useSuccessStore();
  const automation = useAutomationStore();


  if (authRequired && !auth.user) {
      auth.returnUrl = to.fullPath;
      return '/login';
  }
});

export default router
