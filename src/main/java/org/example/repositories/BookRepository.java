package org.example.repositories;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner_Id(int personId);
    Page<Book> findAll(Pageable pageable);

    Optional<Book> findByNameBookStartingWithIgnoreCase(String nameBook);

}
