<template>

  <HeaderComponent/>
  <NavBar />
  <router-view/>


</template>

<script>
import HeaderComponent from "@/components/HeaderComponent";
import NavBar from "@/components/NavBar";
import CONFIG from "@/app-config"
import {Scooter} from "@/models/Scooter";
import {ScootersAdaptor} from "@/services/ScootersAdaptor";
import {CachedRESTAdaptorWithFetch} from "@/services/CachedRESTAdaptorWithFetch";

export default {
  name: "AppComponent31",
  components: { HeaderComponent, NavBar },
  provide() {
    return {
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL + '/scooters'),
      scooterService2: new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/scooters', Scooter.copyConstructor)
    }
  }

}
</script>

<style>

</style>
