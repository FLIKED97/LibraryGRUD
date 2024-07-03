package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BookRepository;
import org.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    public Book findOne(int id) {
        Optional<Book> foundPerson = bookRepository.findById(id);
        return foundPerson.orElse(null);
    }
    @Transactional
    public List<Book> getBooksByPersonId(int personId) {
        return bookRepository.findByOwner_Id(personId);
    }
    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBook_id(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Person> getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner);
    }

    @Transactional
    public void addBookToPerson(Person person, int bookId) {
        // Retrieve the book by its ID
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        // Set the person to the book
        book.setOwner(person);

        book.setBorrowedDate(new Date());

        // Save the updated book entity
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBookForPerson(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setOwner(null);
        bookRepository.save(book);
    }

    public List<Book> findAll(Sort year) {
        return bookRepository.findAll(year);
    }


    public Optional<Book> searchBookByName(String name){
        return bookRepository.findByNameBookStartingWithIgnoreCase(name);
    }

    public Person findBookOwner(Book book) {
        return personRepository.findBookOwner(book);
    }
}
