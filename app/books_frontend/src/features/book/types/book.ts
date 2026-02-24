import type { Author } from "../../author/types/author";

export interface Book {
    id: string,
    title: string,
    publishDate: string,
    authors: Author[],
}
