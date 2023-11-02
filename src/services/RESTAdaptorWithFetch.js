import {ref, watchEffect} from "vue";
import useFetch from "@/utils/useFetch";

export class RESTAdaptorWithFetch /* <E> */ {
    resourcesUrl;
    copyConstructor; // Constructor to copy from a json into the backend.

    constructor(resourcesUrl, copyConstructor) {
        this.resourcesUrl = resourcesUrl;
        this.copyConstructor = copyConstructor;
    }
    async findAll(){
        const entity = ref([])

        const { data, isPending, error, load } = await useFetch(this.resourcesUrl + '/all')

        watchEffect(() => {
            entity.value = data.value.map(entity => this.copyConstructor(entity))
        })

        return { entities: entity, isPending, error, load }
    }
    async findById(id){
        return await this.copyConstructor(fetch(`${this.resourcesUrl}/${id}`));}

    async save(entity){
        const newEntity = ref(entity);

        const { data, isPending, error, load, abort, isAborted } = await useFetch(this.resourcesUrl + '/' + newEntity.value.id, newEntity.value, 'POST')

        watchEffect(() => {
            newEntity.value = this.copyConstructor(data.value)
        })

        return { newEntity, isPending, error, load, abort, isAborted }

    }
    async delete(id){
        const entityId = ref(id)

        const { isPending, error, load, abort, isAborted } = await useFetch(this.resourcesUrl + '/' + entityId.value, {}, 'DELETE')

        return { isPending, error, load, abort, isAborted }

    }

}
