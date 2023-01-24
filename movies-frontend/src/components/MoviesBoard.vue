<script>
import MovieBoardItem from "@/components/MovieBoardItem.vue";
import MoviesApi from "@/api/MoviesApi";
export default {
  name: "MoviesBoard",
  components: {
    MovieBoardItem,
  },

  data() {
    return {
      popularMovies: [],
      upcomingMovies: [],
    };
  },

  methods: {
    async getData() {
      try {
        MoviesApi()
          .getPopular()
          .then((response) => {
            this.popularMovies = response.data;
          });
        MoviesApi()
          .getUpcoming()
          .then((response) => {
            this.upcomingMovies = response.data;
          });
      } catch (error) {
        console.log(error);
      }
    },
  },

  beforeMount() {
    this.getData();
  },
};
</script>

<template>
  <div class="row">
    <div class="col-md-12">
      <h1>Movie Trends</h1>
      <p>Here are the top 10 movies of the week:</p>
      <ul>
        <li v-for="movie in popularMovies" :key="movie.id">
          <MovieBoardItem :movie="movie" />
        </li>
      </ul>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <h1>Movie New</h1>
      <p>Here are the upcoming 10 movies of the week:</p>
      <ul>
        <li v-for="movie in upcomingMovies" :key="movie.id">
          <MovieBoardItem :movie="movie" />
        </li>
      </ul>
    </div>
  </div>
</template>
