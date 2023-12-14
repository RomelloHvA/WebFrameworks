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
import {FetchInterceptor} from "@/services/FetchInterceptor";
import {shallowReactive} from "vue";

export default {
  name: "AppComponent44",
  components: { HeaderSb, NavBarSb },
  provide() {
    this.theSessionService = shallowReactive(new SessionSbService(CONFIG.BACKEND_URL + '/authentication', 'authenticationToken'))
    this.theFetchInterceptor = new FetchInterceptor(this.theSessionService, this.$router)
    return {
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL + '/scooters'),
      scooterService2: new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/scooters', Scooter.copyConstructor),
      SessionSbService: this.theSessionService,
    }
  }

}
</script>

<style>

</style>

