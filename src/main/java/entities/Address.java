package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * 
 * @author Brandstrup
 */
@Entity
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
public class Address implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Street, AdditionalInfo;

    @OneToMany(mappedBy = "address", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Person> persons = new HashSet();

    @ManyToOne
    private CityInfo cityInfo;

    public Address()
    {
    }

    public Address(int id, String Street, String AdditionalInfo, CityInfo cityInfo)
    {
        this.id = id;
        this.Street = Street;
        this.AdditionalInfo = AdditionalInfo;
        this.cityInfo = cityInfo;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getStreet()
    {
        return Street;
    }

    public void setStreet(String Street)
    {
        this.Street = Street;
    }

    public String getAdditionalInfo()
    {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String AdditionalInfo)
    {
        this.AdditionalInfo = AdditionalInfo;
    }

    public Set<Person> getPersons()
    {
        return persons;
    }

    public void setPersons(Set<Person> persons)
    {
        this.persons = persons;
    }

    public CityInfo getCityInfo()
    {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo)
    {
        this.cityInfo = cityInfo;
    }

}
