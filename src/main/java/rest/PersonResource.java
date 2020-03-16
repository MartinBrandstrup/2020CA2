/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.RenameMe;
import utils.EMF_Creator;
import facades.FacadeExample;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author Christian
 */

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final FacadeExample FACADE =  FacadeExample.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
   
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPerson(@PathParam("id") int id){
    
    return GSON.toJson(new PersonDTO(1,"Bob", "Lyngbyvej 34", "Lyngby", "2800", "333,456", "Beer, Programning"));
    } 
 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPerson(String p){
        PersonDTO newPerson = GSON.fromJson(p, PersonDTO.class);
        if 
                (
                // in gruppe agree on requried 
                newPerson.getName() == null || 
                newPerson.getStreet() == null ||
                newPerson.getCity() == null ||
                newPerson.getZip() == null ||
                newPerson.getPhones() == null ||
                newPerson.getHobbies() == null)
                {
                    //throw lackOfDataException // 
                } else{
        //next line just for first scrum meeting => no dataBase data og facade
        newPerson.setId(123);
        return GSON.toJson(newPerson);    
        }
        // removed next two lines when exceptions hads been initiated
           newPerson.setId(123);
        return GSON.toJson(newPerson);    
    }
}
    
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

