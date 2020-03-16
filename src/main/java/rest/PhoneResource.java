package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.PhoneFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("phone")
public class PhoneResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020CA2_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PhoneFacade FACADE = PhoneFacade.getPhoneFacade(EMF);
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
    public String getPhoneCount()
    {
        long count = FACADE.getPhoneCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

}
