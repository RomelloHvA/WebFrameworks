import { ref } from "vue";

export function useFetch(url, object , method = "GET") {

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

    fetch(url, fetchOptions)
    .then( res => {
        if(!res.ok){
            throw Error('could not fetch the data for that resource');
        }
        return res.json();
    })
    .then(json => {
        data.value = json;
        isPending.value = false;
        error.value = null;
    })
    .catch((err) => {
        if(err.name !== 'AbortError'){
            isPending.value = false;
            error.value = err.message
        }else{
            console.log('fetch aborted');
        }   
    })
    
    return { data, isPending, error}
}
