import {Scooter} from "@/models/Scooter.js";
import {useFetch} from "@/utils/useFetch.js";
import { ref, watch } from "vue";

export class ScootersAdaptor {

    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    async fetchJson(url, options = null) {
        let response = await fetch(url, options);
        if(response.ok){
            return await response.json();
        } else {
            return null
        }
    }

    async asyncFindAll() {
        // const scooters = await this.fetchJson(this.resourcesUrl + '/all');
        // return scooters?.map(s => Scooter.copyConstructor(s));
        const scooters = ref(null);
        const fetchIsPending = ref(true);
        const fetchError = ref(null);

        const { data, isPending, error} = useFetch(this.resourcesUrl + '/all')
        
        watch(data, (newValue) => {
            scooters.value = newValue?.map(s => Scooter.copyConstructor(s));
        })

        watch(isPending, (newValue) => {
            fetchIsPending.value = newValue;
        })

        watch(error, (newValue) => {
            fetchError.value = newValue;
        })

        return { scooters, fetchIsPending, fetchError}
    }

    async asyncFindById(id) {
        const scooter = await this.fetchJson(this.resourcesUrl + '/' + id);

        if (scooter === null) {
            return null;
        }

        return Scooter.copyConstructor(scooter);
    }

    // async asyncSave(scooter){
    //     const { data, isPending, error} = useFetch('/scooters/save', scooter, 'POST')
    // }

    // async asyncDeleteById(id){
    //     const { data, isPending, error} = useFetch('/scooters/delete/' + id, {}, 'DELETE')
    // }

}
