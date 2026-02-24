import type { Book } from "../types/book";
import api from "../../../api/api";
import { useEffect, useState } from "react";
import { format } from "date-fns";
import { useNavigate } from "react-router-dom";
import filterIcon from "../../../assets/filter.svg";
import plusIcon from "../../../assets/plus.svg";
import { useAuth } from "../../../service/authservice/AuthContext";

export default function BookListing() {
    const [books, setBooks] = useState<Book[]>([]);
    const [filterBy, setFilterBy] = useState<"title" | "author" | "">("title");
    const [searchText, setSearchText] = useState("");
    const { user } = useAuth();

    const navigate = useNavigate();

    useEffect(() => {
        api.get<Book[]>("/books")
        .then(res => {
            setBooks(res.data);
        })
        .catch(err => {
            console.error("Error fetching books:", err);
        });
    }, []);

    function handleDelete(id: string) {
        let api_url: string = "";
        if (user?.roles.includes("ROLE_ADMIN")) {
            api_url = "/api/admin/book/" + id;
        } else {
            api_url = "/api/author/me/book/" + id;
        }
        api.delete(api_url)
        .then(() => { setBooks(prev => prev.filter(book => book.id !== id)); })
        .catch(err => {
            console.error("Error deleting book:", err);
        });
    }

    async function handleSearch(text: string) {
        const queryParams: Record<string, string> = {};

        if (filterBy === "title" && text.trim() !== "") {
            queryParams.title = text; 
        } else if (filterBy === "author" && text.trim() !== "") {
            queryParams.author = text;
        }

        const queryString = new URLSearchParams(queryParams).toString();
        console.log("Search query string:", queryString);

        const searchUrl = `/search${queryString ? '?' + queryString : ''}`;

        api.get<Book[]>(searchUrl)
            .then(res => {
                setBooks(res.data);
            })
            .catch(err => console.error("Search error:", err));
    }


    return (
        <>
            <div className="card rounded-top-4 rounded-bottom-0 mx-2 mb-2 border border-black margin-top-admin-navbar">
                <div className="card-body mx-0 my-0 p-0">
                    <div className="card-header rounded-top-4 text-white bg-primary d-flex flex-row justify-content-between align-items-center">
                        <h5>Books</h5>
                        <div className="container p-0 m-0" style={{ width: "350px" }}>
                            <div className="row d-flex align-items-center">
                                <div className="col-1 me-2">
                                    <button className="btn btn-primary border-2 border-white d-flex justify-content-center align-items-center"
                                        style={{width: "31px", height: "31px"}}
                                        onClick={() => {
                                            if (user?.roles.includes("ROLE_ADMIN")) {
                                                navigate("/admin/books/new");
                                            } else {
                                                navigate("/author/books/new");
                                            }                                            
                                        }}>
                                        <img
                                            src={plusIcon}
                                            alt="Add"
                                            style={{ width: "30px", height: "30px", filter: "invert(1)" }}
                                        />
                                    </button>
                                </div>
                                <div className="col-10">
                                    <form className="d-flex flex-row justify-content-between align-items-center">
                                        <div className="dropdown">
                                            <button 
                                                className="btn d-flex align-items-center justify-content-center rounded-start-3 p-2 rounded-end-0 border-white"
                                                type="button"
                                                data-bs-toggle="dropdown"
                                                aria-expanded="false"
                                                style={{ height: "31px" }}
                                            >
                                                <img 
                                                    src={filterIcon} 
                                                    alt="Filter" 
                                                    style={{ width: "20px", height: "20px", filter: "invert(1)" }} 
                                                />
                                            </button>

                                            <ul className="dropdown-menu">
                                                <li>
                                                    <button className="dropdown-item"
                                                        onClick={e => {
                                                            setFilterBy("title");
                                                            e.preventDefault();
                                                        }}>
                                                        Title
                                                    </button>
                                                </li>
                                                <li>
                                                    <button className="dropdown-item"
                                                        onClick={e => {
                                                            setFilterBy("author");
                                                            e.preventDefault();
                                                        }}>
                                                        Author
                                                    </button>
                                                </li>
                                            </ul>
                                        </div>
                                        <div className="form-group flex-grow-1">
                                            <input
                                                type="text"
                                                className="form-control form-control-sm rounded-start-0 rounded-end-pill border-white"
                                                placeholder={"Search books by " + filterBy + "..."}
                                                value = {searchText}
                                                onChange={e => {
                                                    const newValue = e.target.value;
                                                    setSearchText(newValue);
                                                    handleSearch(newValue);
                                                }}
                                            />
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="container-fluid mx-0 my-0 p-0">
                        <table className="table table-striped table-hover mb-0">
                            <thead>
                                <tr className="table-primary">
                                    <th scope="col">#</th>
                                    <th scope="col" className="col-2 text-center">Title</th>
                                    <th scope="col" className="col-1 text-center d-none d-sm-table-cell">Published</th>
                                    <th scope="col" className="col-5 text-center">Authors</th>
                                    <th scope="col" className="text-center">Tools</th>
                                </tr>
                            </thead>
                            <tbody className="">
                                {books.map((book, index) => (
                                    <tr className="align-middle" key={book.id}>
                                        <th scope="row" className="align-self-start">{index + 1}</th>
                                        <td className="text-center">{book.title}</td>
                                        <td className="d-none d-sm-table-cell text-center">{format(new Date(book.publishDate), "dd/MM/yyyy")}</td>
                                        <td>{book.authors.map(author => author.name).join(", ")}</td>
                                        <td className="text-center">
                                             {(user?.roles.includes("ROLE_ADMIN") ||
                                                book.authors.some(a => a.username === user?.username)) && (
                                                    <>
                                                        <button
                                                            className="btn btn-sm btn-primary me-1"
                                                            onClick={() => {
                                                                if (user?.roles.includes("ROLE_ADMIN")) {
                                                                    navigate(`/admin/edit/${book.id}`);
                                                                } else {
                                                                    navigate(`/author/edit/${book.id}`);
                                                                }
                                                            }}
                                                        >
                                                            Edit
                                                        </button>

                                                        <button
                                                            className="btn btn-sm btn-danger"
                                                            onClick={() => handleDelete(book.id)}
                                                        >
                                                            Delete
                                                        </button>
                                                    </>
                                                )}
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>          
                    </div>
                </div>
            </div>
        </>
    );

}