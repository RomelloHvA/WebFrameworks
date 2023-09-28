<template>
  <section v-if="selectedScooter != null">
    <h5  class="section-title m-lg-3 mx-2 my-1">Scooter details (id= {{selectedScooter.id }})</h5>
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
        <td><input class="form-control form-control-sm" type="text" placeholder="Tag" v-model="scooterClone.tag"></td>

      </tr>

      <tr>
        <th scope="row">Status:</th>
        <td><select class="form-select w-auto m-lg-3 m-sm-3 mx-2" aria-label="Scooter brand selector" v-model="scooterClone.status">
          <option v-for="(value,key) in scooterStatus" :key="key">{{ value }}</option>

        </select>
        </td>

      </tr>
      <tr>
        <th scope="row">Battery Charge (%):</th>
        <td><input class="form-control form-control-sm" type="text" placeholder="Battery Charge" v-model="scooterClone.batteryCharge"></td>

      </tr>
      <tr>
        <th scope="row">GPS Location:</th>
        <td><input class="form-control form-control-sm" type="text" placeholder="latitude" v-model="scooterClone.gpsLocation.latitude">Latitude
          <input class="form-control form-control-sm" type="text" placeholder="longitude" v-model="scooterClone.gpsLocation.longitude">Longitude
        </td>


      </tr>
      <tr>
        <th scope="row">Total Mileage</th>
        <td><input class="form-control form-control-sm" type="text" placeholder="Total mileage" v-model="scooterClone.mileage"></td>

      </tr>
      </tbody>
    </table>
    <button type="button" @click="getScooter(selectedScooter.id)" class="btn btn-danger">Delete</button>
    <button type="button" @click="clearAllFields()" class="btn btn-secondary">Clear</button>

  </section>
  <h5 v-else class="section-title">Please select a scooter.</h5>

</template>

<script>
import { Scooter } from "@/models/Scooter";

export default {
  name: "ScootersDetail34",
  props: {
    selectedScooter: Scooter,
    getScooter: Function
  },

  data(){
    return {
      scooterStatus: Scooter.Status,
      scooterToDelete: null,
      scooterClone: null

    }
  },

  methods: {
    /**
     * Clones the scooter, so it won't make direct changes to the selected scooter.
     * @author Romello ten Broeke
     */
    cloneScooter() {
      this.scooterClone = Scooter.cloneScooter(this.selectedScooter)
    },
    /**
     * Clears all the available scooter attributes by looping through all the keys in the object and setting
     * the key values to '' whilst still keep
     * @author Romello ten Broeke
     */
    clearAllFields() {
      this.scooterClone = { ...this.scooterClone }; // Preserve the object reference
      Object.keys(this.scooterClone).forEach(key => {
        if (key !== 'status'){
          this.scooterClone[key] = ''; // Set each property to an empty value
        }
      });
      this.scooterClone.status = 'UNAVAILABLE'
    }
  },

  watch: {
    selectedScooter() {
      this.cloneScooter()
    }
  },
  /**
   * Method for cloning the selected scooter in an editable form.
   * @returns {Promise<void>}
   * @author Romello ten Broeke
   */
    async created(){
      this.cloneScooter()
    }

}
</script>

<style>

</style>