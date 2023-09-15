<template>
  <div class="container">
    <div class="row">
      <div class="col-sm">
        <scooters-overview32/>
      </div>
      <div class="col-sm">
        <scooters-detail32/>
      </div>
    </div>
  </div>
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