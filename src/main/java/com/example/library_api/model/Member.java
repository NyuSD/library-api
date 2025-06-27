package com.example.library_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String firstName;
    @NotBlank private String lastName;

    @Email @NotBlank
    @Column(unique = true)
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany
    @JoinTable(name = "member_borrowed_books",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> borrowedBooks = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "member_visited_branches",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id"))
    private Set<LibraryBranch> visitedBranches = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public Set<Book> getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(Set<Book> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public Set<LibraryBranch> getVisitedBranches() { return visitedBranches; }
    public void setVisitedBranches(Set<LibraryBranch> visitedBranches) { this.visitedBranches = visitedBranches; }
}
