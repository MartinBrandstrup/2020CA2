/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import entities.Person;
import java.util.List;

/**
 *
 * @author Brandstrup
 */
public class HobbyDTO
{

    private int id;
    private String name, description;
    private List<Person> persons;
    //Kan denne være en liste? Skal det være en String istedet?

    public HobbyDTO(Hobby hobby)
    {
        this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
        for (Person person : hobby.getPersons())
        {
            this.persons.add(person);
        }
    }

    public int getId()
    {
        return id;
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

    public List<Person> getPersons()
    {
        return persons;
    }

    @Override
    public String toString()
    {
        return "HobbyDTO{" + "id=" + id + ", name=" + name + ", description=" 
                + description + ", persons=" + persons + '}';
    }
    
    

}
