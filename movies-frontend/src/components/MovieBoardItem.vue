<template>
  <div class="movie">
    <h2>{{ movie.title }}</h2>
    <p>{{ movie.synopsys }}</p>
    <p>Release date: {{ movie.releaseDate }}</p>
    <p>Rating: {{ movie.rate }}</p>
  </div>
</template>

<script>
export default {
  name: "MovieBoardItem",
  props: {
    movie: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped></style>
