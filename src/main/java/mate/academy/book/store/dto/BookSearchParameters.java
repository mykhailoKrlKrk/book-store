package mate.academy.book.store.dto;

public record BookSearchParameters(String[] titles, String[] authors, String[] isbns,
                                   String[] descriptions) {
}
