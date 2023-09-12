<template>
  <main>
    <BrandSelectorComponent/>

    <PhotoGalleryComponent/>

    <CityInfoComponent/>
  </main>
</template>

<script>
import BrandSelectorComponent from "@/components/mainContentComponents/BrandSelectorComponent";
import PhotoGalleryComponent from "@/components/mainContentComponents/PhotoGalleryComponent";
import CityInfoComponent from "@/components/mainContentComponents/CityInfoComponent";

export default {
  name: "WelcomeComponent",
  components: {CityInfoComponent, PhotoGalleryComponent, BrandSelectorComponent}
}
</script>

