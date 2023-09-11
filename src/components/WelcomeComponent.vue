<template>
  <section class="brand-selector-section">
    <h4 class="brand-title">Brand selector:</h4>
    <select>
      <option>Option 1</option>
      <option>Option 2</option>
      <option>Option 3</option>

    </select>
  </section>

</template>

<script>
export default {
  name: "WelcomeComponent"
}
</script>

<style>

.brand-selector-section{
  max-width: 30vw;
  border-top: goldenrod 2px solid;
  color: azure;
  padding-left: 10px;
  padding-top: 5px;

}

.brand-title {
  color: goldenrod;
}

</style>