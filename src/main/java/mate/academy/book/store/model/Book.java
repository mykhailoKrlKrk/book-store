package mate.academy.book.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import mate.academy.book.store.validation.Isbn;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "author")
    private String author;

    @NotNull
    @Isbn
    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "price")
    @Min(0)
    private BigDecimal price;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "coverImage")
    private String coverImage;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}
