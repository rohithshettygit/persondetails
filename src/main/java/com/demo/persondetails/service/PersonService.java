package com.demo.persondetails.service;

import com.demo.persondetails.model.Person;

import java.util.List;
import java.util.Map;

public interface PersonService {
    public  Map<String, List<Person>> getPeopleGroupByName();

    public  Map<String, Object> getPeopleByName(String Username);

    Map<String, Object> addPerson(Person person);

    Map<String,Object> addPeople(List<Person> people);
}
