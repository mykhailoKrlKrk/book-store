package mate.academy.book.store.dto;

public record BookSearchParameters(String[] titles, String[] authors,int[] prices, String[] isbns,
                                   String[] descriptions) {
}
