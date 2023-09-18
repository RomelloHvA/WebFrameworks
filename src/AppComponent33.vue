<template>

  <HeaderComponent/>
  <NavBar />
  <router-view/>

</template>

<script>
import HeaderComponent from "@/components/HeaderComponent";
import NavBar from "@/components/NavBar";

export default {
  name: "AppComponent33",
  components: { HeaderComponent, NavBar },

}
</script>

<style>

</style>