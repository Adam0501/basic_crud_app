import { useNavigate } from "react-router-dom";
import type { Book } from "../types/book";
import api from "../../../api/api";
import { useEffect, useState } from "react";
import type { Author } from "../../author/types/author";
import Select from "react-select";

export default function PublishBookPage() {
    const [book, setBook] = useState<Book>({
        id: "",
        title: "",
        publishDate: new Date().toISOString(),
        authors: []
    });
    const [authors, setAuthors] = useState<Author[]>([]);
    const [selectedAuthors, setSelectedAuthors] = useState<Author[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        api.get<Author[]>("/api/author")
        .then(res => setAuthors(res.data))
        .catch(err => console.error("Error fetching authors:", err));
    }, []);

    async function createBook(newBook: Book) {
        const payload = {
            id: newBook.id,
            title: newBook.title,
            publishDate: newBook.publishDate,
            authorUsernames: newBook.authors.map(author => author.username),
        };

        api.post("/api/author/me/book", payload)
            .then(res => {
                console.log("Book created successfully:", res.data);
                navigate("/author/books");
            })
            .catch(err => {
                console.error("Failed to create book:", err);
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
                            placeholder="Enter title"
                            onChange={e => setBook({ ...book, title: e.target.value })}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="publishDate">Publish Date</label>
                        <input
                            type="date"
                            className="form-control"
                            id="publishDate"
                            onChange={e => setBook({ ...book, publishDate: new Date(e.target.value).toISOString() })}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="authors">Add co-authors</label>
                        <Select
                            isMulti
                            options={authors.map(a => ({ value: a.id, label: a.name }))}
                            value={selectedAuthors.map(a => ({ value: a.id, label: a.name }))}
                            onChange={options => {
                                const selected = options.map(o => authors.find(a => a.id === o.value)!);
                                setSelectedAuthors(selected);
                                setBook({ ...book, authors: selected });
                            }}
                        />
                    </div>

                    <button type="submit"
                        className="btn btn-success mt-2"
                        onClick={(e) => { 
                            e.preventDefault();
                            
                            if (!book.title || book.title.trim() === "") {
                                alert("Title is required!");
                                return;
                            }

                            if (!book.publishDate) {
                                alert("Publish date is required!");
                                return;
                            }
                            
                            createBook(book);
                    }}>Create</button>
                    <button type="button" className="btn btn-secondary mt-2 ms-2" onClick={() => navigate("/author/books")}>Cancel</button>
                </form>
            </div>
        </>
    );
}