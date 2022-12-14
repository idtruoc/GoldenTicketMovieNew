import axios from "axios";

export const baseURL = {
  user: "http://localhost:8080/api/auth",
};

const axiosClient = axios.create({
  headers: {
    "Content-Type": "application/json",
  },
});

axiosClient.interceptors.request.use(
  (req) => {
    const token = window.localStorage.getItem("accessToken");
    if (token) {
      req.headers.Authorization = `Bearer ${token}`;
    }
    return req;
  },
  function error() {
    return Promise.reject(error);
  }
);

// Add a response interceptor
axiosClient.interceptors.response.use(
  function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response.data;
  },
  async (error) => {
    const code = error.response.status;
    const msg = error.response.data?.msg;
    if (code && code === 401) {
      if (msg && msg === "jwt expired") {
        // console.log('this is case expired token case')
        // this is expired token case
        const config = error.response.config;
        //step 1 : retrieve new token from refresh token
        const newAccessToken = await refreshToken();
        if (newAccessToken) {
          config.headers.Authorization = `Bearer ${newAccessToken}`;
          //step 2 : store in local storage
          await window.localStorage.setItem("accessToken", newAccessToken);
          //step 3 : resend the request
          return axiosClient(config);
        } else {
          return Promise.reject(error);
        }
      }
    }
    return Promise.reject(error);
  }
);

const refreshToken = async () => {
  const refreshToken = window.localStorage.getItem("refreshToken");
  if (!refreshToken) {
    return false;
  }
  const res = await axiosClient.post(
    `${axiosClient.baseURL}/refreshToken`,
    {
      refreshToken,
    }
  );
  const data = res.data;
  const { newAccessToken } = data;
  return newAccessToken;
};

export { axiosClient };
