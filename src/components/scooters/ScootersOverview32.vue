<template>
  <h5 class="section-title m-lg-3 mx-2 my-1">Scooter Details</h5>
  <div class="justify-content-center m-auto scooterOverview ">
    <div class="table-responsive">
      <table class="table table-dark">
        <thead>
        <tr>
          <th scope="col">Tag:</th>
        </tr>
        </thead>
        <tbody>
        <tr  v-for="scooter in scooterList" :key="scooter.id">
          <th scope="row" class="" @click="scooterClickHandler(scooter)">{{ scooter.tag }}</th>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="d-flex justify-content-end">
      <button type="button" class="btn newScooterButton" @click="createSampleScooterForList(1, scooterList[scooterList.length - 1].id)">New Scooter</button>
    </div>
  </div>
</template>

<script>
import {Scooter} from "@/models/Scooter";

export default {
  name: "ScootersOverview32",
  data(){
    return {
      scooterList: [],
      scooterStatus: Scooter.Status,
      clickedScooter: Scooter
    }
  },
  methods: {

    /**
     * This method will create a list of sample scooters
     * @param {number} amount The amount of scooters to create
     * @param {number} startId The id to start with
     * @author Marco de Boer
     */

    async createSampleScooterForList(amount, startId = 3000){
      for(let i = 0; i < amount; i++){
        startId = startId + Math.floor((Math.random() * 3) + 1);
        this.scooterList.push(await Scooter.createSampleScooter(startId));
      }
    },

    scooterClickHandler(clickedScooter){
      this.clickedScooter = clickedScooter;
      console.log(clickedScooter.id)
    }


  },

  /**
   * This method will create a sample list of scooters when the component is created
   */
  created () {
    this.createSampleScooterForList(8);
  }
}
</script>

<style scoped>

</style>