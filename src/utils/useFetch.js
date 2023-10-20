import { ref, watchEffect, toValue } from "vue";

const url = "http://localhost:8089";

export function useFetch(endpoint, object , method = "GET") {

    const abortCont = new AbortController()

    const data = ref(null);
    const isPending = ref(true);
    const error = ref(null);

    const fetchData = () => {

        data.value = null
        isPending.value = true
        error.value = null

    fetch(toValue(`${url}${endpoint}` + `?${params}`,{
        signal: abortCont.signal,
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(object)
    }))
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
    }

    watchEffect(() => {
        fetchData()
    })
    
    return { data, isPending, error}
}
