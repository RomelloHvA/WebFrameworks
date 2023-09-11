<template>
  <header class="header-container">
    <img src="../assets/headerLogoTransparant.png" alt="Logo" class="header-logo float-left">
    <img src="../assets/headerLogoTransparant.png" alt="Logo" class="header-logo float-right">
    <section class="header-text-container flex-column">
      <h2 class="header-title m-0">Electric scooter</h2>
      <h3 class="float-right">Subtext</h3>


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
  background-color: #343a40;
}

.header-text-container{
  color: goldenrod;
}

.header-logo {
height: 100%;
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

</style>