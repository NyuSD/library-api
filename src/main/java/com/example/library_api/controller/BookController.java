package com.example.library_api.controller;

import com.example.library_api.model.Book;
import com.example.library_api.model.LibraryBranch;
import com.example.library_api.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepo;

    public BookController(BookRepository bookRepo) { this.bookRepo = bookRepo; }

    @GetMapping
    public List<Book> all() { return bookRepo.findAll(); }

    @PostMapping
    public Book create(@Valid @RequestBody Book book) { return bookRepo.save(book); }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        return bookRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody Book updated) {
        return bookRepo.findById(id).map(b -> {
            b.setTitle(updated.getTitle());
            b.setAuthor(updated.getAuthor());
            b.setIsbn(updated.getIsbn());
            return ResponseEntity.ok(bookRepo.save(b));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!bookRepo.existsById(id)) return ResponseEntity.notFound().build();
        bookRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/branches")
    public ResponseEntity<Set<LibraryBranch>> branchesWithBook(@PathVariable Long id) {
        return bookRepo.findById(id)
                .map(b -> ResponseEntity.ok(b.getLibraryBranches()))
                .orElse(ResponseEntity.notFound().build());
    }
}
