import {useFetch} from "@/utils/useFetch"

export class ScooterAdaptor {

    resourcesUrl;
    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    async asyncFindAll() {
        const { data, isPending, error} = useFetch('/scooters/all')
    }

    async asyncFindById(id) {
        const { data, isPending, error} = useFetch('/scooters/' + id)
    }

    async asyncSave(scooter){
        const { data, isPending, error} = useFetch('/scooters/save', scooter, 'POST')
    }
}
