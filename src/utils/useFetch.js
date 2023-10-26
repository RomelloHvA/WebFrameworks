import { ref } from "vue";

const useFetch = (url, object, method = 'GET') => {
    const data = ref(null);
    const isPending = ref(true);
    const error = ref(null);

    const fetchOptions = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    }

    if (method !== 'GET' && object) {
        fetchOptions.body = JSON.stringify(object);
    }

    const load =  async (newUrl = url) => {
        isPending.value = true;
        try{
            const response = await fetch(newUrl, fetchOptions);
            if(!response.ok){
                throw Error('Could not fetch the data for that resource');
            }
            data.value = await response.json();
            error.value = null;
        } catch (err) {
            error.value = err.message;
        } finally {
            isPending.value = false;
        }
    }

    
    return { data, isPending, error, load }
}

export default useFetch
