import { useNavigate, useParams } from "react-router-dom";
import type { Book } from "../types/book";
import api from "../../../api/api";
import { useEffect, useState } from "react";

export default function EditBookPage() {
    const { id } = useParams<{ id: string }>();
    const [book, setBook] = useState<Book | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        api.get<Book>(`/api/admin/book/${id}`)
            .then(res => {
                setBook(res.data);
            })
            .catch(err => {
                console.error("Failed to fetch book:", err);
            })
            .finally(() => setLoading(false));
    }, [id]);

    function updateBook(updatedBook: Book) {
        const payload = {
            bookId: updatedBook.id,
            title: updatedBook.title,
            publishDate: updatedBook.publishDate,
            authorUsernames: updatedBook.authors.map(author => author.username),
        };

        api.put(`/api/admin/book/${updatedBook.id}`, payload)
            .then(res => {
                console.log("Book updated successfully:", res.data);
                navigate("/admin/books");
            })
            .catch(err => {
                console.error("Failed to update book:", err);
            });
    }


    return (
        <>
            <div className="container mt-5 border border-black rounded-4 p-4">
                <form>
                    <div className="form-group">
                        <label htmlFor="title">Title</label>
                        <input
                            type="text"
                            className="form-control"
                            id="title"
                            value={book ? book.title : ""}
                            placeholder="Enter title"
                            onChange={e => setBook(book ? { ...book, title: e.target.value } : null)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="publishDate">Publish Date</label>
                        <input
                            type="date"
                            className="form-control"
                            id="publishDate"
                            value={book ? book.publishDate.split("T")[0] : ""}
                            onChange={e => setBook(book ? { ...book, publishDate: new Date(e.target.value).toISOString() } : null)}
                        />
                    </div>
                    <button type="submit" className="btn btn-success mt-2" onClick={(e) => {
                        e.preventDefault();

                        if (!book) {
                            alert("Book data is not loaded!");
                            return;
                        }
                        
                        if (!book.title || book.title.trim() === "") {
                            alert("Title is required!");
                            return;
                        }

                        if (!book.publishDate) {
                            alert("Publish date is required!");
                            return;
                        }

                        updateBook(book)
                        
                    }}>Save</button>
                    <button type="button" className="btn btn-secondary mt-2 ms-2" onClick={() => navigate("/admin/books")}>Cancel</button>
                </form>
                {!book && !loading && <p className="text-warning">Failed to fetch book data!</p>}
            </div>
        </>
    );
}