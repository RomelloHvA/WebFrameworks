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
            <router-view @reloadScooters="loadFetch()"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Scooter} from "@/models/Scooter";
import router from "@/router";
import {inject, ref, watchEffect, onBeforeMount} from "vue";
import LoadingComponent from "@/components/LoadingComponent.vue";
import ErrorComponent from "../ErrorComponent.vue";
import {useToast} from 'vue-toast-notification';
import { useRoute } from 'vue-router'

export default {
  name: "ScootersOverview37c",
  components: { LoadingComponent, ErrorComponent },
  data() {
    return {
      scooterStatus: Scooter.Status,
      clonedScooter: null,
    }
  },

  setup(){
    const scooterService = inject('scooterService2')
    const newScooterIsPending = ref(false)
    const newScooterError = ref(null)
    const selectedScooter = ref(null)
    const $toast = useToast();
    const scooters = ref([])
    const isPending = ref(false)
    const error = ref(null)
    const load = ref(null)
    const route = useRoute()

    /**
     * This method is called before the component is mounted and is a lifecycle hook
     * It will get all the data attributes from scooterService.asyncFindAll and save them in results
     * results will contain the following attributes: scooters, isPending, error, load
     * The results will be watched and the values will be saved in the data attributes of this component
     * The load function will be called and the selectedScooter will be set to the scooter with the id from the route param
     * @author Marco de Boer
     */
    onBeforeMount(async () => {
      const results = await scooterService.asyncFindAll()

      load.value = results.load

      watchEffect(() => {
        scooters.value = results.entities.value
        isPending.value = results.isPending.value
        error.value = results.error.value
      })

      load.value().then(
        findSelectedFromRouteParam(route.params.id)
      )
    })


    /**
     * This function will find the scooter with the id from the route param
     * @param {Number} scooterId is the id from the route param
     * @returns {Scooter} returns the scooter with the id from the route param or null if the scooterId is undefined
     * @author Marco de Boer
     */
    function findSelectedFromRouteParam (scooterId) {
      if (scooterId === undefined) {
        return null
      }

      return scooters.value.find(scooter => scooter.id === scooterId)
    }



    /**
     * This watchEffect will watch the route param and will set the selectedScooter to the scooter with the id from the route param
     */
    watchEffect(() => {
      selectedScooter.value = findSelectedFromRouteParam(route.params.id)
    })


    const loadFetch = () => {
      load.value()
    }

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
      if (scooter !== null && scooter !== selectedScooter.value || selectedScooter.value === scooter) {
        router.push({ path: `${router.currentRoute.value.matched[0].path}/${scooter.id}` })
      }
    }

    /**
     * @param scooter to be compared to the currently selected scooter
     * @returns {boolean} returns true if the scooters are the same.
     * @author Romello ten Broeke
     */
    const isActiveScooter = (scooter) => {
      return scooter === selectedScooter.value;
    }

    return { scooters, isPending, error, selectedScooter, newScooterIsPending, newScooterError, handleNewScooterClick, onSelect, isActiveScooter, loadFetch }
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
