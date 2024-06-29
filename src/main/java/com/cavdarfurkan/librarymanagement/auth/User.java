package com.cavdarfurkan.librarymanagement.auth;

import com.cavdarfurkan.librarymanagement.model.book.Book;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private double money;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<Role>();


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id")
    private Set<Book> publishedBooks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "reader", fetch = FetchType.EAGER)
    private Set<Book> borrowedBooks = new LinkedHashSet<>();

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public Set<Book> getPublishedBooks() {
        return publishedBooks;
    }

    public void setPublishedBooks(Set<Book> publishedBooks) {
        this.publishedBooks = publishedBooks;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
