package mate.academy.book.store.dto.book;

public record BookSearchParameters(String[] titles, String[] authors, String[] isbns,
                                   String[] descriptions) {
}
