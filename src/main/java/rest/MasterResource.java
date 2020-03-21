package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dtos.AddressDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.Hobby;
import exceptions.CouplingException;
import exceptions.DatabaseException;
import exceptions.NoObjectException;
import exceptions.ORMException;
import utils.EMF_Creator;
import facades.HobbyFacade;
import facades.MasterFacade;
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

@Path("master")
public class MasterResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020CA2_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final MasterFacade FACADE = MasterFacade.getMasterFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String demo()
    {
        return "{\"msg\":\"Hello World\"}";
    }

    @PUT
    @Path("/personToAddress/{pId}/{aId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String couplePersonToAddress(@PathParam("pId") int personId, 
            @PathParam("aId") int addressId)
    {
        try
        {
            AddressDTO aDTO = FACADE.couplePersonToAddress(personId, addressId);
            return GSON.toJson(aDTO);
        }
        catch (ORMException | CouplingException ex)
        {
            return ex.getMessage();
        }
        catch (NoObjectException ex)
        {
            return ex.getMessage() + " Wrong input.";
        }
    }

    @PUT
    @Path("/personsToAddress/{aId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String couplePersonsToAddress(@PathParam("aId") int addressId, String personIds)
    {
        int[] idList = GSON.fromJson(personIds, int[].class);
        try
        {
            AddressDTO aDTO = FACADE.couplePersonsToAddress(idList, addressId);
            return GSON.toJson(aDTO);
        }
        catch (ORMException | CouplingException ex)
        {
            return ex.getMessage();
        }
    }
    
    @PUT
    @Path("/hobbiesToPerson/{pId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String coupleHobbiesToPerson(@PathParam("pId") int personId, String hobbyIds)
    {
        int[] idList = GSON.fromJson(hobbyIds, int[].class);
        try
        {
            PersonDTO pDTO = FACADE.coupleHobbiesToPerson(idList, personId);
            return GSON.toJson(pDTO);
        }
        catch (ORMException | CouplingException ex)
        {
            return ex.getMessage();
        }
    }
    
    @PUT
    @Path("/hobbyFromPerson/{hId}/{pId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeHobbyFromPerson(@PathParam("hId") int hobbyId, 
            @PathParam("pId") int personId)
    {
        try
        {
            PersonDTO pDTO = FACADE.removeHobbyFromPerson(hobbyId, personId);
            return GSON.toJson(pDTO);
        }
        catch (ORMException | CouplingException ex)
        {
            return ex.getMessage();
        }
        catch (NoObjectException ex)
        {
            return ex.getMessage() + " Wrong input.";
        }
    }

    @POST
    @Path("/populate")
    @Produces(MediaType.APPLICATION_JSON)
    public String populate()
    {
        String res = FACADE.populateDatabaseWithTestData();
        return GSON.toJson(res);
    }

}
