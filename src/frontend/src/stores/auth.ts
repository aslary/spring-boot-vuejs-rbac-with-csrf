import type { AuthState } from '@/models/auth-state'
import axios from 'axios'
import { defineStore } from 'pinia'

export const useAuth = defineStore('auth', {
  state: (): AuthState => {
    return {
      username: null,
      authorities: []
    }
  },
  actions: {
    async me() {
      var res = await axios.get('/auth/me')
      if (res && res.status === 200) {
        Object.assign(this, res.data)
      }
    },
    async login(username: string, password: string) {
      await axios.post('/auth/login', { username, password })
    },
    async logout() {
      this.username = null
      this.authorities = []
      await axios.post('/auth/logout')
    }
  },
  getters: {
    isAuthenticated: (state) => !!state.username,
    hasAnyRole:
      (state) =>
      (...roles: string[]) =>
        roles.map((item) => 'ROLE_' + item).some((item) => state.authorities.includes(item))
  }
})
