package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundPerson = bookRepository.findById(id);
        return foundPerson.orElse(null);
    }
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
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setOwner(person);
            bookRepository.save(book);
        } else {
            // Обробка випадку, коли книга не знайдена
            throw new RuntimeException("Book with id " + bookId + " not found");
        }
    }

    @Transactional
    public void deleteBookForPerson(int bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setOwner(null);
            bookRepository.save(book);
        } else {
            // Обробка випадку, коли книга не знайдена
            throw new RuntimeException("Book with id " + bookId + " not found");
        }
    }
}
