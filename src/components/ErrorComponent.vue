<template>
    <div class="container">
        <h5 class="text-danger mx-3">{{error}}</h5>
    </div>
</template>
<script>
export default {
    name: "ErrorComponent",
    props: {
        error: String
    }
}
</script>
<style>
    
</style>