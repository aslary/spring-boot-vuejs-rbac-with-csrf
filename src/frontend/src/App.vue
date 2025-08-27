<script lang="ts">
import { RouterLink } from 'vue-router'
import router from './router'
import { useAuth } from './stores/auth'

export default {
  data() {
    return {
      auth: useAuth()
    }
  },
  methods: {
    async logout() {
      await this.auth.logout()
      router.go(0) // Evaluates the current route again, the route guard will redirect to the login page
    }
  }
}
</script>

<template>
  <div class="container">
    <nav v-if="auth.isAuthenticated">
      <ul>
        <li>
          <strong>Spring + Vue ({{ $route.fullPath }})</strong>
        </li>
      </ul>
      <ul>
        <li><RouterLink to="/">Home </RouterLink></li>
        <li><RouterLink to="/foo">Foo </RouterLink></li>
        <li><a href="/h2-console" data-tooltip="Only admins can view the database">Database</a></li>
        <li>
          <button @click="logout">Logout</button>
        </li>
      </ul>
    </nav>

    <main>
      <RouterView />
    </main>
  </div>
</template>

<style>
div {
  margin-top: 30px;
}
</style>
