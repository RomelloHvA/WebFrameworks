import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import scootersOverview31 from "@/components/scooters/ScootersOverview31";
import ScootersOverview32 from "@/components/scooters/ScootersOverview32";
import ScootersOverview33 from "@/components/scooters/ScootersOverview33";
import ScootersDetail32 from "@/components/scooters/ScootersDetail32";
import UnknownRouteComponent from "@/components/UnknownRouteComponent";

export const routes = [
  {
    path: '/',
    name: 'homeView',
    component: HomeView
  }, {
    path: '/scooters/overview33',
    name: 'scooterOverview33',
    component: ScootersOverview33,
    children: [
      {
        path: ':id',
        name: 'scootersDetail32',
        component: ScootersDetail32,
        props: true
      }
    ]
  },
  {
    path: '/scooters/overview32',
    name: 'scooterOverview32',
    component: ScootersOverview32,
  },
  {
    path: '/scooters/overview31',
    name: 'scooterOverview31',
    component: scootersOverview31
  },
  {
    path: '/:pageMatch(.*)*',
    name: 'UnknownRouteComponent',
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
