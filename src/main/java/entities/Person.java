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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Brandstrup
 */
@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName, lastName;

    @Column(nullable = false, unique = true)
    private String email;
    @ManyToOne
    private Address address;

    @OneToMany(mappedBy = "person", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Phone> phones = new HashSet();

    @ManyToMany
    @JoinTable(name = "PERSON_HOBBY")
    private Set<Hobby> hobbies = new HashSet();

    @Override
    public int hashCode()
    {
        return Objects.hash(this.email);
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.email, other.email))
        {
            return false;
        }
        return true;
    }

    public Person()
    {
    }

    /**
     * When initializing this object, remember to populate with Phone Entities 
     * and Hobby Entities according to the OneToMany (Phone) and ManyToMany 
     * (Hobby) relations (HashSets) using the addPhone and addHobby methods.
     * 
     * @param firstName The first name of this person.
     * @param lastName The last name of this person.
     * @param email The email of this person. Is mapped as unique in the 
     * database.
     * @param address The Address Entity Object this Person relates to 
     * (ManyToOne).
     */
    public Person(String firstName, String lastName, String email, Address address)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Set<Phone> getPhones()
    {
        return phones;
    }

    public void addPhone(Phone phone)
    {
        this.phones.add(phone);
        phone.setPerson(this);
    }

    public void removePhone(Phone phone)
    {
        this.phones.remove(phone);
        phone.setPerson(null);
    }

    public Set<Hobby> getHobbies()
    {
        return hobbies;
    }

    public void addHobby(Hobby hobby)
    {
        this.hobbies.add(hobby);
        hobby.getPersons().add(this);
    }

    public void removeHobby(Hobby hobby)
    {
        this.hobbies.remove(hobby);
        hobby.getPersons().remove(this);
    }

    @Override
    public String toString()
    {
        return "Person{" + "id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + ", address="
                + address + ", phones=" + phones + ", hobbies=" + hobbies + '}';
    }

}
