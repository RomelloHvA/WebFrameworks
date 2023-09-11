<template>
  <HeaderComponent/>
  <NavBar />
  <WelcomeComponent></WelcomeComponent>
</template>

<script>
import HeaderComponent from "@/components/HeaderComponent";
import WelcomeComponent from "@/components/WelcomeComponent";
import NavBar from "@/components/NavBar";
export default {
  name: "AppComponent31",
  components: {WelcomeComponent, HeaderComponent, NavBar}
}
</script>

<style scoped>

</style>