<template>

  <!-- <WelcomeComponent/> -->
  <HeaderComponent/>
  <NavBar />
  <router-view />

</template>

<script>
import HeaderComponent from "@/components/HeaderComponent";
import NavBar from "@/components/NavBar";
export default {
  name: "AppComponent31",
  components: { HeaderComponent, NavBar }
}
</script>

<style>

</style>