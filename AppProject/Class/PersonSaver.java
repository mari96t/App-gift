package com.cloudstechnologies.android.giftapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;

public class PersonSaver
{
    private Context context;
    private String filename;
    
    public PersonSaver(Context context, String filename)
    {
        this.context = context;
        this.filename = filename;
    }
    
    public void save(ArrayList<Person> imageDatas)
            throws JSONException, IOException
    {
        JSONArray array = new JSONArray();
        for (Person person : imageDatas)
            array.put(person.toJSON());
        Writer writer = null;
        try
        {
            OutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }
        finally
        {
            if (writer != null) writer.close();
        }
    }
    
    public ArrayList<Person> load()
            throws JSONException, IOException
    {
        ArrayList<Person> persons = new ArrayList<Person>();
        BufferedReader reader = null;
        try
        {
            InputStream in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                jsonString.append(line);
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();
            for (int i = 0; i < array.length(); i++)
                persons.add(new Person(array.getJSONObject(i)));
        }
        catch (FileNotFoundException e)
        { // no data - init:
//            persons.add(new Person("Мама", new Date(), null, 3));
//            persons.add(new Person("Катя", new Date(), null, 3));
//            persons.add(new Person("Василий", new Date(), null, 3));
        }
        finally
        {
            if (reader != null) reader.close();
        }
        return persons;
    }
}
