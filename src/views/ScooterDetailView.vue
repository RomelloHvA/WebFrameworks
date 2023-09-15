<template>
  <scooters-overview32/>
  <scooters-detail32/>
</template>
<script>
import ScootersOverview32 from "@/components/scooters/ScootersOverview32";
import ScootersDetail32 from "@/components/scooters/ScootersDetail32";

export default {
  name: 'ScooterDetail',
  components: {
    ScootersOverview32, ScootersDetail32
  }
}
</script>