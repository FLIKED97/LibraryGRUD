package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
        public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name_book, date, author) VALUES(?, ?, ?)",
                book.getName_book(), book.getDate(), book.getAuthor());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name_book=?, date=?, author=? WHERE book_id=?",
                book.getName_book(), book.getDate(),book.getAuthor(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public Optional <Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }
    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.book_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }


    public void addBookToPerson(Person person, int id) {
        jdbcTemplate.update("UPDATE book set person_id=? where book_id=?", person.getId(), id);
    }

    public void deleteBookForPerson(int id) {
        jdbcTemplate.update("update book set person_id = null where book_id = ?", id);
    }
    public Optional<Book> getPersonByPersonIdIsNull(int id){
        return jdbcTemplate.query("select b.* from book b join person p ON person_id = p.id where p.id = ? ", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }
    public List<Book> getBook(int id){
        return  jdbcTemplate.query("SELECT * FROM book  WHERE person_id = ?", new Object[]{id}
                , new BeanPropertyRowMapper<>(Book.class));
    }
}
