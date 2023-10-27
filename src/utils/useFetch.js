import { ref } from "vue"

/**
 * This function is used to fetch data from the API you give it the object if you want to send data to the API else just url
 * When you call the function it will return the data, isPending and error and function load
 * Load doesnt fire when calling this function you have to do that in your component
 * This is to keep track of isPending and error in your component
 * @param {*} url the endpoint your trying to fetch from for now has to add server ip and port manually
 * @param {*} object you want to send to backend
 * @param {*} method GET is default but you can change it to POST, PUT, DELETE
 * @returns {data, isPending, error, load} you need to make a const and destructure the return value
 */
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

    /**
     * This function load makes the call to the endpoint
     * @param {*} newUrl if you want to make a new call to a different endpoint mostly used if you want to get different id for example
     */
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
