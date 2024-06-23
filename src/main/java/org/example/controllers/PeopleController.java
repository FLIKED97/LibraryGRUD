package org.example.controllers;

import org.example.dao.BookDao;
import org.example.dao.PersonDao;
import org.example.models.Person;
import org.example.services.BookService;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;
    private final BookService bookService;


    @Autowired
    public PeopleController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personService.findAll());

        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model/*,
                       @ModelAttribute("book") Person person*/) {
        model.addAttribute("person",  personService.findOne(id));
        model.addAttribute("books", bookService.getBooksByPersonId(id)); // Додавання списку книг до моделі

        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personService.findOne(id));
        return "people/edit";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());

        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        //personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "people/new";

        personService.save(person);
        return "redirect:/people";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id){
        //personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        personService.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }
}
