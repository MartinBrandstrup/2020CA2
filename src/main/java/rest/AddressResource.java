package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import entities.Address;
import utils.EMF_Creator;
import facades.AddressFacade;
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
 * @author Christian
 */
@Path("address")
public class AddressResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020CA2_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final AddressFacade FACADE = AddressFacade.getAddressFacade(EMF);
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

    @Path("count")
    @GET
    @Produces(
    {
        MediaType.APPLICATION_JSON
    })
    public String getAddressCount()
    {
        long count = FACADE.getAddressCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

      @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllAddress()
    {
        List<AddressDTO> allAdr = FACADE.getAllAddress();
        if (allAdr != null)
        {
            return GSON.toJson(allAdr);
        }
        else
        {
            return "{\"msg\":\"Operation getAllAddress failed\"}";
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAddressByID(@PathParam("id") int id)
    {
        AddressDTO DTO = FACADE.getAddressDTOById(id);
        if (DTO != null)
        {
            return GSON.toJson(DTO);
        }
        else
        {
            return "{\"msg\":\"Operation getAddressByID " + id + " failed\"}";
        }
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String persistAddress(String address)
    {
        Address adr = GSON.fromJson(address, Address.class); //Converts the request from a JSON string to a Java Entity
        Address adrPersist = FACADE.persistAddress(adr); //Persists the object to the database
        return GSON.toJson(new AddressDTO(adrPersist)); //Returns the managed object as a DTO
    }
//
//    @DELETE
//    @Path("/delete")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String deleteHobby(String hobbyDTO)
//    {
//        HobbyDTO hDTO = GSON.fromJson(hobbyDTO, HobbyDTO.class);
//        Hobby deletedHobby = FACADE.deleteHobby(hDTO);
//        int deletedId = deletedHobby.getId();
//        return "{\"msg\":\"Hobby with id " + deletedId + " has been deleted\"}";
//    }
//
//    @DELETE
//    @Path("/delete/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String deleteAddressById(@PathParam("id") int id)
//    {
//        FACADE.deleteAddressById(id);
//        return "{\"msg\":\"Address with id " + id + " has been deleted\"}";
//    }
//
//    @PUT
//    @Path("/edit/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String editHobby(@PathParam("id") int id, String address)
//    {
//        Address adr = GSON.fromJson(address, Address.class);
//        AddressDTO adrDTO = new AddressDTO(adr);
//        AddressDTO editedAdrDto = FACADE.editAddress(int id, adrDTO);
//        return GSON.toJson(editedAdrDto);
//    }
////
//    @POST
//    @Path("/populate/{numberOfEntries}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String populate(@PathParam("numberOfEntries") int numberOfEntries)
//    {
//        FACADE.populateDatabaseWithHobbies(numberOfEntries);
//        return "{\"msg\":\"Database has been populated with " + numberOfEntries + " Hobbies!\"}";
//    }

}
