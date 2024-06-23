package org.example.controllers;

import org.example.dao.BookDao;
import org.example.dao.PersonDao;
import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BookService;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookService.findAll());
        return "books/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {

        model.addAttribute("book", bookService.findOne(id));

        Optional<Person> bookOwner = bookService.getBookOwner(id);

        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", bookService.findAll());
        }

        //model.addAttribute("person", )
        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());

        return "books/new";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        //personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id){
        //personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/add/{id}")
    public String makeAdmin(@ModelAttribute("person") Person person,
                            @PathVariable("id") int id){
        bookService.addBookToPerson(person, id);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/delete/{id}")
    public String deleteBookForPerson(@PathVariable("id") int id){
        bookService.deleteBookForPerson(id);

        return "redirect:/books/{id}";
    }
}
