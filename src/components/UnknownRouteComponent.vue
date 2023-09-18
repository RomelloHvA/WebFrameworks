<template>
<section class="page-not-found-section">
  <h1 class="section-title">Page not found</h1>
  <img class="not-found-img" src="../assets/pageNotFound.jpg">
  <p class="text-white">It seems the page you are looking for does not exist.</p>
  <router-link to="/"><button class="btn btn-info">Home</button></router-link>
</section>
</template>

<script>
export default {
  name: "PageNotFoundView"
}
</script>

<style >

.page-not-found-section {
  justify-content: center;
  margin-top: 20px;
  margin-left: 40vw;
}

.not-found-img {
  width: 30%;
  height: auto;
}

@media screen and (max-width: 750px){
  .page-not-found-section {
    margin-left: 5vw;
  }
}

</style>