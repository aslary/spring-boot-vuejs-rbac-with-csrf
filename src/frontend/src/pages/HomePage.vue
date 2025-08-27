<script lang="ts">
import { Role } from '@/models/role'
import { useAuth } from '@/stores/auth'

export default {
  data() {
    return {
      auth: useAuth(),
      role: Role
    }
  }
}
</script>

<template>
  <p>Username</p>
  <pre>{{ auth.username }}</pre>

  <hr />

  <p>Authorities</p>
  <pre>{{ auth.authorities }}</pre>

  <hr />

  <pre>hasAnyRole('ADMIN'): {{ auth.hasAnyRole(role.ADMIN) }}</pre>
  <pre>hasAnyRole('USER'): {{ auth.hasAnyRole(role.USER) }}</pre>
  <pre>hasAnyRole('ADMIN', 'USER'): {{ auth.hasAnyRole(role.ADMIN, role.USER) }}</pre>
</template>
