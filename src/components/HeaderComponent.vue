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
      <h3 class="float-right header-subtext">De scooter leveraar</h3>
    </section>


  </header>
</template>

<script>
export default {
  name: "HeaderComponent"
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
@media screen and (max-height: 470px) {
  .header-subtext{
    visibility: hidden;
  }
}

</style>