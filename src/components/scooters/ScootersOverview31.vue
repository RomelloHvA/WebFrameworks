/** This component will display a list of scooters with their current status, battery charge, gps location and mileage.
    *  It will also have a button to create a new scooter.
    *
    * @name ScootersOverviewComponent31
    * @author Marco de Boer
 */

<template>
    <div class="justify-content-center m-auto scooterOverview ">
        <div class="table-responsive">
            <table class="table table-dark">
                <thead>
                    <tr>
                        <th scope="col">Id:</th>
                        <th scope="col">Tag:</th>
                        <th scope="col">Status:</th>
                        <th scope="col">Battery Charge:</th>
                        <th scope="col">GPSLocation:</th>
                        <th scope="col">Milage:</th>
                    </tr>
                </thead>
                <tbody>
                    <tr  v-for="scooter in scooterList" :key="scooter.id">
                        <th scope="row">{{ scooter.id }}</th>
                        <td>{{ scooter.tag }}</td>
                        <td>{{ scooter.status }}</td>
                        <td>{{ scooter.batteryCharge + " %" }}</td>
                        <td v-if="scooter.status !== scooterStatus.IN_USE">{{ scooter.gpsLocation.latitude + "N, " + scooter.gpsLocation.longitude + "E"}}</td>
                        <td v-else>-</td>
                        <td>{{ scooter.mileage + " km" }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="d-flex justify-content-end">
            <button type="button" class="btn newScooterButton" @click="createSampleScooterList(1, scooterList[scooterList.length - 1].id)">New Scooter</button>
        </div>
    </div>
</template>

<script>
import { Scooter } from '@/models/Scooter.js';

export default {
    name: "ScootersOverviewComponent31",
    data(){
        return {
            scooterList: [],
            scooterStatus: Scooter.Status,
        }
    },
    methods: {

        /**
         * This method will create a list of sample scooters
         * @param {int} amount The amount of scooters to create
         * @param {int} startId The id to start with
         * @author Marco de Boer
         */

        async createSampleScooterList(amount, startId = 3000){
            for(let i = 0; i < amount; i++){
                startId = startId + Math.floor((Math.random() * 3) + 1);
                this.scooterList.push(await Scooter.createSampleScooter(startId));
            }
        },


    },

    /**
    * This method will create a sample list of scooters when the component is created
    */
    created () {
            this.createSampleScooterList(8);
            console.log(this.scooterList);
        }
    
}

</script>

<style>
    .scooterOverview{
        width: 90%;
        margin-bottom: 100px !important;
        margin-top: 30px !important;
    }

    .newScooterButton{
        background-color: goldenrod !important;
        color: white !important;
    }

    .newScooterButton:hover{
        background-color: rgb(181, 131, 5) !important;
        color: white !important;
    }
</style>