<template>

  <HeaderComponent/>
  <NavBar />
  <router-view/>

</template>

<script>
import HeaderComponent from "@/components/HeaderComponent";
import NavBar from "@/components/NavBar";
import { ScootersAdaptor } from "./services/ScootersAdaptor";
import CONFIG from "@/app-config"

export default {
  name: "AppComponent31",
  components: { HeaderComponent, NavBar },
  provide() {
    return {
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL + '/scooters')
    }
  }

}
</script>

<style>

</style>