package com.cloudstechnologies.android.giftapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PersonGen
{
    private static final String FILENAME = "persons.database";
    public static PersonGen gen;
    private ArrayList<Person> persons;
    private Context contextApp;
    private PersonSaver s;
    
    private PersonGen(Context contextApp)
    {
        this.contextApp = contextApp;
        s = new PersonSaver(contextApp, FILENAME);
        try
        {
            persons = s.load();
        }
        catch (Exception e)
        {
            persons = new ArrayList<Person>();
        }
    }
    public static PersonGen get(Context c)
    {
        if (gen == null)
        {
            gen = new PersonGen(c.getApplicationContext());
        }
        return gen;
    }
    public ArrayList<Person> getPersons()
    {
        return persons;
    }
    public Person get(UUID id)
    {
        for (Person person : persons)
            if (person.getId().equals(id)) return person;
        return null;
    }
    public void add(Person person)
    {
        persons.add(person);
    }
    public void delete(Person person)
    {
        persons.remove(person);
    }
    public boolean save()
    {
        try
        {
            s.save(persons);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
