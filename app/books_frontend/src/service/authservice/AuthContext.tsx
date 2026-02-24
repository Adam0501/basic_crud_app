import { createContext, useState, useEffect, useContext } from "react";
import api, { setCredentials, clearCredentials } from "../../api/api";
import type { AuthResponse } from "../authservice/auth_response";
import type { ReactNode } from "react";

interface AuthContextType {
  user: AuthResponse | null;
  loading: boolean;
  login: (username: string, password: string) => Promise<void>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthResponse | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    api.get<AuthResponse>("/api/me")
      .then(res => setUser(res.data))
      .catch(() => setUser(null))
      .finally(() => setLoading(false));
  }, []);

  const login = async (username: string, password: string) => {
    const form = new URLSearchParams();
    form.append("username", username);
    form.append("password", password);

    setCredentials(username, password);

    try {
      const me = await api.get<AuthResponse>("/api/me");
      setUser(me.data);
    } catch (err) {
      clearCredentials();
      throw err;
    }
  };

  const logout = async () => {
    clearCredentials();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth(): AuthContextType {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
}
