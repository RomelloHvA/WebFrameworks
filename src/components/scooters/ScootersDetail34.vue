<template>
  <section v-if="selectedScooter != null">
    <h5 class="section-title m-lg-3 mx-2 my-1">Scooter details (id= {{ selectedScooter.id }})</h5>
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
    <button type="button" :class="{ 'disabled' : !hasChanged}" @click="deleteScooter()"
            class="btn btn-danger">Delete
    </button>
    <button type="button" @click="clearAllFields()" class="btn btn-secondary m-1">Clear</button>
    <button type="button" :class="{ 'disabled' : hasChanged}" @click="resetScooter()" class="btn btn-secondary">Reset
    </button>
    <button type="button" :class="{ 'disabled' : hasChanged}" @click="saveScooter()" class="btn btn-success m-1">Save
    </button>
    <button type="button" @click="handleCancel()" class="btn btn-warning">Cancel</button>


  </section>
  <NoScooterSelectedComponent/>

</template>

<script>
import {Scooter} from "@/models/Scooter";
import router from "@/router";
import NoScooterSelectedComponent from "@/components/scooters/NoScooterSelectedComponent";

export default {
  name: "ScootersDetail34",
  components: {NoScooterSelectedComponent},
  props: {
    selectedScooter: Scooter,
    getScooter: Function
  },

  data() {
    return {
      scooterStatus: Scooter.Status,
      scooterToDelete: null,
      scooterClone: null,
      cloneGpsLocation: null,
      preventRouterLeaveWarning: false

    }
  },

  methods: {
    /**
     * Clones the scooter, so it won't make direct changes to the selected scooter.
     * @author Romello ten Broeke
     */
    cloneScooter() {
      if (this.selectedScooter !== null) {
        this.scooterClone = Scooter.cloneScooter(this.selectedScooter)
      }
    },
    /**
     * Deletes the selected scooter and unselects it in the route.
     * @author Romello ten Broeke
     */
    deleteScooter(){
      if(!window.confirm('Are you sure you want to delete this scooter?')) {
        return
      }

      this.getScooter(this.selectedScooter.id)
      this.pushRoute()
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

      this.scooterClone = {...this.scooterClone}; // Preserve the object reference
      Object.keys(this.scooterClone).forEach(key => {
        if (key !== 'status' && key !== 'gpslocation') {
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
      router.push('/scooters/overview34').then(() => {
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
      if (this.selectedScooter === null || this.scooterClone === null) {
        return true
      }
      if (this.scooterClone.equals(this.selectedScooter)) {
        return true;
      }

      if (window.confirm('You have unsaved changes, are you sure you want to discard those?')) {
          return true;
      }

      return false;
    },
    /**
     * Saves the new scooter values to the selected scooter
     * @author Romello ten Broeke
     */
    saveScooter() {
      Object.keys(this.scooterClone).forEach(key => {
        if (key !== 'id') {
          this.selectedScooter[key] = this.scooterClone[key]
          console.log('Copied to the selected scooter.')
        }
      })
      this.pushRoute()
    }
  },
  beforeRouteLeave (to, from, next) {
    console.log(this.preventRouterLeaveWarning)
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
  },
  beforeUnmount() {
    window.removeEventListener('beforeunload', this.confirmDiscardingChanges)
  },

  watch: {
    selectedScooter() {
      this.cloneScooter()
    }
  },
  computed: {
    hasChanged() {
      return this.selectedScooter.equals(this.scooterClone)
    }
  },
  /**
   * Method for cloning the selected scooter in an editable form.
   * @returns {Promise<void>}
   * @author Romello ten Broeke
   */
  async created() {
    this.cloneScooter()
  }

}
</script>

