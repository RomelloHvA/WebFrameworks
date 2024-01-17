<template>
  <section v-if="selectedScooter != null">
  <h5  class="section-title m-lg-3 mx-2 my-1">Scooter details (id= {{selectedScooter.id}})</h5>
  <table class="table table-hover table-dark">
    <thead>
    <tr>
      <th scope="col">Property</th>
      <th scope="col">Value</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <th scope="row">Tag:</th>
      <td><input class="form-control form-control-sm" type="text" placeholder="Tag" v-model="selectedScooter.tag"></td>

    </tr>

    <tr>
      <th scope="row">Status:</th>
      <td><select class="form-select w-auto m-lg-3 m-sm-3 mx-2" aria-label="Scooter brand selector" v-model="this.selectedScooter.status">
        <option v-for="(value,key) in scooterStatus" :key="key">{{ value }}</option>

      </select>
      </td>

    </tr>
    <tr>
      <th scope="row">Battery Charge (%):</th>
      <td><input class="form-control form-control-sm" type="text" placeholder="Battery Charge" v-model="selectedScooter.batteryCharge"></td>

    </tr>
    <tr>
      <th scope="row">GPS Location:</th>
      <td><input class="form-control form-control-sm" type="text" placeholder="latitude" v-model="selectedScooter.gpsLocation.latitude">Latitude
        <input class="form-control form-control-sm" type="text" placeholder="longitude" v-model="selectedScooter.gpsLocation.longitude">Longitude
      </td>


    </tr>
    <tr>
      <th scope="row">Total Mileage</th>
      <td><input class="form-control form-control-sm" type="text" placeholder="Total mileage" v-model="selectedScooter.mileage"></td>

    </tr>
    </tbody>
  </table>
    <button type="button" @click="getScooter(selectedScooter.id)" class="btn btn-danger">Delete Scooter</button>
  </section>
  <h5 v-else class="section-title">Please select a scooter.</h5>

</template>

<script>
import {Scooter, } from "@/models/Scooter";

export default {
  name: "ScootersDetail32",
  props: {
    selectedScooter: Scooter,
    getScooter: Function
  },

  data(){
    return {
      scooterStatus: Scooter.Status,
      scooterToDelete: null,

    }
  },
}
</script>

<style>

</style>