/** This is the Header Component this will be shown on top of the page
*  @name HeaderComponent
*  @author Romello ten Broeken

*/

<template>
  <header class="header-container bg-dark">
    <img src="../assets/headerLogoTransparant.png" alt="Logo" class="header-logo float-left">
    <img src="../assets/headerLogoTransparant.png" alt="Logo" class="header-logo float-right">

    <section class="header-text-container">
      <h2 class="header-title">Electric scooter</h2>
      <div class="float-left header-subtext">
        <h5>Today is</h5>
        <h5>{{ currentDay + ' ' + currentDate }}</h5>
      </div>
      <h3 class="float-right header-subtext">De scooter leveraar</h3>
    </section>


  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
  data() {
    return {
      currentDay: new Date().toLocaleDateString('en-US', { weekday: 'long'}),
      currentDate: new Date().toLocaleDateString(),
    }
  },

  methods: {
    /**
     * This method will update the current date
     */
    updateCurrentDate(){
      this.currentDay = new Date().toLocaleDateString('en-US', { weekday: 'long'});
      this.currentDate = new Date().toLocaleDateString();
    },

    
  },

    // Update the current date every 60000 milliseconds aka 1 minute 

  mounted(){
      setInterval(this.updateCurrentDate, 60000);
    }

  
}
</script>

<style>

.header-container{
  height: 15vh !important;
  width: 100% !important;
}

.header-text-container{
  color: goldenrod;
  overflow-block: hidden;
  padding-bottom: 5px;
}

.header-logo {
  height: 15vh;
  width: auto;
}

.header-title {
  vertical-align: middle;
  text-align: center;
}

.float-left{
  float: left;
}

.float-right {
  float: right;
}
@media screen and (max-height: 640px) {
  .header-subtext{
    visibility: hidden;
  }
}

@media screen and (max-width: 500px){
  .header-subtext{
    visibility: hidden;
  }
}

</style>