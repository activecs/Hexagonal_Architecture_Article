import axios from "axios";

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_ENDPOINT,
  timeout: import.meta.env.VITE_API_TIMEOUT,
});

const MoviesApi = (auth) => {
  axiosInstance.interceptors.request.use(
    async function (config) {
      if (auth && auth.getAccessToken()) {
        const accessToken = auth.getAccessToken();
        config.headers = {
          Authorization: `Bearer ${accessToken}`,
        };
      }
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
  );

  return {
    getPopular() {
      console.log(`getPopular base url: ${import.meta.env.VITE_API_ENDPOINT}`);
      return axiosInstance.get("/popular", {
        transformResponse: [
          function (data) {
            return data ? JSON.parse(data) : data;
          },
        ],
      });
    },

    getUpcoming() {
      return axiosInstance.get("/upcoming", {
        transformResponse: [
          function (data) {
            return data ? JSON.parse(data) : data;
          },
        ],
      });
    },
  };
};

export default MoviesApi;
