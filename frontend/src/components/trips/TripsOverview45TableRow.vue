<template lang="">
    <tr  >
        <th scope="row">{{ trip.id }}</th>
        <td>{{ trip.startTime }}</td>
        <td>{{ trip.active }}</td>
        <td>{{ "N" + trip.startPosition.latitude + " E" + trip.startPosition.longitude }}</td>
        <td>
            <div>
                <div v-if="trip.endPosition !== null">
                    {{ "N" + trip.endPosition.latitude + " E" + trip.endPosition.longitude }}
                </div>
            </div>
        </td>
        <td>
            <div v-if="!trip.active">
                {{ trip.mileage + " km" }}
            </div>
            <div v-else>
                    <button @click="endTrip" type="button" class="btn btn-outline-light">End trip</button>
            </div>
        </td>
    </tr>
</template>
<script setup>
import { defineProps, ref } from 'vue'
import useFetch from '@/utils/useFetch';

const props = defineProps({
    tripfromparent: Object,
})

const trip = ref(props.tripfromparent);

const endTrip = () => {
    const response = useFetch('http://localhost:8085/trips/' + props.tripfromparent.id + '/end', null, 'PUT');
    response.load().then(() => {
        trip.value = response.data.value;
    });
}


</script>
<style>
    
</style>