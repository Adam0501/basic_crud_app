import type { Author } from "../../author/types/author";
import { useState } from "react";
import api from "../../../api/api";
import { useNavigate } from "react-router-dom";

export default function CreateAuthorPage() {
    const [authorData, setAuthorData] = useState<Author>({
        id: "",
        name: "",
        username: "",
        email: "",
        admin: false,
        books: [],
    });

    const navigate = useNavigate();

    async function createAuthor(newAuthor: Author) {
        api.post("/api/admin/author", newAuthor)
            .then(res => {
                console.log("Author created successfully: ", res.data);
                navigate("/admin/authors");
            })
            .catch(err => {
                console.log("Failed to create author: ", err);
            });
    }

    return (
        <>
            <div className="container mt-5 border border-black rounded-4 p-4">
                <form>
                    <div className="form-group">
                        <label htmlFor="fullName">Full Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="fullName"
                            placeholder="Enter name..."
                            onChange={e => setAuthorData({ ...authorData, name: e.target.value})}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="username">Username</label>
                        <input 
                            type="text"
                            className="form-control"
                            id="username"
                            placeholder="Enter username..."
                            onChange={e => setAuthorData({ ...authorData, username: e.target.value})}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input 
                            type="email"
                            className="form-control"
                            id="email"
                            placeholder="Enter email..."
                            onChange={e => setAuthorData({ ...authorData, email: e.target.value})}
                        />
                    </div>

                    <div className="form-group form-check mt-2">
                        <input
                            type="checkbox"
                            className="form-check-input"
                            id="isAdmin"
                            checked={authorData.admin}
                            onChange={e => setAuthorData({ ...authorData, admin: e.target.checked })}
                        />
                        <label className="form-check-label" htmlFor="isAdmin">Is Admin</label>
                    </div>

                    <button type="submit"
                        className="btn btn-success"
                        onClick={e => {
                            e.preventDefault();

                            if (!authorData.name || authorData.name.trim() === "") {
                                alert("Author name is required!");
                                return;
                            }

                            if (!authorData.username || authorData.username.trim() === "") {
                                alert("Username is required!");
                                return;
                            }

                            createAuthor(authorData);
                        }}>Create</button>

                    <button type="button" className="btn btn-secondary ms-2" onClick={() => navigate("/admin/authors")}>Cancel</button>
                </form>
            </div>
        </>
    );
}