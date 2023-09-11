<template>
  <header class="header-container">
    <img src="../assets/headerLogoTransparant.png" alt="Logo" class="header-logo float-left">
    <img src="../assets/headerLogoTransparant.png" alt="Logo" class="header-logo float-right">
    <h1 class="header-title">Electric scooter title</h1>


  </header>
</template>

<script>
export default {
  name: "HeaderComponent"
}
</script>

<style>

.header-container{
  height: 10vh !important;
  width: 100% !important;
  background-color: #343a40;
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