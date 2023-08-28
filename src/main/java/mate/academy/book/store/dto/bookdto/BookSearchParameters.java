package mate.academy.book.store.dto.bookdto;

public record BookSearchParameters(String[] titles, String[] authors, String[] isbns,
                                   String[] descriptions) {
}
