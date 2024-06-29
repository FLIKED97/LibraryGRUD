package org.example.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// Define the Book entity class and specify it is a JPA entity
@Entity
@Table(name = "Book")
public class Book {

    // Define the primary key and specify it should be auto-generated
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    // Define the name of the book with validation constraints
    @NotEmpty(message = "поле повинно бути заповнене")
    @Size(max = 100, message = "Розмір повинен бути не більше 100 символів")
    @Column(name = "name_book")
    private String nameBook;

    // Define the author of the book with validation constraints
    @NotEmpty(message = "поле повинно бути заповнене")
    //@Size(min = 20, max = 200, message = "Поле повинно бути мінімум від 20 до 200 символів")
    @Column(name = "author")
    private String author;

    // Define the publication date of the book with validation constraints
    @NotEmpty(message = "поле повинно бути заповнене")
    @Size(max = 50, message = "Розмір повинен бути не більше 50 символів")
    //@Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "поле повинно бути у форматі день.місяць.рік (01.02.2001)")
    @Column(name = "date")
    private String date;

    // Define the relationship with the Person entity (many books can belong to one person)
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    // Getter and setter for owner
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }


    public Book() {
    }

    public Book(int book_id, String nameBook, String author, String date) {
        this.book_id = book_id;
        this.nameBook = nameBook;
        this.author = author;
        this.date = date;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
