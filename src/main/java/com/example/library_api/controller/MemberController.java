package com.example.library_api.controller;

import com.example.library_api.model.LibraryBranch;
import com.example.library_api.model.Member;
import com.example.library_api.model.Book;
import com.example.library_api.repository.BookRepository;
import com.example.library_api.repository.LibraryBranchRepository;
import com.example.library_api.repository.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepo;
    private final BookRepository bookRepo;
    private final LibraryBranchRepository branchRepo;

    public MemberController(MemberRepository memberRepo,
                            BookRepository bookRepo,
                            LibraryBranchRepository branchRepo) {
        this.memberRepo = memberRepo;
        this.bookRepo = bookRepo;
        this.branchRepo = branchRepo;
    }

    @GetMapping
    public List<Member> all() { return memberRepo.findAll(); }

    @PostMapping
    public Member create(@Valid @RequestBody Member m) { return memberRepo.save(m); }

    @GetMapping("/{id}")
    public ResponseEntity<Member> get(@PathVariable Long id) {
        return memberRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @Valid @RequestBody Member updated) {
        return memberRepo.findById(id).map(m -> {
            m.setFirstName(updated.getFirstName());
            m.setLastName(updated.getLastName());
            m.setEmail(updated.getEmail());
            m.setCity(updated.getCity());
            return ResponseEntity.ok(memberRepo.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!memberRepo.existsById(id)) return ResponseEntity.notFound().build();
        memberRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Set<Book>> borrowed(@PathVariable Long id) {
        return memberRepo.findById(id)
                .map(m -> ResponseEntity.ok(m.getBorrowedBooks()))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/branches")
    public ResponseEntity<Set<LibraryBranch>> visited(@PathVariable Long id) {
        return memberRepo.findById(id)
                .map(m -> ResponseEntity.ok(m.getVisitedBranches()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{memberId}/borrow/{bookId}")
    public ResponseEntity<Void> borrow(@PathVariable Long memberId, @PathVariable Long bookId) {
        Member m = memberRepo.findById(memberId).orElse(null);
        Book b = bookRepo.findById(bookId).orElse(null);
        if (m == null || b == null) return ResponseEntity.notFound().build();
        m.getBorrowedBooks().add(b);
        memberRepo.save(m);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{memberId}/visit/{branchId}")
    public ResponseEntity<Void> visit(@PathVariable Long memberId, @PathVariable Long branchId) {
        Member m = memberRepo.findById(memberId).orElse(null);
        LibraryBranch br = branchRepo.findById(branchId).orElse(null);
        if (m == null || br == null) return ResponseEntity.notFound().build();
        m.getVisitedBranches().add(br);
        memberRepo.save(m);
        return ResponseEntity.ok().build();
    }
}
