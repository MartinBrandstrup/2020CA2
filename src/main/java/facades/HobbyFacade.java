package facades;

import entities.Hobby;
import dtos.HobbyDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class HobbyFacade
{

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    /*
    This facade contains the following methods in order:
    getHobbyCount()
    getAllHobbies()
    getHobbyById(int id)
    getHobbyDTOById(int id)
    persistHobby(Hobby hobby)
    deleteHobby(Hobby hobby)
    deleteHobbyById(int id)
    populateDatabaseWithHobbies(int numberOfEntries)
     */
    /**
     * Counts the amount of Hobby entries existing in the database.
     *
     * @return The amount of existing entries in the database.
     */
    public long getHobbyCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long hobbyCount = (long) em.createQuery("SELECT COUNT(h) FROM Hobby h").getSingleResult();
            return hobbyCount;
        }
        finally
        {
            em.close();
        }

    }

    /**
     * Retrieves all Hobbies from the database as HobbyDTOs objects. Returns
     * null if the operation fails.
     *
     * @return a List<HobbyDTO>.
     */
    public List<HobbyDTO> getAllHobbies()
    {
        EntityManager em = getEntityManager();
        try
        {
            List<HobbyDTO> hobbyDTOList = new ArrayList<>();
            TypedQuery<Hobby> query
                    = em.createQuery("SELECT h FROM Hobby h", Hobby.class);

            for (Hobby h : query.getResultList())
            {
                hobbyDTOList.add(new HobbyDTO(h));
            }

            return hobbyDTOList;
        }
        catch (Exception ex)
        {
            System.out.println("Operation getAllHobbies failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to retrieve a Hobby object from the database corresponding to
     * the provided ID. Used mainly for back-end work, since not all information
     * of the Car object should be displayed on the front-end. Returns null if
     * the operation fails
     *
     * @param id The provided ID to search the database for.
     * @return a Hobby object containing all information.
     */
    public Hobby getHobbyById(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            Hobby h = em.find(Hobby.class, id);
            return h;
        }
        catch (Exception ex)
        {
            System.out.println("Operation getHobbyById failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to retrieve a HobbyDTO object from the database corresponding to
     * the provided ID. Used mainly for front-end since not all necessary info
     * is provided with a DTO object. Returns null if the operation fails.
     *
     * @param id The provided ID to search the database for.
     * @return a HobbyDTO object containing the necessary information to be
     * displayed on the front-end.
     */
    public HobbyDTO getHobbyDTOById(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            Hobby h = em.find(Hobby.class, id);
            HobbyDTO hDTO = new HobbyDTO(h);
            return hDTO;
        }
        catch (Exception ex)
        {
            System.out.println("Operation getHobbyDTOById failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to persist a Hobby object to the database. Returns the persisted
     * object if successful; null if the operation fails.
     *
     * @param hobby The Hobby object to persist.
     * @return the Hobby object after it has been managed by the Entity Manager.
     */
    public Hobby persistHobby(Hobby hobby)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
            return hobby;
        }
        catch (Exception ex)
        {
            System.out.println("Operation persistHobby failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to delete the provided Hobby object's entry from the database.
     * Returns the deleted Hobby object; null if the operation fails.
     *
     * @param hobby The Hobby object to delete.
     * @return the deleted Hobby object.
     */
    public Hobby deleteHobby(Hobby hobby)
    {
        EntityManager em = getEntityManager();
        try
        {
            Hobby h = em.find(Hobby.class, hobby.getId());

            em.getTransaction().begin();
            em.remove(h);
            em.getTransaction().commit();
            return h;
        }
        catch (Exception ex)
        {
            System.out.println("Operation deleteHobby failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Attempts to delete the Hobby of the provided id object's entry from the
     * database. Returns the deleted Hobby object; null if the operation fails.
     *
     * @param id The id of the Hobby object to delete.
     * @return the deleted Hobby object.
     */
    public Hobby deleteHobbyById(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            Hobby h = em.find(Hobby.class, id);

            em.getTransaction().begin();
            em.remove(h);
            em.getTransaction().commit();
            return h;
        }
        catch (Exception ex)
        {
            System.out.println("Operation deleteHobbyById failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Populates the database with a set of dummy entries for testing. Returns a
     * list of these entries as Java objects after they have been managed; null
     * if the operation fails. WARNING: wipes the database of existing entries!
     * Part of this method is synchronized to avoid race conditions and thus
     * match the object ids with their position in the returned List.
     *
     * @param numberOfEntries The number of entries to populate database with.
     * @return a List<Hobby> containing the created objects after they have been
     * managed by the Entity Manager.
     */
    public List<Hobby> populateDatabaseWithHobbies(int numberOfEntries)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            List<Hobby> hobbyList = new ArrayList<>();
            synchronized (this)
            {
                for (int i = 0; i < numberOfEntries; i++)
                {
                    String name = "name" + i;
                    String description = "description" + i;
                    Hobby h = new Hobby(name, description);
                    hobbyList.add(h);
                    em.persist(h);
                }
            }
            em.getTransaction().commit();
            return hobbyList;
        }
        catch (Exception ex)
        {
            System.out.println("Operation populateDatabaseWithHobbies failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }
}
