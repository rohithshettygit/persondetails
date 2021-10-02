package com.demo.persondetails.restcontroller;

import com.demo.persondetails.model.Person;
import com.demo.persondetails.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello, World";
    }

    @GetMapping("/getPeopleGroupByName")
    public  Map<String, List<Person>> getPeopleGroupByName(){
       return personService.getPeopleGroupByName();
    }

    @GetMapping("/peopleByName/{userName}")
    public  Map<String, Object> peopleByName(@PathVariable(name="userName",required = true) String userName){
        return personService.getPeopleByName(userName);
    }

    @PostMapping("/addPerson")
    public Map<String,Object> addPerson(@RequestBody Person person){
        return personService.addPerson(person);
    }

    @PostMapping("/addPeople")
    public Map<String,Object> addPerson(@RequestBody List<Person> person){
        return personService.addPeople(person);
    }
}
