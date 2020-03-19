package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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

    @Column(nullable = false, unique = true)
    private String street, additionalInfo;

    @ManyToOne
    private CityInfo cityInfo;

    @OneToMany(mappedBy = "address", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Person> persons;

    @Override
    public int hashCode()
    {
        return Objects.hash(this.street);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.street, other.street))
        {
            return false;
        }
        return true;
    }

    public Address()
    {
    }

    /**
     * When initializing this object, remember to populate with Person Entities
     * according to the OneToMany relation (HashSet) using the addPerson method.
     *
     * @param street The street name for this address. Is mapped as unique in
     * the database.
     * @param additionalInfo Optional information about this address.
     */
    public Address(String street, String additionalInfo)
    {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.persons = new HashSet();
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
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getAdditionalInfo()
    {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo)
    {
        this.additionalInfo = additionalInfo;
    }

    public CityInfo getCityInfo()
    {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo)
    {
        this.cityInfo = cityInfo;
    }

    public Set<Person> getPersons()
    {
        return persons;
    }

    public void addPerson(Person person)
    {
        this.persons.add(person);
        person.setAddress(this);
    }

    public void removePerson(Person person)
    {
        this.persons.remove(person);
        person.setAddress(null);
    }

    @Override
    public String toString()
    {
        return "{ \"id\":\"" + id + "\", \"street\":\"" + street 
                + "\", \"additionalInfo\":\"" + additionalInfo 
                + "\", \"city\":\"" + cityInfo.getCity() + " }";
    }

}
