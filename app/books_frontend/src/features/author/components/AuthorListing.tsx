import type { Author } from "../../author/types/author";
import api from "../../../api/api";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import plusIcon from "../../../assets/plus.svg";

export default function AuthorListing() {
    const [authors, setAuthors] = useState<Author[]>([]);
    const [searchText, setSearchText] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        api.get<Author[]>("/api/admin/author")
        .then(res => {
            setAuthors(res.data);
        })
        .catch(err => {
            console.error("Error fetching books:", err);
        });
    }, []);

    function handleDelete(id: string) {
        api.delete("api/admin/author/" + id)
            .then(() => { setAuthors(prev => prev.filter(author => author.id !== id)); })
            .catch(err => {
                console.error("Error deleting book:", err);
        });
    }

    async function handleSearch(text: string) {
        const queryParams: Record<string, string> = {username : text};

        const queryString = new URLSearchParams(queryParams).toString();

        const searchUrl = `/search/author${queryString ? '?' + queryString : ''}`;

        api.get<Author[]>(searchUrl)
            .then(res => {
                setAuthors(res.data);
            })
            .catch(err => console.error("Search error:", err));
    }


    return (
        <>
            <div className="card rounded-top-4 rounded-bottom-0 mx-2 mb-2 border border-black margin-top-admin-navbar">
                <div className="card-body mx-0 my-0 p-0">
                    <div className="card-header rounded-top-4 text-white bg-primary d-flex flex-row justify-content-between align-items-center">
                        <h5>Authors</h5>
                        <div className="container p-0 m-0" style={{ width: "350px" }}>
                            <div className="row d-flex align-items-center">
                                <div className="col-1 me-2">
                                    <button className="btn btn-primary border-2 border-white d-flex justify-content-center align-items-center"
                                        style={{width: "31px", height: "31px"}}
                                        onClick={() => navigate("/admin/authors/new")}>
                                        <img
                                            src={plusIcon}
                                            alt="Add"
                                            style={{ width: "30px", height: "30px", filter: "invert(1)" }}
                                        />
                                    </button>
                                </div>
                                <div className="col-10">
                                    <form className="d-flex flex-row justify-content-between align-items-center">
                                        <div className="form-group flex-grow-1">
                                            <input
                                                type="text"
                                                className="form-control form-control-sm rounded-pill border-white"
                                                placeholder="Search authors by username..."
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
                                    <th scope="col" className="col-2 text-center">Name</th>
                                    <th scope="col" className="col-1 text-center d-none d-sm-table-cell">Username</th>
                                    <th scope="col" className="col-5 text-center">Books</th>
                                    <th scope="col" className="col-1 text-center">Is admin</th>
                                    <th scope="col" className="text-center">Tools</th>
                                </tr>
                            </thead>
                            <tbody className="">
                                {authors.map((author, index) => (
                                    <tr className="align-middle" key={author.id}>
                                        <th scope="row" className="align-self-start">{index + 1}</th>
                                        <td className="text-center">{author.name}</td>
                                        <td className="d-none d-sm-table-cell text-center">{author.username}</td>
                                        <td>{author.books?.length ? author.books.map(book => book.title).join(", ") : (<span className="text-danger">Author has no books</span>)}</td>
                                        <td className="text-center">{author.admin ? "Yes" : "No"}</td>
                                        <td className="text-center">
                                            <button
                                                className="btn btn-sm btn-primary me-1"
                                                onClick={() => navigate(`/admin/edit/author/${author.id}`)}
                                            >
                                                Edit
                                            </button>

                                            <button
                                                className="btn btn-sm btn-danger"
                                                onClick={() => {
                                                    if (author.admin) {
                                                        alert("Cannot delete admin author!");
                                                        return;
                                                    }

                                                    if (author.books && author.books.length > 0) {
                                                        alert("Cannot delete author with associated books!");
                                                        return;
                                                    }

                                                    handleDelete(author.id);
                                            }}>
                                                Delete
                                            </button>
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