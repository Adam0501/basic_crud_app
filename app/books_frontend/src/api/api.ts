import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8081",
});

let credentials: { username: string; password: string } | null = null;
const STORAGE_KEY = "authCreds";

try {
  const saved = localStorage.getItem(STORAGE_KEY);
  if (saved) {
    credentials = JSON.parse(saved);
  }
} catch (e) {
  // ignore
}

export function setCredentials(username: string, password: string) {
  credentials = { username, password };
  try {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(credentials));
  } catch (e) {
    // ignore
  }
}

export function clearCredentials() {
  credentials = null;
  try {
    localStorage.removeItem(STORAGE_KEY);
  } catch (e) {
    // ignore
  }
}

api.interceptors.request.use(config => {
  if (credentials) {
    const { username, password } = credentials;
    const token = btoa(`${username}:${password}`);
    config.headers['Authorization'] = `Basic ${token}`;
  }

  return config;
});

export default api;
