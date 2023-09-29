import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import scootersOverview31 from "@/components/scooters/ScootersOverview31";
import ScootersOverview32 from "@/components/scooters/ScootersOverview32";
import ScootersOverview33 from "@/components/scooters/ScootersOverview33";
import ScootersDetail32 from "@/components/scooters/ScootersDetail32";
import UnknownRouteComponent from "@/components/UnknownRouteComponent";
import ScootersOverView34 from "@/components/scooters/ScootersOverView34";
import ScootersDetail34 from "@/components/scooters/ScootersDetail34";
import NoScooterSelectedComponent from "@/components/scooters/NoScooterSelectedComponent";

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
  },{
    path: '/scooters/overview34',
    name: 'scooterOverview34',
    component: ScootersOverView34,
    children: [
      {
        path: ':id',
        name: 'scootersDetail34',
        component: ScootersDetail34,
        props: true
      },
      {
        path: '',
        name: 'scootersNoDetail',
        component: NoScooterSelectedComponent
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
