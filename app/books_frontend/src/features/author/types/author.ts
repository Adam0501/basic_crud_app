import type { Book } from "../../book/types/book";

export interface Author {
    id: string,
    name: string,
    username: string,
    email: string,
    admin: boolean,
    books?: Book[],
}
