<template>
    <div class="container d-flex m-auto">
        <h5 class="text-warning mx-3">{{message}}</h5>
        <div class="spinner-border text-warning" role="status">
            <span class="sr-only"></span>
        </div>
    </div>
</template>

<script>
export default {
    name: "LoadingComponent",
    data() {
        return {
            message: "Loading "
        }
    }
    
}
</script>

<style>
    
</style>
