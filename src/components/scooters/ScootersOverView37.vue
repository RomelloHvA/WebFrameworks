<template>
  <div class="container my-3">
    <div v-if="error">
      <ErrorComponent :error="error"/>
    </div>
    <div v-else-if="isPending">
      <LoadingComponent/>
    </div>
    <div v-else>
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
                    v-for="scooter in scooters" :key="scooter.id">
                  <th scope="row" role="button">{{ scooter.tag }}</th>
                </tr>
                </tbody>
              </table>
            </div>
            <div class="d-flex justify-content-end">
              <button id="newButton" type="button" class="btn newScooterButton " @click="handleNewScooterClick" :disabled="newScooterIsPending">
                <div class="row d-flex">
                  <div class="col">
                    New Scooter
                  </div>
                  <div v-if="newScooterIsPending" class="col spinnerInButton p-0">
                    <div class="spinner-border text-light spinnerInButton" role="status">
                      <span class="sr-only"></span>
                    </div>
                  </div>
                </div>
              </button>
            </div>

          </div>
        </div>
        <div class="col-sm">
          <Suspense>
            <router-view @reloadScooters="loadFetch()"/>
          </Suspense>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Scooter} from "@/models/Scooter";
import router from "@/router";
import {inject, ref, watchEffect} from "vue";
import LoadingComponent from "@/components/LoadingComponent.vue";
import ErrorComponent from "../ErrorComponent.vue";
import {useToast} from 'vue-toast-notification';

export default {
  name: "ScootersOverview37",
  inject: ['scootersService'],
  components: { LoadingComponent, ErrorComponent },
  data() {
    return {
      scooterStatus: Scooter.Status,
      clonedScooter: null,
    }
  },

  async setup(){
    const scooterService = inject('scootersService')
    const loaded = ref(false)
    const {scooters, isPending, error, load } = await scooterService.asyncFindAll()
    const newScooterIsPending = ref(false)
    const newScooterError = ref(null)
    const selectedScooter = ref(null)
    const $toast = useToast();

    const loadFetch = () => {
      loaded.value = false
      load().then( () => {
        loaded.value = true
      })
    }
    loadFetch()

        /**
     *
     * Adds a new scooter and selects it immediatly afterwards in the table.
     * @author Romello ten Broeke
     */

    const handleNewScooterClick = async () => {
      const {scooter, isPending, error, load } = await scooterService.asyncSave(new Scooter(0))

      watchEffect(() => {
        newScooterIsPending.value = isPending.value;
        newScooterError.value = error.value;
      })

      load().then( async () => {
        if(error.value === null){
          await scooters.value.push(scooter.value)
          await onSelect(scooter.value)
        } else {
          $toast.error('Error while creating new scooter');
        }
      })

    }

        /**
     * This method checks if a scooter has been selected or if the same scooter is clicked again.
     * It will then either change the url to the scooter.id or remove the id from the url
     * @param {Scooter} scooter is the scooter that is selected with a mouseclick
     * @author Marco de Boer
     */
    const onSelect = async (scooter) => {
      if (scooter !== null && scooter !== selectedScooter.value) {
        router.push({ path: `${router.currentRoute.value.matched[0].path}/${scooter.id}` })
      } else if (selectedScooter.value === scooter){
        router.push({ path: `${router.currentRoute.value.matched[0].path}/${scooter.id}` });
      }
    }

    return { scooters, isPending, error, loaded, handleNewScooterClick, newScooterIsPending, newScooterError, onSelect, selectedScooter, loadFetch }
  },

  methods: {
    /**
     *
     * @param scooter to be compared to the currently selected scooter
     * @returns {boolean} returns true if the scooters are the same.
     * @author Romello ten Broeke
     */
    isActiveScooter(scooter) {
      return scooter === this.selectedScooter;
    },

    /** This function finds the scooter from the giving scooter and id in the list Scooters and returns it.
     *
     * @param {Number} scooterId
     * @author Marco de Boer
     */
    findSelectedFromRouteParam(scooterId) {
      return this.scooters.find(scooter => scooter.id == scooterId);
    }
  },
  /**
   * This method will create a sample list of scooters when the component is created
   *
   * @author Marco de Boer
   */
  async mounted() {
    watchEffect (() => {
      if (this.loaded){
        this.selectedScooter = this.findSelectedFromRouteParam(this.$route.params.id);
      }
    })   
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
.text-white {
  color: white;
}

.spinnerInButton {
  max-height: 25px;
  max-width: 25px;
  margin-right: 5px !important;
}

.nextButtonDiv{
  width: fit-content;
  height: fit-content;
}




</style>
