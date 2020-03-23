package facades;

import exceptions.ORMException;
import exceptions.DatabaseException;
import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
/**
 *
 * @author Christian
 */
public class AddressFacade {

    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private AddressFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static AddressFacade getAddressFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getAddressCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long addressCount = (long) em.createQuery("SELECT COUNT(a) FROM Address a").getSingleResult();
            return addressCount;
        } finally {
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
        }  catch (Exception ex){
            System.out.println("Operation getAddressById failed.");
            ex.printStackTrace();
            return null;
        }finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public AddressDTO getAddressDTOById(int arg0) {
         EntityManager em = emf.createEntityManager();

        try {
            Address adr = em.find(Address.class, arg0);
            AddressDTO adrDTO = new AddressDTO(adr);
            return adrDTO;
        }
         catch (Exception ex){
            System.out.println("Operation getAddressDTOById failed.");
            ex.printStackTrace();
            return null;
        }finally {
            em.close();
        }
    }
    
    /**
     * Attempts to retrieve a Address object from the database corresponding to
     * the provided street name. Used mainly for back-end work, since not all information
     * of the Address object should be displayed on the front-end.
     *
     * @param name The provided first name to search the database for.
     * @return a Address object containing all information.
     * @author Brandstrup
     */
    public Address getAddressByName(String name)
    {
        EntityManager em = getEntityManager();
        try
        {
            Address a;

            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a "
                    + "WHERE a.street = :addressStreet", Address.class)
                    .setParameter("addressStreet", name);
            a = query.getSingleResult();
            
            return a;
        }
        finally
        {
            em.close();
        }
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
            try {
                em.getTransaction().begin();
                em.persist(arg0);
                em.getTransaction().commit();
                return arg0;
            } catch (Exception ex) {
                System.out.println("Operation persistAdress failed.");
                return null;
            } finally {
                em.close();
            }
        }
    }

    /**
     *
     * @return
     */
    public Address deleteAddressById(int id) {
        EntityManager em = getEntityManager();
        try {
            Address adr = em.find(Address.class, id);

            em.getTransaction().begin();
            em.remove(adr);
            em.getTransaction().commit();
            return adr;
        } catch (Exception ex) {
            System.out.println("Operation deleteAddress failed.");
            ex.printStackTrace();
            return null;
        } finally {
            em.close();
        }

    }

//    /**
//     *
//     * @param id = Used to find the Address user wish to edit
//     * @param addressDTRO = the new values to be inserted in to taget Address
//     * @return
//     */
//    public AddressDTO editAddress(int id, AddressDTO addressDTRO) {
//        EntityManager em = getEntityManager();
//        try {
//            Address adr = em.find(Address.class, id);
//
//            em.getTransaction().begin();
//            adr.setStreet(addressDTRO.getStreet());
//            adr.setAdditionalInfo(addressDTRO.getAdditionalInfo());
//            // adr.setCityInfo();
//            //em.merge(adr); => should not need this: https://www.objectdb.com/java/jpa/persistence/update
//            em.getTransaction().commit();
//            AddressDTO edited = new AddressDTO(adr);
//            return edited;
//        } catch (Exception ex) {
//            System.out.println("Operation edit failed.");
//            ex.printStackTrace();
//            return null;
//        } finally {
//            em.close();
//        }
//
//    }


    /**
     * 
     * @param addressId
     * @param person
     * @return
     * @throws ORMException 
     * @author Brandstrup
     */
    public AddressDTO addPersonToAddress(int addressId, Person person) throws ORMException
    {
        EntityManager em = getEntityManager();
        try
        {
            Address a = em.find(Address.class, addressId);

            if(person.getAddress() != null)
            {
                throw new ORMException("The provided Person already has an address.");
            }
            a.addPerson(person);

            em.getTransaction().begin();
            em.merge(a);
            em.getTransaction().commit();
            return new AddressDTO(a);
        }
        catch (Exception ex)
        {
            System.out.println("Operation addPersonToAddress failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * 
     * @param addressId
     * @param person
     * @return 
     * @author Brandstrup
     */
    public AddressDTO removePersonFromAddress(int addressId, Person person)
    {
        EntityManager em = getEntityManager();
        try
        {
            Address a = em.find(Address.class, addressId);
            
            a.removePerson(person);
            
            em.getTransaction().begin();
            em.merge(a);
            em.getTransaction().commit();
            return new AddressDTO(a);
        }
        finally
        {
            em.close();
        }
    }
    
}
