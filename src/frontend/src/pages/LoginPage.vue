<script lang="ts">
import router from '@/router'
import { useAuth } from '@/stores/auth'

export default {
  data() {
    return {
      username: '',
      password: '',
      auth: useAuth()
    }
  },
  methods: {
    async submit() {
      await this.auth.login(this.username, this.password)
      router.go(0) // Evaluates the current route again, the route guard will do an auto-login on the client if authentication was successful
    }
  }
}
</script>

<template>
  <h1>Login</h1>
  <form @submit.prevent="submit">
    <label>
      Username
      <input v-model="username" />
    </label>
    <label>
      Password
      <input v-model="password" type="password" />
    </label>
    <button type="submit">Login</button>
  </form>
</template>
