/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import utils.EMF_Creator;
import facades.FacadeExample;
import facades.PersonFacade;
import java.util.List;
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
public class PersonResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020CA2_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(
    {
        MediaType.APPLICATION_JSON
    })
    public String demo()
    {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonCount()
    {
        long count = FACADE.getPersonCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersons()
    {
        List<PersonDTO> pDTOList = FACADE.getAllPersons();
        if (pDTOList != null)
        {
            return GSON.toJson(pDTOList);
        }
        else
        {
            return "{\"msg\":\"Operation getAllPersons failed\"}";
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonById(@PathParam("id") int id)
    {
        PersonDTO pDTO = FACADE.getPersonDTOById(id);
        if (pDTO != null)
        {
            return GSON.toJson(pDTO);
        }
        else
        {
            return "{\"msg\":\"Operation getPersonById " + id + " failed\"}";
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String persistPerson(String person)
    {
        Person p = GSON.fromJson(person, Person.class); //Converts the request from a JSON string to a Java Entity
        Person pManaged = FACADE.persistPerson(p); //Persists the object to the database
        return GSON.toJson(new PersonDTO(pManaged)); //Returns the managed object as a DTO
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePerson(String personDTO)
    {
        PersonDTO hDTO = GSON.fromJson(personDTO, PersonDTO.class);
        Person deletedHobby = FACADE.deletePerson(hDTO);
        int deletedId = deletedHobby.getId();
        return "{\"msg\":\"Person with id " + deletedId + " has been deleted\"}";
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePersonById(@PathParam("id") int id)
    {
        FACADE.deletePersonById(id);
        return "{\"msg\":\"Person with id " + id + " has been deleted\"}";
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editPersonById(@PathParam("id") int id, String hobby)
    {
        Person p = GSON.fromJson(hobby, Person.class);
        Person editedPerson = FACADE.editPerson(id, p);
        return GSON.toJson(new PersonDTO(editedPerson));
    }
    
    @POST
    @Path("/populate/{numberOfEntries}")
    @Produces(MediaType.APPLICATION_JSON)
    public String populate(@PathParam("numberOfEntries") int numberOfEntries)
    {
        FACADE.populateDatabaseWithPersons(numberOfEntries);
        return "{\"msg\":\"Database has been populated with " + numberOfEntries + " Persons!\"}";
    }
}

//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getPerson(@PathParam("id") int id){
//    
//    return GSON.toJson(new PersonDTO(1,"Bob", "Lyngbyvej 34", "Lyngby", "2800", "333,456", "Beer, Programning"));
//    }
// 
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String addPerson(String p){
//        PersonDTO newPerson = GSON.fromJson(p, PersonDTO.class);
//        if 
//                (
//                // in gruppe agree on requried 
//                newPerson.getFirstName() == null || 
//                newPerson.getStreet() == null ||
//                newPerson.getCity() == null ||
//                newPerson.getZip() == null ||
//                newPerson.getPhones() == null ||
//                newPerson.getHobbies() == null)
//                {
//                    //throw lackOfDataException // 
//                } else{
//        //next line just for first scrum meeting => no dataBase data og facade
//        newPerson.setId(123);
//        return GSON.toJson(newPerson);    
//        }
//        // removed next two lines when exceptions hads been initiated
//           newPerson.setId(123);
//        return GSON.toJson(newPerson);    
//    }
//    @PUT
//    @Path("delete/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String deletePerson(PathParam("id") int i){
//    PersonDTO toDelete =  FACADE.getPerson(i);
//    FACADE.deltePerson(toDelete);
//    return GSON.toJson("The following user has been deleted: " + toDelete);
//}
//   cheklist:
//           - delete person
//           - Add tests
//           - add more API
//           - Change addPerson and getPersonById from scrum one stat
/**
 *
 * @author Christian
 */
