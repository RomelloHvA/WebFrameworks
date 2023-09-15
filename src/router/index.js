import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ScooterDetailView from "@/views/ScooterDetailView";
import scootersOverview31 from "@/components/scooters/ScootersOverview31";

const routes = [
  {
    path: '/',
    name: 'homeView',
    component: HomeView
  },
  {
    path: '/scooterDetail',
    name: 'scooterDetail',
    component: ScooterDetailView
  },{
    path: '/scooterOverview',
    name: 'scooterOverview',
    component: scootersOverview31
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
