package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class AddressFacade
{

    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private AddressFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static AddressFacade getAddressFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public long getAddressCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long addressCount = (long) em.createQuery("SELECT COUNT(a) FROM Address a").getSingleResult();
            return addressCount;
        }
        finally
        {
            em.close();
        }

    }
    // not sure about this one
//    public long getAddressCountByHobby(Hobby arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    /**
     * 
     * @return 
     */
    public List<AddressDTO> getAllAddress() {
                EntityManager em = getEntityManager();
        
        try {
            List<AddressDTO> returnList = new ArrayList();
            TypedQuery<Address> persons = em.createQuery("SELECT a FROM Address a", Address.class);
            List<Address> list = persons.getResultList();
            for (Address a : list) {
                returnList.add(new AddressDTO(a));
            }
            return returnList;

        } finally {
            em.close();
        }

    }
    /**
     * 
     * @return 
     */
    public List<AddressDTO> getAllAddressByPerson(Person arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * 
     * @return 
     */
    public List<AddressDTO> getAllAddressByCity(CityInfo arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @return 
     */
    public Address getAddressById(int arg0) {
        EntityManager em = emf.createEntityManager();

        try {
            Address adr = em.find(Address.class, arg0);
            return adr;
        } finally {
            em.close();
        }
    }

    /**
     * 
     * @return 
     */
    public AddressDTO getAddressDTOById(int arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return 
     */
    public AddressDTO getAddressDTOByPhone(Phone arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param arg0
     * @return 
     */
    public Address persistAddress(Address arg0) {
        {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(arg0);
            em.getTransaction().commit();
            return arg0;
        }
        catch (Exception ex)
        {
            System.out.println("Operation persistAdress failed.");
            return null;
        }
        finally
        {
            em.close();
        }
    }
    }
    /**
     * 
     * @return 
     */
    public Address deleteAddress(AddressDTO arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return 
     */
    public Address deleteAddressById(int arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return 
     */
    public Address editAddress(Address arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return 
     */
    public PersonDTO addAddressToPerson(Person arg0, Address arg1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
