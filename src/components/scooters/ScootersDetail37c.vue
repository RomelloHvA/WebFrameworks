<template>
  <div class="container my-3">
    <div v-if="error">
      <ErrorComponent :error="error"/>
    </div>
    <div v-else-if="isPending">
      <LoadingComponent/>
    </div>
    <div v-else>
      <section v-if="scooterClone != null">
        <h5 class="section-title m-lg-3 mx-2 my-1">Scooter details (id= {{ scooterClone.id }})</h5>
        <table class="table table-hover table-dark" id="content-to-blur" :class="{ 'blur-effect': pendingBusy }">
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
            <td><select class="form-select w-auto m-lg-3 m-sm-3 mx-2" aria-label="Scooter brand selector"
                        v-model="scooterClone.status">
              <option v-for="(value,key) in scooterStatus" :key="key">{{ value }}</option>

            </select>
            </td>

          </tr>
          <tr>
            <th scope="row">Battery Charge (%):</th>
            <td><input class="form-control form-control-sm" type="text" placeholder="Battery Charge"
                      v-model="scooterClone.batteryCharge"></td>

          </tr>
          <tr>
            <th scope="row">GPS Location:</th>
            <td><input class="form-control form-control-sm" type="text" placeholder="latitude"
                      v-model="scooterClone.gpsLocation.latitude">Latitude
              <input class="form-control form-control-sm" type="text" placeholder="longitude"
                    v-model="scooterClone.gpsLocation.longitude">Longitude
            </td>


          </tr>
          <tr>
            <th scope="row">Total Mileage</th>
            <td><input class="form-control form-control-sm" type="text" placeholder="Total mileage"
                      v-model="scooterClone.mileage"></td>

          </tr>
          </tbody>
        </table>
          <button type="button" :class="{ 'disabled' : !hasChanged || pendingBusy}"  @click="deleteScooter()" class="btn btn-danger">
            <div class="d-flex row">
            <div class="col">
              Delete
            </div>
            <div v-if="deleteIsPending" class="col spinnerInButton p-0">
              <div class="spinner-border text-light spinnerInButton" role="status">
                <span class="sr-only"></span>
              </div>
            </div>
            </div>
          </button>
          <button type="button" @click="clearAllFields()" class="btn btn-secondary m-1" :disabled="pendingBusy">Clear</button>
          <button type="button" :class="{ 'disabled' : hasChanged || pendingBusy}" @click="resetScooter()" class="btn btn-secondary">Reset</button>
          <button type="button" :class="{ 'disabled' : hasChanged || pendingBusy}" @click="saveScooter(scooterClone)" class="btn btn-success m-1">
            <div class="d-flex row">
            <div class="col">
              Save
            </div>
            <div v-if="saveScooterIsPending" class="col spinnerInButton p-0">
              <div class="spinner-border text-light spinnerInButton" role="status">
                <span class="sr-only"></span>
              </div>
            </div>
            </div>
          </button>
          <button type="button" @click="handleCancel()" class="btn btn-warning" :disabled="false">Cancel</button>

      </section>
      <section v-else>
        <NoScooterSelectedComponent/>
      </section>
    </div>
  </div>
</template>

<script>
import {Scooter} from "@/models/Scooter";
import router from "@/router";
import NoScooterSelectedComponent from "@/components/scooters/NoScooterSelectedComponent";
import LoadingComponent from "@/components/LoadingComponent.vue";
import ErrorComponent from "@/components/ErrorComponent.vue";
import {inject, ref, watchEffect, onBeforeMount, onMounted, onBeforeUnmount, watch, computed} from "vue";
import {useRoute, onBeforeRouteLeave, onBeforeRouteUpdate} from "vue-router";
import {useToast} from 'vue-toast-notification';

export default {
  name: "ScootersDetail37c",
  inject: ['scootersService'],
  components: { NoScooterSelectedComponent, LoadingComponent, ErrorComponent },

  data() {
    return {
      scooterStatus: Scooter.Status,
      scooterToDelete: null,
      cloneGpsLocation: null
    }
  },

  setup(props,{ emit }){
    const scooterService = inject('scooterService')
    const route = useRoute()
    const deleteIsPending = ref(false)
    const deleteError = ref(null)
    const saveScooterIsPending = ref(false)
    const saveScooterError = ref(null)
    const $toast = useToast()
    let preventRouterLeaveWarning = false
    const scooter = ref(null)
    const scooterId = ref(null)
    const isPending = ref(false)
    const error = ref(null)
    const load = ref(null)
    const scooterClone = ref(null)
    const abortDelete = ref(null)
    const abortSave = ref(null)

    /**
     * This method is called before the component is mounted and is a lifecycle hook
     * It will get all the data attributes from scooterService.asyncFindById and save them in results
     * results will contain the following attributes: scooters, isPending, error, load, scooterId
     * The results will be watched and the values will be saved in the data attributes of this component
     * The load function will be called and the selectedScooter will be set to the scooter with the id from the route param
     * The scooterId will be watched and if it changes the scooterId from results will be set to the new value and scooterservice will make a new fetch
     * @author Marco de Boer
     */
    onBeforeMount(async () => {
      const result = await scooterService.asyncFindById(route.params.id)

      load.value = result.load

      watchEffect(async () => {
        scooter.value = result.entity.value
        isPending.value = result.isPending.value
        error.value = result.error.value
        await cloneScooter()
      })

      watch(scooterId, (newVal) => {
        if(newVal !== undefined && newVal !== null){
          result.entityId.value = newVal
        }
      })

      load.value().then(() => {
        if(error.value === 'Could not fetch the data for that resource'){
          error.value = 'Could not find scooter with id: ' + route.params.id
        }
      })
    })

    /**
     * This watcher looks for changes to route and if there is a changes searches the id using the function
     * @author Marco de Boer
     */
    watch(() => route.params.id, (newVal) => {
      if(newVal !== undefined && newVal !== null){
        scooterId.value = newVal
      }
    })

    onMounted(() =>{
        window.addEventListener('beforeunload', confirmDiscardingChanges)
    })

    onBeforeUnmount( () => {
      window.removeEventListener('beforeunload', confirmDiscardingChanges)
    })

    onBeforeRouteLeave( async (to, from, next) => {
      if (preventRouterLeaveWarning) {
        next()
      } else {

        if(await !confirmDiscardingChanges()){
          next(false)
          return
        }
        next()
      }
    })

    onBeforeRouteUpdate( async (to, from, next) => {
      if (preventRouterLeaveWarning) {
        next()
      } else {
        if(await !confirmDiscardingChanges()){
          next(false)
          return
        }
        next()
      }
    })

    /**
     * Clones the scooter, so it won't make direct changes to the selected scooter.
     * @author Romello ten Broeke
     */
     async function cloneScooter() {
      if (scooter.value !== null) {
        scooterClone.value = await Scooter.cloneScooter(scooter.value)
      }
    }

    /**
     * Confirms if the user wants to discard the changes made to the scooter.
     * @author Marco de Boer
     * @returns {boolean} true if the user wants to discard the changes, false if not.
     */
    async function confirmDiscardingChanges () {
      if (scooter.value === null || scooterClone.value === null) {
        return true
      }
      if (await scooterClone.value.equals(scooter.value)) {
        return true;
      }

      if (window.confirm('You have unsaved changes, are you sure you want to discard those?')) {
          return true;
      }

      return false;
    }

    /**
     * This method will abort the save or delete request if there is one pending.
     * @returns {boolean} true if there was a pending request, false if not. This is used to prevent the router from navigating away if there is a pending request.
     * @author Marco de Boer
     */
    function handleAbort() {
      let aborted = false

      if(abortSave.value !== null && saveScooterIsPending.value){
        abortSave.value()
        aborted = true
      }

      if (abortDelete.value !== null && deleteIsPending.value){
        abortDelete.value()
        aborted = true
      }

      return aborted
    }

    /**
     * Pushes this route. Helps to unselect scooters
     * @author Romello ten Broeke
     */
     function pushRoute () {
      preventRouterLeaveWarning = true
      router.push('/scooters/overview37').then(() => {
        preventRouterLeaveWarning = false
      })
    }

    /**
     * Handles the cancel button. If the user has unsaved changes, it will ask the user if they want to discard the changes.
     * @author Marco de Boer
     */
    const handleCancel = () => {
      if(handleAbort()){
        return
      }

      if(!confirmDiscardingChanges()){
        return
      }
      pushRoute()
    }

    const resetScooter = () => {
      if(!confirmDiscardingChanges()){
          return
        }

      cloneScooter()
    }

    /**
     * Deletes the selected scooter and navigates back to the overview page.
     * It also emits an event to reload the scooters in the overview page.
     * @author Marco de Boer
     */

    const deleteScooter = async () => {
      if(!window.confirm('Are you sure you want to delete this scooter?')) {
        return
      }
      const {isPending, error, load, abort, isAborted} = await scooterService.asyncDeleteById(scooter.value.id)

      abortDelete.value = abort

      watchEffect(() => {
        deleteIsPending.value = isPending.value;
        deleteError.value = error.value;
      })


      load().then( () => {
        if(error.value === null && !isAborted.value){
          router.push('/scooters/overview37')
          emit('reloadScooters')
        } else if (!isAborted.value) {
          $toast.error('Error while deleting scooter:' + scooter.value.id);
        }
      })
    }

    /**
     * This method saves the scooterClone to the database and navigates back to the overview page.
     * It also emits an event to reload the scooters in the overview page.
     * @param {Scooter} scooterToSave
     * @author Marco de Boer
     */
    const saveScooter = async (scooterToSave) => {
      const {isPending, error, load, abort, isAborted } = await scooterService.asyncSave(scooterToSave)

      abortSave.value = abort

      watchEffect(() => {
        saveScooterIsPending.value = isPending.value;
        saveScooterError.value = error.value;
      })

      load().then( async () => {
        if(error.value === null && !isAborted.value){
          preventRouterLeaveWarning = true
          await emit('reloadScooters')
          await router.push('/scooters/overview37')
          preventRouterLeaveWarning = false
          $toast.success('Scooter: ' + scooterToSave.id + ' has been saved');
        } else if (!isAborted.value) {
          $toast.error('Error while saving scooter:' + scooterToSave.id);
        }
      })

    }

    /**
     * Clears all the available scooter attributes by looping through all the keys in the object and setting
     * the key values to '' whilst still keeping the same id
     * Also initializes the scootergps location if there is none.
     * @author Romello ten Broeke
     */
    const clearAllFields = () => {
      if (!confirmDiscardingChanges()) {
        return;
      }

      Object.keys(scooterClone.value).forEach(key => {
        if (key !== 'status' && key !== 'gpslocation' && key !== 'id') {
          scooterClone.value[key] = ''; // Set each property to an empty value
        }
      })

      if (!scooterClone.value.gpsLocation) {
        scooterClone.value.gpsLocation = {latitude: null, longitude: null};
      }
      scooterClone.value.gpsLocation.latitude = 0
      scooterClone.value.gpsLocation.longitude = 0
      scooterClone.value.status = 'UNAVAILABLE'
    }

    const hasChanged = computed(() => { return scooter.value.equals(scooterClone.value)})

    const pendingBusy = computed(() => { return deleteIsPending.value || saveScooterIsPending.value })

    return { scooter, isPending, error,load, scooterClone, deleteScooter, deleteIsPending, deleteError, saveScooter, saveScooterIsPending,
       saveScooterError, handleCancel, resetScooter, clearAllFields, hasChanged, pendingBusy
      }
  }
}
</script>

<style>
.blur-effect {
    filter: blur(1px);
    pointer-events: none;
    user-select: none;
}
</style>

