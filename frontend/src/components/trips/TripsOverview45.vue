<template>
    <div class="justify-content-center m-auto scooterOverview ">
        <div class="table-responsive">
            <table class="table table-dark">
                <thead>
                    <tr>
                        <th scope="col">Id:</th>
                        <th scope="col">Start date/time:</th>
                        <th scope="col">Active:</th>
                        <th scope="col">Start location:</th>
                        <th scope="col">EndLocation:</th>
                        <th scope="col">total milage:</th>
                    </tr>
                </thead>
                <tbody>
                    <TripsOverview45TableRow v-for="trip in trips" :key="trip.id" :tripfromparent="trip" />
                </tbody>
            </table>
        </div>
    </div>
</template>
<script setup>
import useFetch from '@/utils/useFetch';
import TripsOverview45TableRow from './TripsOverview45TableRow.vue';
import { ref } from 'vue'
const response = useFetch('http://localhost:8085/trips', null, 'GET');
const trips = ref(null);

response.load().then(() => {
    console.log(response.data.value)
    trips.value = response.data.value;
});

</script>
<style>

</style>
