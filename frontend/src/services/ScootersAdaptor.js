import {Scooter} from "@/models/Scooter.js";
import useFetch from "@/utils/useFetch";
import {ref, watch, watchEffect} from "vue";

/**
 * This class is used to fetch data from the API you give it the object if you want to send data to the API else just url
 * When you call the function it will return the data, isPending and error and function load
 * Load doesnt fire when calling this function you have to do that in your component
 * This is to keep track of isPending and error in your component
 * @param {String} resourcesUrl the endpoint your trying to fetch from for now has to add server ip and port manually
 * 
 */

export class ScootersAdaptor {

    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    /**
     * This function is used to call all scooters from the API
     * 
     * @returns {scooters, isPending, error, load} you need to make a const and destructure the return value
     * @author Marco de Boer
     */
    async asyncFindAll(){
        const scooters = ref([])

        const { data, isPending, error, load } = await useFetch(this.resourcesUrl + '/all')

        watchEffect(() => {
            scooters.value = data.value.map(s => Scooter.copyConstructor(s))
        })

        return { scooters, isPending, error, load }
    }

    /**
     * This function is used to get a scooter by id from the API
     * If you want to make a cal for a new id you have to change scooterId in your component
     * @param {Number} id  the id of the scooter you want to get
     * @returns {scooter, isPending, error, load, scooterId} you need to make a const and destructure the return value
     * @author Marco de Boer
     */
    async asyncFindById(id) {
        const scooter = ref(null)
        const scooterId = ref(id)
        
        const { data, isPending, error, load } = await useFetch(this.resourcesUrl + '/' + scooterId.value)

        const updateAndLoad = (newId) => {
            scooterId.value = newId;
            load(this.resourcesUrl + '/' + newId)
        }

        watchEffect(() => {
            scooter.value = Scooter.copyConstructor(data.value) 
        })

        watch(scooterId, (newId) => {
            updateAndLoad(newId);
        })

        return { scooter, isPending, error, load, scooterId }
    }

    /**
     * This function is used to save a scooter to the API or update it if it already exists
     * @param {Scooter} scooterToSave  the scooter you want to save
     * @returns  {scooter, isPending, error, load, abort, isAborted} you need to make a const and destructure the return value
     * @author Marco de Boer
     */
    async asyncSave(scooterToSave){
        const scooter = ref(scooterToSave)

        const { data, isPending, error, load, abort, isAborted } = await useFetch(this.resourcesUrl + '/' + scooter.value.id, scooter.value, 'POST')

        watchEffect(() => {
            scooter.value = Scooter.copyConstructor(data.value) 
        })

        return { scooter, isPending, error, load, abort, isAborted }
    }

    /**
     * This function is used to delete a scooter from the API
     * @param {Number} id the id of the scooter you want to delete
     * @returns {isPending, error, load} you need to make a const and destructure the return value
     * @author Marco de Boer
     */
    async asyncDeleteById(id){
        const scooterId = ref(id)

        const { isPending, error, load, abort, isAborted } = await useFetch(this.resourcesUrl + '/' + scooterId.value, {}, 'DELETE')

        return { isPending, error, load, abort, isAborted }
    }

}
