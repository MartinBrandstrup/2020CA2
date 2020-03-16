package facades;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CityInfoFacade
{

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityInfoFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public long getCityInfoCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long cityInfoCount = (long) em.createQuery("SELECT COUNT(c) FROM CityInfo c").getSingleResult();
            return cityInfoCount;
        }
        finally
        {
            em.close();
        }

    }

}
