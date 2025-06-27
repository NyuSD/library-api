package com.example.library_api.controller;

import com.example.library_api.model.Book;
import com.example.library_api.model.LibraryBranch;
import com.example.library_api.repository.BookRepository;
import com.example.library_api.repository.LibraryBranchRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/branches")
public class LibraryBranchController {

    private final LibraryBranchRepository branchRepo;
    private final BookRepository bookRepo;

    public LibraryBranchController(LibraryBranchRepository branchRepo, BookRepository bookRepo) {
        this.branchRepo = branchRepo;
        this.bookRepo = bookRepo;
    }


    @GetMapping
    public List<LibraryBranch> all() { return branchRepo.findAll(); }

    @PostMapping
    public LibraryBranch create(@Valid @RequestBody LibraryBranch branch) { return branchRepo.save(branch); }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryBranch> get(@PathVariable Long id) {
        return branchRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryBranch> update(@PathVariable Long id,
                                                @Valid @RequestBody LibraryBranch updated) {
        return branchRepo.findById(id).map(branch -> {
            branch.setName(updated.getName());
            branch.setStreetAddress(updated.getStreetAddress());
            branch.setOpenHours(updated.getOpenHours());
            branch.setCity(updated.getCity());
            return ResponseEntity.ok(branchRepo.save(branch));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!branchRepo.existsById(id)) return ResponseEntity.notFound().build();
        branchRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{id}/books")
    public ResponseEntity<Set<Book>> booksInBranch(@PathVariable Long id) {
        return branchRepo.findById(id)
                .map(b -> ResponseEntity.ok(b.getBooks()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{branchId}/books/{bookId}")
    public ResponseEntity<Void> addBookToBranch(@PathVariable Long branchId, @PathVariable Long bookId) {
        LibraryBranch branch = branchRepo.findById(branchId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);
        if (branch == null || book == null) return ResponseEntity.notFound().build();
        branch.getBooks().add(book);
        branchRepo.save(branch);
        return ResponseEntity.ok().build();
    }
}
