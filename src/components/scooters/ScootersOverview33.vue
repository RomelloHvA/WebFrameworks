<template>
  <div class="container">
    <div class="row">
      <div class="col-sm">
        <h5 class="section-title m-lg-3 mx-2 my-1">All scooter details</h5>
        <div class="justify-content-center m-auto">
          <div class="table-responsive">
            <table class="table table-dark table-hover">
              <thead>
              <tr>
                <th scope="col">Scooter tag:</th>
              </tr>
              </thead>
              <tbody>
              <tr :class="{ 'table-active': isActiveScooter(scooter) }" @click="onSelect(scooter)"
                  v-for="scooter in scooterList" :key="scooter.id">
                <th scope="row" role="button">{{ scooter.tag }}</th>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn newScooterButton"
                    @click="handleButtonClick">New Scooter
            </button>
          </div>
        </div>
      </div>
      <div class="col-sm">
        <router-view />
      </div>
    </div>
  </div>

</template>

<script>
import {Scooter} from "@/models/Scooter";

export default {
  name: "ScootersOverview33",
  components: {},
  data() {
    return {
      scooterList: [],
      scooterStatus: Scooter.Status,
      selectedScooter: null,

    }
  },
  methods: {

    /**
     * This method will create a list of sample scooters
     * @param {number} amount The amount of scooters to create
     * @param {number} startId The id to start with
     * @author Marco de Boer
     */

    async createSampleScooterForList(amount, startId = 3000) {
      for (let i = 0; i < amount; i++) {
        startId = startId + Math.floor((Math.random() * 3) + 1);
        this.scooterList.push(await Scooter.createSampleScooter(startId));
      }
    },

    /**
     * This method checks if a scooter has been selected or if the same scooter is clicked again.
     * It will then either set the activeScooterId to the selected scooter or to null if the same scooter is clicked.
     * @param scooter is the scooter that is selected with a mouseclick
     * @author Romello ten Broeke
     */

    onSelect(scooter) {
      if (scooter !== null && scooter !== this.selectedScooter) {
        this.$router.push(this.$route.matched[0].path + "/" + scooter.id);
      } else if (this.selectedScooter === null){
        this.$router.push({name: 'scooterOverview33'});
      }

    },
    /**
     *
     * @param scooter to be compared to the currently selected scooter
     * @returns {boolean} returns true if the scooters are the same.
     * @author Romello ten Broeke
     */
    isActiveScooter(scooter) {
      return scooter === this.selectedScooter;
    },
    /**
     *
     * Adds a new scooter and selects it immediatly afterwards in the table.
     * @author Romello ten Broeke
     */
    async handleButtonClick() {
      await this.createSampleScooterForList(1, this.scooterList[this.scooterList.length - 1].id);
      this.onSelect(this.scooterList[this.scooterList.length - 1 ]);
    },

    /**
     * Method for removing the scooter given by the child component.
     * @param scooterId of the scooter to be removed.
     * @author Romello ten Broeke
     */
    deleteScooterById(scooterId){
      this.onSelect(null);
      // if this was an === it would only keep the scooter that was supposed to be deleted.
      this.scooterList = this.scooterList.filter(scooter => scooter.id !== scooterId);
    },

    findSelectedFromRouteParam(scooterId) {
      return this.scooterList.find(scooter => scooter.id == scooterId);
    }


  },

  /**
   * This method will create a sample list of scooters when the component is created
   */
  created() {
    this.createSampleScooterForList(8);
  },

  watch: {
    '$route' () {
      this.selectedScooter = this.findSelectedFromRouteParam(this.$route.params.id);
    }
  }
}
</script>


<style scoped>

</style>