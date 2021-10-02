package com.demo.persondetails.service;

import com.demo.persondetails.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonServiceImpl implements PersonService {
    public String greet() {
        return "Hello, World";
    }
    private static final String PERSON_DETAILS_FILE_PATH = "src/main/resources/templates/PersonDetails.csv";
    private static final String PERSON_DOB_DATE_FORMAT = "dd MMMM yyyy";

    @Override
    public Map<String, List<Person>> getPeopleGroupByName() {
        Map<String, List<Person>> peopleByName = new HashMap<>();
        List<Person> people = new ArrayList<>();
        people = read();
        peopleByName = people.stream().collect(Collectors.groupingBy(p -> p.getName()));
        return peopleByName;
    }

    @Override
    public Map<String, Object> getPeopleByName(String userName) {
        Map<String,Object> peopleByName = new HashMap<>();
        List<Person> people = new ArrayList<>();
        people = read().stream().filter(person -> person.getName().equals(userName)).collect(Collectors.toList());

        peopleByName.put("name",userName);

        if(people!=null && people.size()>0){
            peopleByName.put("records",people);
        }else{
            peopleByName.put("records","No Records Found");
        }
        return peopleByName;
    }

    @Override
    public Map<String, Object> addPerson(Person person) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", writePeople(person));
        return responseData;
    }

    @Override
    public Map<String, Object> addPeople(List<Person> people) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", writePeople(people));
        return responseData;
    }

    public List<Person> read() {
        List<Person> people = new ArrayList<>();
        Path path = Paths.get(PERSON_DETAILS_FILE_PATH);
        try (
                Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        ) {
            CSVFormat format = CSVFormat.RFC4180.withFirstRecordAsHeader();
            CSVParser parser = CSVParser.parse(reader, format);
            for (CSVRecord csvRecord : parser) {
                if (true) // Toggle parsing of the string data into objects. Turn off (`false`) to see strictly the time taken by Apache Commons CSV to read & parse the lines. Turn on (`true`) to get a feel for real-world load.
                {
                    people.add(new Person(csvRecord.get(0),
                            LocalDate.parse(csvRecord.get(1), DateTimeFormatter.ofPattern(PERSON_DOB_DATE_FORMAT)),
                            csvRecord.get(2)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return people;
    }


    public static String[] toArray(CSVRecord rec) {
        String[] arr = new String[rec.size()];
        int i = 0;
        for (String str : rec) {
            arr[i++] = str;
        }
        return arr;
    }


    public static void print(CSVPrinter printer, String[] s) throws Exception {
        for (String val : s) {
            printer.print(val != null ?val : "");
        }
        printer.println();
    }


    private boolean writePeople(Object people) {

        CSVParser parser = null;
        try {
            parser = new CSVParser(new FileReader(PERSON_DETAILS_FILE_PATH), CSVFormat.DEFAULT);


            List<CSVRecord> list = parser.getRecords();

            CSVPrinter printer = new CSVPrinter(new FileWriter(PERSON_DETAILS_FILE_PATH), CSVFormat.DEFAULT.withRecordSeparator("\n"));

            for (CSVRecord record : list) {
                String[] s = toArray(record);
                print(printer, s);
            }
            if (people instanceof List<?>){
                List<Person> people1= (List<Person>)people;
                people1.stream().forEach(person -> {
                    try {
                        printer.printRecord(person.getName(), DateTimeFormatter.ofPattern(PERSON_DOB_DATE_FORMAT).format(person.getDateOfBirth()), person.getPlace());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }else  if (people instanceof Person){
                Person person=(Person) people;
                printer.printRecord(person.getName(), DateTimeFormatter.ofPattern(PERSON_DOB_DATE_FORMAT).format(person.getDateOfBirth()), person.getPlace());

            }else{
                System.out.println("Invalid Parameter");
            }
            parser.close();
            printer.close();

            System.out.println("CSV file was updated successfully !!!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}

