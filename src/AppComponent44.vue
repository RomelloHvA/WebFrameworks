<template>

  <HeaderSb/>
  <NavBarSb />
  <router-view class="vh-100"/>


</template>

<script>
import CONFIG from "@/app-config"
import {Scooter} from "@/models/Scooter";
import {ScootersAdaptor} from "@/services/ScootersAdaptor";
import {CachedRESTAdaptorWithFetch} from "@/services/CachedRESTAdaptorWithFetch";
import {SessionSbService} from "@/services/SessionSbService";
import HeaderSb from "@/components/HeaderSb.vue";
import NavBarSb from "./components/NavBarSb.vue";

export default {
  name: "AppComponent44",
  components: { HeaderSb, NavBarSb },
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

