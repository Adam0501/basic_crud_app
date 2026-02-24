import { useNavigate, useParams } from "react-router-dom";
import type { Author } from "../types/author";
import api from "../../../api/api";
import { useEffect, useState } from "react";
import Select from "react-select";
import type { Book } from "../../book/types/book";

export default function EditAuthorPage() {
    const { id } = useParams<{ id: string }>();
    const [author, setAuthor] = useState<Author | null>(null);
    const [books, setBooks] = useState<Book[]>([]);
    const [selectedBooks, setSelectedBooks] = useState<Book[] | undefined>(undefined);
    const [loading, setLoading] = useState<boolean>(true);
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        api.get<Author>(`/api/admin/author/${id}`)
            .then(res => {
                setAuthor(res.data);
                setSelectedBooks(res.data.books);
            })
            .catch(err => {
                console.error("Failed to fetch author: ", err);
            })
            .finally(() => setLoading(false));

        api.get<Book[]>(`/books`)
            .then(res => {
                setBooks(res.data);
            })
            .catch(err => {
                console.error("Failed to fetch books: ", err);
            });
    }, [id]);

    function updateAuthor(updatedAuthor: Author) {
        const payload = {
            id: updatedAuthor.id,
            username: updatedAuthor.username,
            name: updatedAuthor.name,
            email: updatedAuthor.email,
            admin: updatedAuthor.admin,
            bookIDList: updatedAuthor.books?.map(book => book.id),
        };

        api.put(`/api/admin/author`, payload)
            .then(res => {
                console.log("Author updated successfully:", res.data);
                navigate("/admin/authors");
            })
            .catch(err => {
                console.error("Failed to update author:", err);
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
                            id="authorName"
                            value={author ? author.name : ""}
                            placeholder="Enter full name"
                            onChange={e => setAuthor(author ? { ...author, name: e.target.value } : null)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="username">Username</label>
                        <input
                            type="text"
                            className="form-control"
                            id="authorUsername"
                            value={author ? author.username : ""}
                            onChange={e => setAuthor(author ? { ...author, username: e.target.value } : null)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            className="form-control"
                            id="authorEmail"
                            value={author ? author.email : ""}
                            onChange={e => setAuthor(author ? { ...author, email: e.target.value } : null)}
                        />
                    </div>
                    <div className="form-group form-check mt-2">
                        <input
                            type="checkbox"
                            className="form-check-input"
                            id="isAdmin"
                            checked={author ? author.admin : false}
                            onChange={e => setAuthor(author ? { ...author, admin: e.target.checked } : null)}
                        />
                        <label className="form-check-label" htmlFor="isAdmin">Is Admin</label>
                    </div>
                    <div className="form-group">
                        <label htmlFor="books">Books</label>
                        <Select
                            isMulti
                            options={books.map(b => ({ value: b.id, label: b.title}))}
                            value={selectedBooks?.map(b => ({ value: b.id, label: b.title}))}
                            onChange={options => {
                                const selected = options.map(o => books.find(b => b.id === o.value)!);
                                setSelectedBooks(selected);
                                setAuthor(author ? { ...author, books: selected} : null);
                            }}
                        />
                    </div>
                    <button type="submit" className="btn btn-success mt-2" onClick={(e) => {
                        e.preventDefault();

                        if (!author) {
                            alert("Author data is not loaded!");
                            return;
                        }
                        
                        if (!author.name || author.name.trim() === "") {
                            alert("Author name is required!");
                            return;
                        }

                        if (!author.username || author.username.trim() === "") {
                            alert("Username is required!");
                            return;
                        }

                        updateAuthor(author);
                        
                    }}>Save</button>
                    <button type="button" className="btn btn-secondary mt-2 ms-2" onClick={() => navigate("/admin/authors")}>Cancel</button>
                </form>
                {!author && !loading && <p className="text-warning">Failed to fetch author data!</p>}
            </div>
        </>
    );
}