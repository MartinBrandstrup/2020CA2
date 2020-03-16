package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author Brandstrup
 */
@Entity
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
public class Phone implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private long number;
    private String description;

    @ManyToOne
    private Person person;

    @Override
    public int hashCode()
    {
        return Objects.hash(this.number);
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
        final Phone other = (Phone) obj;
        if (this.number != other.number)
        {
            return false;
        }
        return true;
    }

    public Phone()
    {
    }

    /**
     *
     * @param number The telephone number for this object. Is mapped as unique
     * in the database.
     * @param description An optional description.
     */
    public Phone(long number, String description)
    {
        this.number = number;
        this.description = description;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public long getNumber()
    {
        return number;
    }

    public void setNumber(long number)
    {
        this.number = number;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    @Override
    public String toString()
    {
        return "Phone{" + "number=" + number + ", description="
                + description + ", person=" + person + '}';
    }

}
