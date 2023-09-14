<template>
  <HeaderComponent/>
  <NavBar />
  <!-- <WelcomeComponent/> -->
  <ScootersOverview31/>
</template>

<script>
import HeaderComponent from "@/components/HeaderComponent";
// import WelcomeComponent from "@/components/WelcomeComponent";
import ScootersOverview31 from "@/components/scooters/ScootersOverview31.vue";
import NavBar from "@/components/NavBar";
export default {
  name: "AppComponent31",
  components: { HeaderComponent, NavBar, ScootersOverview31 }
}
</script>

<style>

</style>