import type { ReactNode } from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

interface ProtectedRouteProps {
  children: ReactNode;
  role?: string;
}

export default function ProtectedRoute({ children, role }: ProtectedRouteProps) {
  const { user, loading } = useAuth();

  if (loading) {
        return <div>Loading user session...</div>;
  }

  if (!user || !user.roles || user.roles.length === 0) 
    return <Navigate to="/login" replace />;

  if (role && !user.roles.includes(role)) return <Navigate to="/" replace />;

  return <>{children}</>;
}
