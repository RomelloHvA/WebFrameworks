import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import scootersOverview31 from "@/components/scooters/ScootersOverview31";
import ScootersOverview32 from "@/components/scooters/ScootersOverview32";

const routes = [
  {
    path: '/',
    name: 'homeView',
    component: HomeView
  },
  {
    path: '/scooterDetail',
    name: 'scooterDetail',
    component: ScootersOverview32
  },{
    path: '/scooterOverview',
    name: 'ScootersOverview31',
    component: scootersOverview31
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
