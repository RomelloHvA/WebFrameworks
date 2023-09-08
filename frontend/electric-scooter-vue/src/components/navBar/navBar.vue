<template>
<ul>
  <nav-button navigation-item="Home"></nav-button>
  <nav-button navigation-item="About"></nav-button>
</ul>
</template>

<script>
import NavButton from "@/components/navBar/navButton";
export default {
  name: "navBar",
  components: {NavButton}

}
</script>

<style scoped>

</style>