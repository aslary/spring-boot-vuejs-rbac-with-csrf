import '@picocss/pico/css/pico.css'
import axios from 'axios'
import { createPinia } from 'pinia'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { useAuth } from './stores/auth'

axios.defaults.baseURL = window.location.origin + '/api'
axios.defaults.withXSRFToken = true
axios.defaults.withCredentials = true

axios.interceptors.response.use(
  (response) => response,
  async (error) => {
    const auth = useAuth()
    switch (error.status) {
      case 401:
      case 403:
        if (auth.isAuthenticated) {
          await auth.logout()
        }
    }
    return Promise.resolve()
  }
)

const app = createApp(App)

app.use(createPinia())
const auth = useAuth()
await auth.me()

app.use(router)

app.mount('#app')
