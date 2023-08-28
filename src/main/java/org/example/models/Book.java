package org.example.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {

    private int book_id;
//    private int person_id;
    @NotEmpty(message = "поле повинно бути заповнене")
    @Size(max = 100, message = "Розмір повинен бути не більше 100 символів")
    private String name_book;
    @NotEmpty(message = "поле повинно бути заповнене")
    @Size(min = 20, max = 200, message = "Поле повинно бути мінімум від 20 до 200 символів")
    @Pattern(regexp = "[А-ЯІЇЄҐ][а-яіїєґ']+ [А-ЯІЇЄҐ][а-яіїєґ']+ [А-ЯІЇЄҐ][а-яіїєґ']+$", message = "повинно бути у формати Прізвище Ім'я По батькові")
    private String author;
    @NotEmpty(message = "поле повинно бути заповнене")
    @Size(max = 50, message = "Розмір повинен бути не більше 50 символів")
    @Pattern(regexp = "(0[1-9]|[12]\\d|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$", message = "поле повинно бути у форматі день.місяць.рік (01.02.2001)")
    private String date;

    public Book() {
    }

    public Book(int book_id, String name_book, String author, String date) {
        this.book_id = book_id;
        this.name_book = name_book;
        this.author = author;
        this.date = date;
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

//    public int getPerson_id() {
//        return person_id;
//    }
//
//    public void setPerson_id(int person_id) {
//        this.person_id = person_id;
//    }

    public String getName_book() {
        return name_book;
    }

    public void setName_book(String name_book) {
        this.name_book = name_book;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
