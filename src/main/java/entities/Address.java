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
    private String Street;
    private String AdditionalInfo;

    @ManyToOne
    private CityInfo cityInfo;

    @OneToMany(mappedBy = "address", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Person> persons = new HashSet();

    @Override
    public int hashCode()
    {
        return Objects.hash(this.Street);
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
        if (!Objects.equals(this.Street, other.Street))
        {
            return false;
        }
        return true;
    }

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
        return "Address{" + "id=" + id + ", Street=" + Street
                + ", AdditionalInfo=" + AdditionalInfo + ", cityInfo="
                + cityInfo + ", persons=" + persons + '}';
    }

}
