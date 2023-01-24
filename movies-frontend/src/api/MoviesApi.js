import axios from "axios";

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_GATEWAY_SERVER_URI,
  timeout: 2000,
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
