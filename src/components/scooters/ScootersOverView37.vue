<template>
  <div>

  </div>
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
        <router-view :selected-scooter="selectedScooter" :getScooter="deleteScooterById"/>
      </div>
    </div>
  </div>

</template>

<script>
import {Scooter} from "@/models/Scooter";
import router from "@/router";
import {useFetch} from "@/utils/useFetch";
import {watchEffect} from "vue";

export default {
  name: "ScootersOverview37",
  components: {},
  data() {
    return {
      scooterList: [],
      scooterStatus: Scooter.Status,
      selectedScooter: null,
      clonedScooter: null,
      data: null,
      isPending: false,
      error: null

    }
  },

  methods: {

    async asyncfindAll() {
      const { data, isPending, error } = await useFetch('/scooters')
      this.data = data.value
      this.isPending = isPending.value
      this.error = error.value

      watchEffect(() => {
        this.data = data.value
        this.isPending = isPending.value
        this.error = error.value
      })
    },

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
     * It will then either change the url to the scooter.id or remove the id from the url
     * @param {Scooter} scooter is the scooter that is selected with a mouseclick
     * @author Marco de Boer
     */

    onSelect(scooter) {
      if (scooter !== null && scooter !== this.selectedScooter) {
        this.$router.push(this.$route.matched[0].path + "/" + scooter.id);
      } else if (this.selectedScooter === scooter){
        router.push('/scooters/overview34')
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
     * @param  {Number} scooterId of the scooter to be removed.
     * @author Marco de Boer
     */
    deleteScooterById(scooterId){
      this.onSelect(null);
      // if this was an === it would only keep the scooter that was supposed to be deleted.
      this.scooterList = this.scooterList.filter(scooter => scooter.id !== scooterId);
      this.selectedScooter = null;
      this.onSelect(null);
    },


    /** This function finds the scooter from the giving scooter and id in the list Scooters and returns it.
     *
     * @param {Number} scooterId
     * @author Marco de Boer
     */
    findSelectedFromRouteParam(scooterId) {
      return this.scooterList.find(scooter => scooter.id == scooterId);
    }


  },

  /**
   * This method will create a sample list of scooters when the component is created
   *
   * @author Marco de Boer
   */
  async created() {
    await this.createSampleScooterForList(8);
    await this.asyncfindAll()
    this.selectedScooter = this.findSelectedFromRouteParam(this.$route.params.id);
  },

  watch: {

    /**
     * This watcher looks for changes to route and if there is a changes searches the id using the function
     * findSelectedFromRoute and sets the selectedScooter
     *
     * @author Marco de Boer
     */
    '$route' () {
      this.selectedScooter = this.findSelectedFromRouteParam(this.$route.params.id);
    }
  }
}
</script>


<style >

</style>
