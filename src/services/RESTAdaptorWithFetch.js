import {ref, watch, watchEffect} from "vue";
import useFetch from "@/utils/useFetch";

export class RESTAdaptorWithFetch /* <E> */ {
    resourcesUrl;
    copyConstructor;

    constructor(resourcesUrl, copyConstructor) {
        this.resourcesUrl = resourcesUrl;
        this.copyConstructor = copyConstructor;
    }
    async asyncFindAll() {
        const entities = ref([])
        const {data, isPending, error, load } = await useFetch(this.resourcesUrl+ '/all')

        watchEffect(() => {
            entities.value = data.value.map(entity => this.copyConstructor(entity))
        })

        return {entities, isPending, error, load}
    }

    async asyncFindById(id) {
        const entity = ref(null)
        const entityId = ref(id)

        const {data, isPending, error, load} = await useFetch(this.resourcesUrl + '/' + id)

        const updateAndLoad = (newId) => {
            entityId.value = newId
            load(this.resourcesUrl + '/' + newId)
        }
        watchEffect(() => {
            entity.value = this.copyConstructor(data.value)
        })

        watch(entityId, (newId) => {
            updateAndLoad(newId);
        })

        return {entity, isPending, error, load, entityId}
    }

}
