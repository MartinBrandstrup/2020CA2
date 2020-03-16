package facades;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

}
