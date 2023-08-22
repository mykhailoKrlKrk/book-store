package mate.academy.book.store.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.BookDto;
import mate.academy.book.store.dto.CreateBookRequestDto;
import mate.academy.book.store.service.BookService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
}
