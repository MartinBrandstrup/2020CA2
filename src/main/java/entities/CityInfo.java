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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Brandstrup
 */
@Entity
@NamedQuery(name = "CityInfo.deleteAllRows", query = "DELETE from CityInfo")
public class CityInfo implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private int zipCode;
    private String city;

    @OneToMany(mappedBy = "cityInfo", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Address> addresses = new HashSet();

    @Override
    public int hashCode()
    {
        return Objects.hash(this.zipCode);
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
        final CityInfo other = (CityInfo) obj;
        if (this.zipCode != other.zipCode)
        {
            return false;
        }
        return true;
    }

    public CityInfo()
    {
    }

    /**
     * When initializing this object, remember to populate with Address Entities
     * according to the OneToMany relation (HashSet) using the addAddress method.
     * 
     * @param zipCode The zip code for this city. Is mapped a unique in the 
     * database.
     * @param city The name of the city for this object.
     */
    public CityInfo(int zipCode, String city)
    {
        this.zipCode = zipCode;
        this.city = city;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(int zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Set<Address> getAddresses()
    {
        return addresses;
    }

    public void addAddress(Address address)
    {
        this.addresses.add(address);
        address.setCityInfo(this);
    }

    public void removeAddress(Address address)
    {
        this.addresses.remove(address);
        address.setCityInfo(null);
    }

    @Override
    public String toString()
    {
        return "CityInfo{" + "id=" + id + ", zipCode=" + zipCode
                + ", city=" + city + ", addresses=" + addresses + '}';
    }

}
