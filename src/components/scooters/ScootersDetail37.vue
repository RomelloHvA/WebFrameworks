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
          <button type="button" @click="handleCancel()" class="btn btn-warning">Cancel</button>

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
import {inject, ref, watchEffect} from "vue";
import {useRoute} from "vue-router";
import {useToast} from 'vue-toast-notification';

export default {
  name: "ScootersDetail37",
  inject: ['scootersService'],
  components: { NoScooterSelectedComponent, LoadingComponent, ErrorComponent },

  data() {
    return {
      scooterStatus: Scooter.Status,
      scooterToDelete: null,
      scooterClone: null,
      cloneGpsLocation: null
    }
  },

  async setup(props,{ emit }){
    const loaded = ref(false)
    const scooterService = inject('scootersService')
    const route = useRoute()
    const routeScooterId = ref(route.params.id)
    const deleteIsPending = ref(false)
    const deleteError = ref(null)
    const saveScooterIsPending = ref(false)
    const saveScooterError = ref(null)
    const $toast = useToast()
    const preventRouterLeaveWarning = ref(false)

    const {scooter, isPending, error, load, scooterId } = await scooterService.asyncFindById(routeScooterId.value)

    /**
     * Loads the selected scooter and sets the loaded value to true when done loading. So the 
     */
    
    load().then( () => {
      loaded.value = true
    })
    

    /**
     * Deletes the selected scooter and navigates back to the overview page.
     * @author Marco de Boer
     */
    const deleteScooter = async () => {
      if(!window.confirm('Are you sure you want to delete this scooter?')) {
        return
      }
      const {isPending, error, load} = await scooterService.asyncDeleteById(scooter.value.id)

      watchEffect(() => {
        deleteIsPending.value = isPending.value;
        deleteError.value = error.value;
      })

      load().then( () => {
        if(error.value === null){
          router.push('/scooters/overview37')
          emit('reloadScooters')
        }
      })
    }

    const saveScooter = async (scooterToSave) => {
      const {isPending, error, load } = await scooterService.asyncSave(scooterToSave)

      watchEffect(() => {
        saveScooterIsPending.value = isPending.value;
        saveScooterError.value = error.value;
      })

      load().then( async () => {
        if(error.value === null){
          preventRouterLeaveWarning.value = true
          await emit('reloadScooters')
          await router.push('/scooters/overview37')
          preventRouterLeaveWarning.value = false
          $toast.success('Scooter: ' + scooterToSave.id + ' has been saved');
        } else {
          $toast.error('Error while saving scooter:' + scooterToSave.id);
        }
      })

    }

    return { scooter, isPending, error,load , loaded, scooterId, deleteScooter, deleteIsPending, deleteError, saveScooter, saveScooterIsPending, saveScooterError, preventRouterLeaveWarning}
  },

  methods: {
    /**
     * Clones the scooter, so it won't make direct changes to the selected scooter.
     * @author Romello ten Broeke
     */
    async cloneScooter() {
      if (this.scooter !== null) {
        this.scooterClone = await Scooter.cloneScooter(this.scooter)
      }
    },
    /**
     * Clears all the available scooter attributes by looping through all the keys in the object and setting
     * the key values to '' whilst still keeping the same id
     * Also initializes the scootergps location if there is none.
     * @author Romello ten Broeke
     */
     clearAllFields() {
      if (!this.confirmDiscardingChanges()) {
        return;
      }

      Object.keys(this.scooterClone).forEach(key => {
        if (key !== 'status' && key !== 'gpslocation' && key !== 'id') {
          this.scooterClone[key] = ''; // Set each property to an empty value
        }
      })

      if (!this.scooterClone.gpsLocation) {
        this.scooterClone.gpsLocation = {latitude: null, longitude: null};
      }
      this.scooterClone.gpsLocation.latitude = 0
      this.scooterClone.gpsLocation.longitude = 0
      this.scooterClone.status = 'UNAVAILABLE'
    },
    /**
     * Handles the cancel button. If the user has unsaved changes, it will ask the user if they want to discard the changes.
     * @author Marco de Boer
     */
    handleCancel () {
      if(!this.confirmDiscardingChanges()){
        return
      }
      this.pushRoute()
    },

    /**
     * Pushes this route. Helps to unselect scooters
     * @author Romello ten Broeke
     */
    pushRoute(){
      this.preventRouterLeaveWarning = true
      router.push('/scooters/overview37').then(() => {
        this.preventRouterLeaveWarning = false
      })
    },

    resetScooter(){
      if(!this.confirmDiscardingChanges()){
          return
        }

      this.cloneScooter()
    },
    /**
     * Confirms if the user wants to discard the changes made to the scooter.
     * @author Marco de Boer
     * @returns {boolean} true if the user wants to discard the changes, false if not.
     */
    confirmDiscardingChanges () {
      if (this.scooter === null || this.scooterClone === null) {
        return true
      }
      if (this.scooterClone.equals(this.scooter)) {
        return true;
      }

      if (window.confirm('You have unsaved changes, are you sure you want to discard those?')) {
          return true;
      }

      return false;
    }
  },
  
  beforeRouteLeave (to, from, next) {
    if (this.preventRouterLeaveWarning) {
      next()
    } else {

      if(!this.confirmDiscardingChanges()){
        next(false)
        return
      }
      next()
    } 
  },

  beforeRouteUpdate (to, from, next) {
    if (this.preventRouterLeaveWarning) {
      next()
    } else {
      if(!this.confirmDiscardingChanges()){
        next(false)
        return
      }
      next()
    }
  },

 mounted() {
    window.addEventListener('beforeunload', this.confirmDiscardingChanges)

    watchEffect(async () => {
      if (this.loaded) {
        await this.cloneScooter()
      }
    })


  },

  beforeUnmount() {
    window.removeEventListener('beforeunload', this.confirmDiscardingChanges)
  },

  watch: {
        /**
     * This watcher looks for changes to route and if there is a changes searches the id using the function
     * findSelectedFromRoute and sets the selectedScooter
     *
     * @author Marco de Boer
     */
    '$route' () {
      if(this.$route.params.id !== undefined){
        this.scooterId = this.$route.params.id
      }
    }
  },
      
  computed: {
    hasChanged() {
      return this.scooter.equals(this.scooterClone)
    },
    pendingBusy(){
      return this.deleteIsPending || this.saveScooterIsPending
    }
  },
  
}
</script>

