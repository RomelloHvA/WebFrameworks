<template>

  <HeaderComponent/>
  <NavBar />
  <router-view/>


</template>

<script>
import HeaderComponent from "@/components/HeaderComponent";
import NavBar from "@/components/NavBar";
import CONFIG from "@/app-config"
import {RESTAdaptorWithFetch} from "@/services/RESTAdaptorWithFetch";
import {Scooter} from "@/models/Scooter";
import {ScootersAdaptor} from "@/services/ScootersAdaptor";

export default {
  name: "AppComponent31",
  components: { HeaderComponent, NavBar },
  provide() {
    return {
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL + '/scooters'),
      scooterService2: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/scooters', Scooter.copyConstructor)
    }
  }

}
</script>

<style>

</style>
