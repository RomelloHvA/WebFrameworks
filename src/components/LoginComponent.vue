<template>
    <div class="container w-25 top">
    <form class="justify-content-center m-auto">
    <!-- Email input -->
    <div class="form-outline mb-4">
        <input v-model="email" type="email" id="form2Example1" class="form-control" />
        <label class="form-label text-white" for="form2Example1">Email address</label>
    </div>

    <!-- Password input -->
    <div class="form-outline mb-4">
        <input v-model="password" type="password" id="form2Example2" class="form-control" />
        <label class="form-label text-white" for="form2Example2">Password</label>
        <p v-show="loginerror" class="text-danger">Wrong credentials</p>
    </div>


    <!-- Submit button -->
    <button @click="handleLogin" type="button" class="btn btn-primary btn-block mb-4 w-100">Sign in</button>

    <!-- Register buttons -->
    <div class="text-center text-white">
        <p>Not a member? <a href="#!">Sing up</a></p>
    </div>
    </form>
    </div>
</template>
<script setup>
import { inject, ref, onBeforeMount } from 'vue'
import router from '@/router';
import { useRoute } from 'vue-router';

const email = ref('')
const password = ref('')
const loginerror = ref(false)
const route = useRoute()
const SessionSbService = inject('SessionSbService')

/**
 * If the user is already logged in, redirect to home
 */
onBeforeMount(() => {
    if (route.query.signOut) {
        SessionSbService.signOut()
        router.push({ query: null})
    }
})

/**
 * Handle the login process and redirect to the returnTo query param or home
 */
const handleLogin = async () => {
    const {user, status} = await SessionSbService.asyncSignIn(email.value, password.value)

    if (user === null && status === 406){
        loginerror.value = true
    } else {
        loginerror.value = false
        router.push(route.query.returnTo || '/');
    }
}


</script>
<style>
    .top {
        margin-top: 10%;
        min-width: 300px !important;    
    }
</style>