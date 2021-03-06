package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import entities.Hobby;
import exceptions.NoObjectException;
import utils.EMF_Creator;
import facades.HobbyFacade;
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
 * @author Brandstrup
 */
@Path("hobby")
public class HobbyResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020CA2_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final HobbyFacade FACADE = HobbyFacade.getHobbyFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String demo()
    {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("/count")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHobbyCount()
    {
        long count = FACADE.getHobbyCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllHobbies()
    {
        List<HobbyDTO> hDTOList = FACADE.getAllHobbies();
        if (hDTOList != null)
        {
            return GSON.toJson(hDTOList);
        }
        else
        {
            return "{\"msg\":\"Operation getAllHobbies failed\"}";
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHobbyByID(@PathParam("id") int id)
    {
        try
        {
            HobbyDTO hDTO = FACADE.getHobbyDTOById(id);
            return GSON.toJson(hDTO);
        }
        catch (NoObjectException ex)
        {
            return ex.getMessage();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String persistHobby(String hobbyDTO)
    {
        HobbyDTO hDTO = GSON.fromJson(hobbyDTO, HobbyDTO.class);     //Converts the request from a JSON string to a DTO
        Hobby hManaged = FACADE.persistHobby( //Persists the object to the database
                new Hobby(hDTO.getName(), hDTO.getDescription()));
        return GSON.toJson(new HobbyDTO(hManaged));                  //Returns the managed object as a DTO
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteHobby(String hobbyDTO)
    {
        HobbyDTO hDTO = GSON.fromJson(hobbyDTO, HobbyDTO.class);
        Hobby deletedHobby = FACADE.deleteHobby(hDTO);
        int deletedId = deletedHobby.getId();
        return "{\"msg\":\"Hobby with id " + deletedId + " has been deleted\"}";
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteHobbyById(@PathParam("id") int id)
    {
        FACADE.deleteHobbyById(id);
        return "{\"msg\":\"Hobby with id " + id + " has been deleted\"}";
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editHobbyById(@PathParam("id") int id, String hobby)
    {
        Hobby h = GSON.fromJson(hobby, Hobby.class);
        Hobby editedHobby = FACADE.editHobby(id, h);
        return GSON.toJson(new HobbyDTO(editedHobby));
    }

    @POST
    @Path("/populate/{numberOfEntries}")
    @Produces(MediaType.APPLICATION_JSON)
    public String populate(@PathParam("numberOfEntries") int numberOfEntries)
    {
        List<HobbyDTO> hDTOList = new ArrayList();
        List<Hobby> hList = FACADE.populateDatabaseWithHobbies(numberOfEntries);
        for (Hobby hobby : hList)
        {
            hDTOList.add(new HobbyDTO(hobby));
        }
        return GSON.toJson(hDTOList);
//        return "{\"msg\":\"Database has been populated with " + numberOfEntries + " Hobbies!\"}";
    }

}
