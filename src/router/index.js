import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import scootersOverview31 from "@/components/scooters/ScootersOverview31";
import ScootersOverview32 from "@/components/scooters/ScootersOverview32";
import UnknownRouteComponent from "@/components/UnknownRouteComponent";

export const routes = [
  {
    path: '/',
    name: 'homeView',
    component: HomeView
  },
  {
    path: '/scooters/scooterOverview32',
    name: 'scooterOverview32',
    component: ScootersOverview32
  },
  {
    path: '/scooters/scooterOverview31',
    name: 'scooterOverview31',
    component: scootersOverview31
  },
  {
    path: '/:pageMatch(.*)*',
    name: '404',
    component: UnknownRouteComponent
  },{
    path: '/scooters',
    name: 'scooters',
    component: null

  }


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
