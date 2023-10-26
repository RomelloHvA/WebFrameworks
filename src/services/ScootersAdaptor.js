import {Scooter} from "@/models/Scooter.js";
import useFetch from "@/utils/useFetch";
import {ref, watch} from "vue";

export class ScootersAdaptor {

    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    async asyncFindAll(){
        const scooters = ref([])

        const { data, isPending, error, load } = useFetch(this.resourcesUrl + '/all')

        watch(data, (newVal) => {
            scooters.value = newVal.map(s => Scooter.copyConstructor(s))
        })

        return { scooters, isPending, error, load }
    }

    async asyncFindById(id) {
        const scooter = ref(null)
        const scooterId = ref(id)
        
        const { data, isPending, error, load } = useFetch(this.resourcesUrl + '/' + scooterId.value)

        const updateAndLoad = (newId) => {
            scooterId.value = newId;
            load(this.resourcesUrl + '/' + newId);
        }

        watch(scooterId, (newId) => {
            updateAndLoad(newId);
        })

        watch(data, (newVal) => {
            scooter.value = Scooter.copyConstructor(newVal)
        })

        return { scooter, isPending, error, load, scooterId }
    }

    async asyncSave(scooterToSave){
        const scooter = ref(scooterToSave)

        const { data, isPending, error, load } = useFetch(this.resourcesUrl + '/' + scooter.value.id, scooter.value, 'POST')

        watch(data, (newVal) => {
            scooter.value = Scooter.copyConstructor(newVal)
        })

        return { scooter, isPending, error, load }
    }

    async asyncDeleteById(id){
        const scooterId = ref(id)

        const { isPending, error, load } = useFetch(this.resourcesUrl + '/' + scooterId.value, {}, 'DELETE')

        return { isPending, error, load }
    }

}
