/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import exceptions.CouplingException;
import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import exceptions.DatabaseException;
import exceptions.NoObjectException;
import exceptions.ORMException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 *
 * @author Brandstrup
 */
public class MasterFacade
{

    Person martin = new Person("Martin", "Brandstrup", "martin.l.brandstrup@gmail.com");
    Person flemming = new Person("Flemming", "Hansen", "flemming.hansen@gmail.com");
    Hobby java = new Hobby("Java", "Software development 101");
    Hobby js = new Hobby("JavaScript", "Software development 102");
    Address vang21 = new Address("Vangegade 21", "Den eksisterer kun i min fantasi");
    Address vang25 = new Address("Vangegade 25", "Den eksisterer kun i min fantasi");

    private static EntityManagerFactory emf;

    HobbyFacade hobbyFacade = HobbyFacade.getHobbyFacade(emf);
    PersonFacade personFacade = PersonFacade.getPersonFacade(emf);
    AddressFacade addressFacade = AddressFacade.getAddressFacade(emf);

    private static MasterFacade instance;

    //Private Constructor to ensure Singleton
    private MasterFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MasterFacade getMasterFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new MasterFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public String populateDatabaseWithTestData()
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
//            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Person.deleteAllRows").executeUpdate();

            personFacade.persistPerson(martin);
            personFacade.persistPerson(flemming);
            hobbyFacade.persistHobby(java);
            hobbyFacade.persistHobby(js);
            addressFacade.persistAddress(vang21);
            addressFacade.persistAddress(vang25);

            em.getTransaction().commit();
            return "Operation populateDatabaseWithTestData successful.";
        }
        catch (IllegalStateException ex)
        {
            System.out.println("Operation populateDatabaseWithTestData "
                    + "encountered an error with the EntityManager");
            ex.printStackTrace();
            return null;
        }
        catch (Exception ex)
        {
            System.out.println("Operation populateDatabaseWithTestData failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }
    
    private static boolean contains(final int[] arr, final int key)
    {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    public AddressDTO couplePersonToAddress(int personId, int addressId) throws ORMException, CouplingException, NoObjectException
    {
        Person p = personFacade.getPersonById(personId);
        AddressDTO aDTO;

        if (p.getAddress() != null)
        {
            addressFacade.removePersonFromAddress(addressId, p);
        }

        aDTO = addressFacade.addPersonToAddress(addressId, p);

        if (aDTO == null)
        {
            throw new CouplingException("Return value of couplePersonToAddress is null.");
        }
        return aDTO;
    }

    public AddressDTO couplePersonsToAddress(int[] personIds, int addressId) throws ORMException, CouplingException
    {
        EntityManager em = getEntityManager();
        AddressDTO aDTO = null;
        List<Person> personList = new ArrayList<>();

        TypedQuery<Person> query
                = em.createQuery("SELECT p FROM Person p", Person.class);
        for (Person p : query.getResultList())
        {
            if (contains(personIds, p.getId()))
            {
                personList.add(p);
            }
        }
//        TypedQuery<Person> query
//                = em.createQuery("SELECT p FROM Person p WHERE p.id IN (:ids)",
//                        Person.class).setParameter("ids", Arrays.asList(personIds));
//        for (Person p : query.getResultList())
//        {
//            personList.add(p);
//        }
        for (Person p : personList)
        {
            if (p.getAddress() != null)
            {
                addressFacade.removePersonFromAddress(addressId, p);
            }

            aDTO = addressFacade.addPersonToAddress(addressId, p);
        }

        if (aDTO == null)
        {
            throw new CouplingException("Return value of couplePersonsToAddress is null.");
        }

        return aDTO;
    }

    public PersonDTO coupleHobbiesToPerson(int[] hobbyIds, int personId) throws ORMException, CouplingException
    {
        EntityManager em = getEntityManager();
        PersonDTO pDTO = null;
        List<Hobby> hobbyList = new ArrayList<>();
        Person p = em.find(Person.class, personId);

        TypedQuery<Hobby> query
                = em.createQuery("SELECT h FROM Hobby h", Hobby.class);
        for (Hobby h : query.getResultList())
        {
            if (contains(hobbyIds, h.getId()))
            {
                hobbyList.add(h);
            }
        }
        
        for (Hobby h : hobbyList)
        {
            if (!(h.getPersons().contains(p) && p.getHobbies().contains(h)))
            {
                pDTO = personFacade.addHobbyToPerson(personId, h);
            }
        }

        if (pDTO == null)
        {
            throw new CouplingException("Return value of coupleHobbiesToPerson is null.");
        }

        return pDTO;
    }
    
    public PersonDTO removeHobbyFromPerson(int hobbyId, int personId) throws ORMException, CouplingException, NoObjectException
    {
        PersonDTO pDTO = null;
        Hobby h = hobbyFacade.getHobbyById(hobbyId);
        
        if(h.getPersons() == null || h.getPersons().isEmpty())
        {
            throw new ORMException("The provided Hobby does not contain any Person relations.");
        }
        
        pDTO = personFacade.removeHobbyFromPerson(personId, h);
        
        if (pDTO == null)
        {
            throw new CouplingException("Return value of coupleHobbiesToPerson is null.");
        }
        
        return pDTO;
    }
}
