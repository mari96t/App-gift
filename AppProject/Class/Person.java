package com.cloudstechnologies.android.giftapp;

import android.app.NotificationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Person
{
    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_COUNT = "count";
    private static final String JSON_READYGIFTS = "readygifts";
    private static final String JSON_AL = "giftArrayList";
    
    private UUID id;
    private String name;
    private Date date;
    private Photo photo;
    private int count;
    private int readygifts;
    
    private ArrayList<Gift> giftArrayList;
    
    public Person()
    {
        id = UUID.randomUUID();
        count = 0;
        readygifts = 0;
        giftArrayList = new ArrayList<>();
    }
    
    public Person(String name, Date date, Photo photo, int count)
    {
        id = UUID.randomUUID();
        this.name = name;
        this.date = date;
        this.photo = photo;
        this.count = count;
        giftArrayList = new ArrayList<>();
        readygifts = 0;
    }
    
    // download
    public Person(JSONObject json)
            throws JSONException
    {
        id = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_NAME)) name = json.getString(JSON_NAME);
        if (json.has(JSON_DATE)) date = new Date(json.getLong(JSON_DATE));
        if (json.has(JSON_PHOTO)) photo = new Photo(json.getJSONObject(JSON_PHOTO));
        count = json.getInt(JSON_COUNT);
        readygifts = json.getInt(JSON_READYGIFTS);
        giftArrayList = new ArrayList<>();
        if (json.has(JSON_AL))
        {
            JSONArray array = json.getJSONArray(JSON_AL);
    
            for (int i = 0; i < array.length(); i++)
                giftArrayList.add(new Gift(array.getJSONObject(i)));
        }
    }
    
    // upload
    public JSONObject toJSON()
            throws JSONException
    {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        if (name != null) json.put(JSON_NAME, name);
        if (date != null) json.put(JSON_DATE, date.getTime());
        if (photo != null) json.put(JSON_PHOTO, photo.toJSON());
        json.put(JSON_COUNT, count);
        json.put(JSON_READYGIFTS, readygifts);
        if (giftArrayList != null)
        {
            JSONArray array = new JSONArray();
    
            for (Gift gift : giftArrayList)
                array.put(gift.toJSON());
            
            json.put(JSON_AL, array);
        }
        return json;
    }
    
    void add_gift(Gift gift)
    {
        giftArrayList.add(gift);
        update_count();
    }
    void delete_gift(Gift gift)
    {
        giftArrayList.remove(gift);
        update_readygifts();
        update_count();
    }
    void update_count()
    {
        count = giftArrayList.size();
    }
    void update_readygifts()
    {
        readygifts = 0;
        for (Gift gift : giftArrayList)
            if (gift.isReady()) readygifts++;
    }
    
    public UUID getId()
    {
        return id;
    }
    public void setId(UUID id)
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
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }
    public Photo getPhoto()
    {
        return photo;
    }
    public void setPhoto(Photo photo)
    {
        this.photo = photo;
    }
    public int getCount()
    {
        return count;
    }
    public void setCount(int count)
    {
        this.count = count;
    }
    public int getReadygifts()
    {
        return readygifts;
    }
    public void setReadygifts(int readygifts)
    {
        this.readygifts = readygifts;
    }
    public ArrayList<Gift> getGiftArrayList()
    {
        return giftArrayList;
    }
    public void setGiftArrayList(
            ArrayList<Gift> giftArrayList)
    {
        this.giftArrayList = giftArrayList;
    }
}
