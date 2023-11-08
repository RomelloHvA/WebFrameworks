import {RESTAdaptorWithFetch} from "@/services/RESTAdaptorWithFetch";

export class CachedRESTAdaptorWithFetch extends RESTAdaptorWithFetch {
    entities;

    constructor(resourcesUrl, copyConstructor) {
        super(resourcesUrl,copyConstructor);
        this.entities = [];

    }

    async asyncFindAll() {
        this.entities = await super.asyncFindAll();
        return this.entities;
    }

    async asyncFindById(id) {
        return super.asyncFindById(id);
    }

    async asyncSave(entityToSave) {
        return super.asyncSave(entityToSave);
    }

    async asyncDeleteById(id) {
        return super.asyncDeleteById(id);
    }
}
