package com.example.library_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LibraryBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String streetAddress;

    @NotBlank
    private String openHours;


    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany
    @JoinTable(name = "branch_books",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new HashSet<>();

    @ManyToMany(mappedBy = "visitedBranches")
    private Set<Member> visitors = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getOpenHours() { return openHours; }
    public void setOpenHours(String openHours) { this.openHours = openHours; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public Set<Book> getBooks() { return books; }
    public void setBooks(Set<Book> books) { this.books = books; }

    public Set<Member> getVisitors() { return visitors; }
    public void setVisitors(Set<Member> visitors) { this.visitors = visitors; }
}
