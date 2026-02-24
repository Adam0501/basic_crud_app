import { useAuth } from '../../../service/authservice/AuthContext';

export default function UserNavBar() {
    const { logout } = useAuth();

    async function handleLogout() {
        try {
            await logout();
        }
        catch (err) {
            console.error("Logout error:", err);
        }
    }

    return (
        <div className="container-fluid p-0 m-0 position-fixed top-0 z-2">
            <nav className="navbar navbar-expand-lg navbar-dark bg-primary p-2 mb-2 border-2 border-bottom border-dark">
                <a className="navbar-brand" href="#">User Panel</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0 d-flex align-items-center">
                            <li className="nav-item">
                                <a className="nav-link" href="/">Dashboard</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/author/books">Books</a>
                            </li>
                            <li className="nav-item">
                                <button className="btn btn-sm btn-warning" onClick={() => handleLogout()}>Log out</button>
                            </li>
                        </ul>
                    </div>
                
            </nav>
        </div>
    );
}