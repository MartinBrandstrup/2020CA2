package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import entities.Phone;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class AddressFacadeTest {

    private static EntityManagerFactory emf;
    private static AddressFacade facade;

//    CityInfo city1 = new CityInfo(1111, "FirstCity");
//    CityInfo city2 = new CityInfo(1111, "ScondCity");
    private String street1 = "firstStreet";
    private String street2 = "secondStreet";
    private String street3 = "NewStreet";
    private String AdditionalInfo1 = "Its the first street";
    private String AdditionalInfo2 = "Its not the first street tho";
    private String AdditionalInfo3 = "It made it";
    Address adr1 = new Address(street1, AdditionalInfo1);
    Address adr2 = new Address(street2, AdditionalInfo2);
    Address adr3 = new Address(street3, AdditionalInfo3);
    AddressDTO adrDTO1 = new AddressDTO(adr1);
    AddressDTO adrDTO3 = new AddressDTO(adr3);

    public AddressFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/2020CA2_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = AddressFacade.getAddressFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = AddressFacade.getAddressFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.persist(adr1);
            em.persist(adr2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void addressCountTest() {
        assertEquals(2, facade.getAddressCount(), "Expects two rows in the database");
    }

    @Test
    public void getAllAddress() {
        System.out.println("getAllAddress");
        List<AddressDTO> adrDTO = facade.getAllAddress();
        System.out.println("Expects: 2 " + adrDTO.size());
        assertEquals(2, adrDTO.size(), "Expects two rows in the database");
    }
//
//    @Test
//    public void getAllAddressByPerson(Person arg0) {
//        assertEquals(2, facade.getAddressCount(), "Expects two rows in the database");
//    }
//
//    @Test
//    public void getAllAddressByCity(CityInfo arg0) {
//        assertEquals(2, facade.getAddressCount(), "Expects two rows in the database");
//    }
//
    @Test
    public void getAddressById() {
        System.out.println("GetAddressById");
        AddressFacade adrF = facade;
        int AdressID = adrF.persistAddress(adr3).getId();
        System.out.println(AdressID);
        Address res = facade.getAddressById(AdressID);
        assertEquals(adr3, res, "Expects adr3");

    }

    @Test
    public void getAddressDTOById() {
         System.out.println("GetAddressById");
        AddressFacade adrF = facade;
        int addressID = adrF.persistAddress(adr3).getId();
        System.out.println(addressID);
        adrDTO3.setId(addressID);
        AddressDTO res = facade.getAddressDTOById(addressID);
        assertEquals(adrDTO3, res, "Expects adr3");
        //sammenling id
    }

//    @Test
//    public void getAddressDTOByPhone(Phone arg0) {
//        assertEquals(2, facade.getAddressCount(), "Expects two rows in the database");
//    }
//
    @Test
    public void persistAddress() {
        System.out.println("Persist Adress");
        AddressFacade adrF = facade;
        int AdressID = adrF.persistAddress(adr3).getId();
        System.out.println(AdressID);
        assertNotNull(AdressID);
    }
//
    @Test
    public void deleteAddressById() {
        System.out.println("DeleteAddressById");
        facade.persistAddress(adr3);
        int newId = adr3.getId();
        System.out.println("new adr id: " + newId );
        facade.deleteAddressById(newId);
        assertEquals(null, facade.getAddressById(newId), "Expects two rows in the database");
    }
//
//    @Test
//    public void editAddress() {
//        System.out.println("editAddress");
//        facade.persistAddress(adr3);
//        facade.editAddress(adr3.getId(), adrDTO1);
//        AddressDTO expt = new AddressDTO(facade.getAddressById(adr3.getId())); 
//        assertEquals(adrDTO1, expt, "Expects two rows in the database");
//    }


    // needs PersistAdrss + getaddressbyID
//    @Test
//    public void addAddressToPerson() {
//        facade.persistAddress(adr3);
//       Address expt = new Address(street3, AdditionalInfo3);
//       Address res = facade.getAddressById(adr3.getId());
//        assertEquals(expt,res, "Looking for adr3");
//    }
}
