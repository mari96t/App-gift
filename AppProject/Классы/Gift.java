package com.cloudstechnologies.android.giftapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Gift
{
    private static final String JSON_ID = "id";
    private static final String JSON_GIFT = "gift";
    private static final String JSON_PRICE = "price";
    private static final String JSON_DATE_TIME_GIFTING = "date_time_gifting";
    private static final String JSON_DATE_TIME_BUYING = "date_time_buying";
    private static final String JSON_NOTIFY_GIFTING = "notify_gifting";
    private static final String JSON_NOTIFY_BUYING = "notify_buying";
    private static final String JSON_LINK = "link";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_READY = "ready";
    
    private UUID id;
    private String gift;
    private String price;
    private Date date_time_gifting = null;
    private Date date_time_buying = null;
    private boolean notify_gifting;
    private boolean notify_buying;
    private String link;
    private Photo photo;
    private boolean ready;
    
    public Gift()
    {
        id = UUID.randomUUID();
    }
    
    public Gift(
            String gift,
            String price,
            Date date_time_gifting,
            Date date_time_buying,
            boolean notify_gifting,
            boolean notify_buying,
            String link,
            Photo photo)
    {
        id = UUID.randomUUID();
        this.gift = gift;
        this.price = price;
        this.date_time_gifting = date_time_gifting;
        this.date_time_buying = date_time_buying;
        this.notify_gifting = notify_gifting;
        this.notify_buying = notify_buying;
        this.link = link;
        this.photo = photo;
        ready = false;
    }
    
    // download
    public Gift(JSONObject json)
            throws JSONException
    {
        id = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_GIFT)) gift = json.getString(JSON_GIFT);
        if (json.has(JSON_PRICE)) price = json.getString(JSON_PRICE);
        if (json.has(JSON_DATE_TIME_GIFTING))
            date_time_gifting = new Date(json.getLong(JSON_DATE_TIME_GIFTING));
        if (json.has(JSON_DATE_TIME_BUYING))
            date_time_buying = new Date(json.getLong(JSON_DATE_TIME_BUYING));
        if (json.has(JSON_NOTIFY_GIFTING))
            notify_gifting = json.getBoolean(JSON_NOTIFY_GIFTING);
        if (json.has(JSON_NOTIFY_BUYING))
            notify_buying = json.getBoolean(JSON_NOTIFY_BUYING);
        if (json.has(JSON_LINK)) link = json.getString(JSON_LINK);
        if (json.has(JSON_PHOTO))
            photo = new Photo(json.getJSONObject(JSON_PHOTO));
        ready = json.getBoolean(JSON_READY);
    }
    
    // upload
    public JSONObject toJSON()
            throws JSONException
    {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        if (gift != null) json.put(JSON_GIFT, gift);
        if (price != null) json.put(JSON_PRICE, price);
        if (date_time_gifting != null)
            json.put(JSON_DATE_TIME_GIFTING, date_time_gifting.getTime());
        if (date_time_buying != null)
            json.put(JSON_DATE_TIME_BUYING, date_time_buying.getTime());
        json.put(JSON_NOTIFY_GIFTING, notify_gifting);
        json.put(JSON_NOTIFY_BUYING, notify_buying);
        if (link != null) json.put(JSON_LINK, link);
        if (photo != null) json.put(JSON_PHOTO, photo.toJSON());
        json.put(JSON_READY, ready);
        return json;
    }
    
    public UUID getId()
    {
        return id;
    }
    public void setId(UUID id)
    {
        this.id = id;
    }
    public String getGift()
    {
        return gift;
    }
    public void setGift(String gift)
    {
        this.gift = gift;
    }
    public String getPrice()
    {
        return price;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }
    public Date getDate_time_gifting()
    {
        return date_time_gifting;
    }
    public void setDate_time_gifting(Date date_time_gifting)
    {
        this.date_time_gifting = date_time_gifting;
    }
    public Date getDate_time_buying()
    {
        return date_time_buying;
    }
    public void setDate_time_buying(Date date_time_buying)
    {
        this.date_time_buying = date_time_buying;
    }
    public boolean isNotify_gifting()
    {
        return notify_gifting;
    }
    public void setNotify_gifting(boolean notify_gifting)
    {
        this.notify_gifting = notify_gifting;
    }
    public boolean isNotify_buying()
    {
        return notify_buying;
    }
    public void setNotify_buying(boolean notify_buying)
    {
        this.notify_buying = notify_buying;
    }
    public String getLink()
    {
        return link;
    }
    public void setLink(String link)
    {
        this.link = link;
    }
    public Photo getPhoto()
    {
        return photo;
    }
    public void setPhoto(Photo photo)
    {
        this.photo = photo;
    }
    public boolean isReady()
    {
        return ready;
    }
    public void setReady(boolean ready)
    {
        this.ready = ready;
    }
}
