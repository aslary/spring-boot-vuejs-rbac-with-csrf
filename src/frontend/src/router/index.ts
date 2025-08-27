import FooPage from '@/pages/FooPage.vue'
import HomePage from '@/pages/HomePage.vue'
import LoginPage from '@/pages/LoginPage.vue'
import { useAuth } from '@/stores/auth'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: HomePage },
    { path: '/login', component: LoginPage },
    { path: '/foo', component: FooPage },
    { path: '/:pathMatch(.*)*', redirect: '/' }
  ]
})

router.beforeEach((to, from, next) => {
  const auth = useAuth()

  if (to.path === '/login' && auth.isAuthenticated) {
    next('/')
  } else if (to.path !== '/login' && !auth.isAuthenticated) {
    next('/login')
  } else {
    next()
  }
})

export default router
