package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

/**
 *
 * @author Brandstrup
 */
@Entity
@NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE from Hobby")
public class Hobby implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String name, description;

    @ManyToMany(mappedBy = "hobbies")
    private Set<Person> persons;

    @Override
    public int hashCode()
    {
        return Objects.hash(this.name);
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
        final Hobby other = (Hobby) obj;
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        return true;
    }

    public Hobby()
    {
    }

    /**
     * Due to the ManyToMany relation between the Hobby and Person Entities,
     * Hobby is unable to populate its own persons field; this must be done by
     * the related Person Entity Object.
     *
     * @param name The name of this hobby. Is mapped as unique in the database.
     * @param description An optional description about this hobby.
     */
    public Hobby(String name, String description)
    {
        this.name = name;
        this.description = description;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Set<Person> getPersons()
    {
        return persons;
    }

    @Override
    public String toString()
    {
        return "{ \"id\":\"" + id + "\", \"name\":\"" + name 
                + "\", \"description\":\"" + description + " }";
    }

}
