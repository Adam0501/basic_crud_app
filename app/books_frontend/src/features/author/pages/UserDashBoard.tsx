import booksImage from '../../../assets/books.avif';
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../../service/authservice/AuthContext";
import UserNavBar from "../components/UserNavBar";

export default function UserDashBoard() {
    const navigate = useNavigate();
    const { user } = useAuth();

    return (
        <>
            <UserNavBar />
            <div className="container-fluid margin-top-admin-navbar">
                <h1 className="mb-4">Welcome, {user?.username}</h1>
                <div className="container-fluid d-flex justify-content-center align-items-center flex-wrap gap-4 p-4">
                    <div className="card border-1 border-black shadow-sm"
                        style={{width:"20em", height: "16em"}}
                        onClick={() => navigate("/author/books")}
                        onMouseOver={(e) => (e.currentTarget.style.cursor = "pointer")}>
                        <img src={booksImage} alt="Books" />
                        <div className="card-body bg-success text-white d-flex justify-content-center align-items-center border-1 border-top border-black" style={{height: "2em"}}>
                            <span>Browse books</span>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}