/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import exceptions.NoObjectException;
import facades.HobbyFacade;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Christian & Brandstrup
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020CA2_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final HobbyFacade hFACADE = HobbyFacade.getHobbyFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(
            {
                MediaType.APPLICATION_JSON
            })
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonCount() {
        long count = FACADE.getPersonCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersons() {
        List<PersonDTO> pDTOList = FACADE.getAllPersons();
        if (pDTOList != null) {
            return GSON.toJson(pDTOList);
        } else {
            return "{\"msg\":\"Operation getAllPersons failed\"}";
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonById(@PathParam("id") int id) {
        try {
            PersonDTO pDTO = FACADE.getPersonDTOById(id);
            return GSON.toJson(pDTO);
        } catch (NoObjectException ex) {
            return ex.getMessage();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String persistPerson(String personDTO) {
        PersonDTO pDTO = GSON.fromJson(personDTO, PersonDTO.class); //Converts the request from a JSON string to a DTO
        Person pManaged = FACADE.persistPerson( //Persists the object to the database
                new Person(pDTO.getFirstName(), pDTO.getLastName(), pDTO.getEmail()));
        return GSON.toJson(new PersonDTO(pManaged));                //Returns the managed object as a DTO
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePerson(String personDTO) {
        PersonDTO hDTO = GSON.fromJson(personDTO, PersonDTO.class);
        Person deletedHobby = FACADE.deletePerson(hDTO);
        int deletedId = deletedHobby.getId();
        return "{\"msg\":\"Person with id " + deletedId + " has been deleted\"}";
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePersonById(@PathParam("id") int id) {
        FACADE.deletePersonById(id);
        return "{\"msg\":\"Person with id " + id + " has been deleted\"}";
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editPersonById(@PathParam("id") int id, String person) {
        Person p = GSON.fromJson(person, Person.class);
        Person editedPerson = FACADE.editPerson(id, p);
        return GSON.toJson(new PersonDTO(editedPerson));
    }

    @POST
    @Path("/populate/{numberOfEntries}")
    @Produces(MediaType.APPLICATION_JSON)
    public String populate(@PathParam("numberOfEntries") int numberOfEntries) {
        List<PersonDTO> pDTOList = new ArrayList();
        List<Person> pList = FACADE.populateDatabaseWithPersons(numberOfEntries);
        for (Person person : pList) {
            pDTOList.add(new PersonDTO(person));
        }

        return GSON.toJson(pDTOList);
//        return "{\"msg\":\"Database has been populated with " + numberOfEntries + " Persons!\"}";
    }

    @GET
    @Path("/PersonsWithHobby/{hId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String personsWithHobby(@PathParam("hId") int id)
    {
        List<PersonDTO> persons = FACADE.getAllPersonByHobby(id);
        if (persons != null)
        {
            return GSON.toJson(persons);
        }
        else
        {
            return "Operation personsWithHobby failed.";
        }
    }
//    @GET
//    @Path("/personswithhobby")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String personsWithHobbys(String hobbyDTO) {
//        HobbyDTO hDTO = GSON.fromJson(hobbyDTO, HobbyDTO.class);
//        Hobby h = new Hobby(hDTO.getName(), hDTO.getDescription());
//        List<PersonDTO> persons = FACADE.getAllPersonByHobby(h);
//        if (persons != null){
//        return GSON.toJson(persons);
//        } else {
//            return "Operation failed";
//        }
//    }

    @GET
    @Path("/countPersonsWithHobby/{hId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String countPersonsWithHobby(@PathParam("hId") int id)
    {
        long amount = FACADE.countPeopleWithHobby(id);
        if (String.valueOf(amount) != null)
        {
            return GSON.toJson(
//                    "The amount of people with the hobby matching id: " + id + " is equal to: " + 
                    String.valueOf(amount));
        }
        else
        {
            return "Operation countPersonsWithHobby failed.";
        }
    }

}
