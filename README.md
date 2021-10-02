# persondetails
Simple SpringBoot Application
#rest apito store person details in a csv file
   #api to post individual user detail 
   ->http://localhost:8080/addPerson
  #sample data:
  
  {
    "name": "Amar",
    "dateOfBirth": "2021-01-01",
    "place": "Goa"
 }

  #api to post multiple user details
  ->http://localhost:8080/addPeople
  #sample data
  
  [{
    "name": "Amar",
    "dateOfBirth": "1951-01-01",
    "place": "Patna"

  },
  {
    "name": "Akbar",
    "dateOfBirth": "1980-01-01",
    "place": "Chennai"

  },
  {
    "name": "Anthony",
    "dateOfBirth": "1970-01-01",
    "place": "Delhi"

  }
  ]
  
# rest api	to get all the persons group by name from the csv file
  ->http://localhost:8080/getPeopleGroupByName
  
# rest api	to get all the persons with a given name from the csv file  
  ->http://localhost:8080//peopleByName/Amar
 
