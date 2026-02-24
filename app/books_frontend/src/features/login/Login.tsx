import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../service/authservice/AuthContext";

export default function Login() {
  const { login } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      await login(username, password);
      navigate("/admin");
    } catch {
      setError("Invalid username or password");
    }
  }

  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100">
      <div className="card border border-black d-flex flex-shrink-1 shadow-sm" style={{ width: "25rem" }}>
        <div className="card-header bg-primary d-flex allign-items-center justify-content-center">
          <label htmlFor="header" className="text-white">Welcome!</label>
        </div>
        <div className="card-body d-flex flex-column justify-content-center">
          <form onSubmit={handleSubmit}>

            <div className="form-group mb-2 mx-2 p-2">
              <label htmlFor="username">Username</label>
              <input
                type="text"
                className="form-control"
                id="username"
                value={username}
                placeholder="Enter username"
                onChange={e => setUsername(e.target.value)}
              />
            </div>

            <div className="form-group mb-2 mx-2 p-2">
              <label htmlFor="password">Password</label>
              <input 
                type="password"
                className="form-control"
                id="password"
                placeholder="Password"
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
            </div>
            
            <div className="d-flex justify-content-center">
              <button type="submit" className="btn btn-primary btn-sm">Log in</button>
            </div>

          </form>
        </div>
          {error && <p style={{color: "red"}}>{error}</p>}
      </div>
    </div>
    
  );
}
