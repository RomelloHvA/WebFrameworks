/** This is the Brand Selector Component
*  @name BrandSelectorComponent
*  @author Romello ten Broeken

*/

<template>
  <section class="brand-selector-section">
    <h4 class="section-title header-title">Brand selector:</h4>
    <select class="form-select w-auto m-lg-3 m-sm-3 mx-2" aria-label="Scooter brand selector">
      <option selected>Select a scooter brand!</option>
      <option>Option 1</option>
      <option>Option 2</option>
      <option>Option 3</option>
    </select>
    <h5 class="section-title m-lg-3 mx-2 my-1">Scooter availability:</h5>
    <table class="table table-hover table-dark">
      <thead>
      <tr>
        <th scope="col">Location</th>
        <th scope="col">Amount</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>Leidsesquare</td>
        <td class="bg-success">5</td>
      </tr>
      <tr>
        <td>Rembrandtsquare</td>
        <td class="bg-success">10</td>
      </tr>
      <tr>
        <td>Leidsesquare</td>
        <td class="bg-warning">2</td>
      </tr>
      <tr>
        <td>Rembrandtsquare</td>
        <td class="bg-danger">0</td>
      </tr>
      </tbody>
    </table>
  </section>
</template>
<script>
export default {
  name: 'BrandSelectorComponent'
}
</script>
