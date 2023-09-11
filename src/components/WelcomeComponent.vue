<template>
  <main>
    <section class="brand-selector-section">
      <h4 class="section-title header-title">Brand selector:</h4>
      <select class="form-select w-auto m-lg-3" aria-label="Scooter brand selector">
        <option selected>Select a scooter brand!</option>
        <option>Option 1</option>
        <option>Option 2</option>
        <option>Option 3</option>
      </select>
    </section>

    <section class="photo-gallery-section ">
      <h4 class="section-title header-title">Photo gallery</h4>
    </section>

    <section class="city-info-section">
      <h4 class="section-title header-title">City info</h4>
    </section>
  </main>
</template>

<script>
export default {
  name: "WelcomeComponent"
}
</script>

<style>

.brand-selector-section, .city-info-section, .photo-gallery-section {
  flex: 1;
  max-width: 30vw;
  height: 100%;
  border-top: goldenrod 2px solid;
  color: azure;
  padding-top: 5px;

}

.photo-gallery-section {
  flex: 1.2;
  max-width: 40vw;
  border-left: goldenrod 2px solid;
  border-right: goldenrod 2px solid;

}

.section-title {
  color: goldenrod;
}

main {
  display: flex;
  justify-content: space-between;
}

</style>