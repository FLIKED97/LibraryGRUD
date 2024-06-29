package org.example.controllers;

import org.example.dao.BookDao;
import org.example.dao.PersonDao;
import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BookService;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        Model model) {
        if (page != null && booksPerPage != null) {
            model.addAttribute("books",bookService.findAll(PageRequest.of(page, booksPerPage, Sort.by("date"))).getContent());
        } else {
            model.addAttribute("books", bookService.findAll());
        }
        return "books/books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Optional<Person> bookOwner = bookService.getBookOwner(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personService.findAll());
        }
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

    @GetMapping("/search")
    public String showSearchPage(Model model) {
        return "books/search";
    }

    @PostMapping("/search")
    public String searchBook(@RequestParam("name") String name, Model model) {
        Optional<Book> bookOptional = bookService.searchBookByName(name);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            model.addAttribute("book", book);
            model.addAttribute("bookFound", true);
            model.addAttribute("bookOwner", bookService.findBookOwner(book));
        } else {
            model.addAttribute("bookFound", false);
        }
        return "books/search";
    }

}
