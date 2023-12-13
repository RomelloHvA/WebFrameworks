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
import {SessionSbService} from "@/services/SessionSbService";

export default {
  name: "AppComponent44",
  components: { HeaderComponent, NavBar },
  provide() {
    return {
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL + '/scooters'),
      scooterService2: new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/scooters', Scooter.copyConstructor),
      SessionSbService: new SessionSbService(CONFIG.BACKEND_URL + '/authentication', 'authenticationToken')
    }
  }

}
</script>

<style>

</style>

