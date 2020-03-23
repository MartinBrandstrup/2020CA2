/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brandstrup
 */
public class HobbyNoPersonRelationsDTO
{

    private int id;
    private String name, description;

    public HobbyNoPersonRelationsDTO(Hobby hobby)
    {
        this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
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

    @Override
    public String toString()
    {
        return "HobbyNoRelationsDTO{" + "id=" + id + ", name=" + name
                + ", description=" + description + '}';
    }

}
