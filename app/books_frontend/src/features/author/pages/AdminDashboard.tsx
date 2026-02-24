import AdminNavBar from "../components/AdminNavBar";
import booksImage from '../../../assets/books.avif';
import authorImage from '../../../assets/author.avif';
import { useNavigate } from "react-router-dom";

export default function AdminDashBoard() {
    const navigate = useNavigate();

    return (
        <>
            <AdminNavBar />
            <div className="container-fluid margin-top-admin-navbar">
                <h1 className="mb-4">Welcome to the Admin Dashboard</h1>
                <div className="container-fluid d-flex justify-content-center align-items-center flex-wrap gap-4 p-4">
                    <div className="card border-1 border-black shadow-sm"
                        style={{width:"20em", height: "16em"}}
                        onClick={() => navigate("/admin/books")}
                        onMouseOver={(e) => (e.currentTarget.style.cursor = "pointer")}>
                        <img src={booksImage} alt="Books" />
                        <div className="card-body bg-success text-white d-flex justify-content-center align-items-center border-1 border-top border-black" style={{height: "2em"}}>
                            <span>Manage books</span>
                        </div>
                    </div>

                    <div className="card border-1 border-black shadow-sm"
                        style={{width:"20em", height: "16em"}}
                        onClick={() => navigate("/admin/authors")}
                        onMouseOver={(e) => (e.currentTarget.style.cursor = "pointer")}>
                        <img src={authorImage} alt="Author" />
                        <div className="card-body bg-success text-white d-flex justify-content-center align-items-center border-1 border-top border-black" style={{height: "2em"}}>
                            <span>Manage authors</span>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}