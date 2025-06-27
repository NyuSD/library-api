package com.example.library_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property  = "id")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String title;
    @NotBlank private String author;

    @NotBlank
    @Column(unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "borrowedBooks")
    private Set<Member> borrowers = new HashSet<>();

    @ManyToMany(mappedBy = "books")
    private Set<LibraryBranch> libraryBranches = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Set<Member> getBorrowers() { return borrowers; }
    public void setBorrowers(Set<Member> borrowers) { this.borrowers = borrowers; }

    public Set<LibraryBranch> getLibraryBranches() { return libraryBranches; }
    public void setLibraryBranches(Set<LibraryBranch> libraryBranches) { this.libraryBranches = libraryBranches; }
}
