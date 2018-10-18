package com.cloudstechnologies.android.giftapp;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ModifyGiftActivity extends AppCompatActivity
{
    Gift g;
    
    ImageView viewA;
    LinearLayout viewB;
    
    // gift
    ImageView iv_gift;
    LinearLayout ll_gift;
    TextView tv_gift;
    EditText et_gift;
    View v_gift;
    TextView tv_gift_obligatory;
    
    // price
    ImageView iv_price;
    LinearLayout ll_price;
    TextView tv_price;
    EditText et_price;
    View v_price;
    
    // date_gifting
    ImageView iv_date_gifting;
    LinearLayout ll_date_gifting;
    TextView tv_date_gifting;
    EditText et_date_gifting;
    View v_date_gifting;
    CheckBox cb_date_gifting_notify;
    LinearLayout ll_date_gifting_notify;
    TextView tv_date_gifting_notify;
    // time_gifting
    LinearLayout ll_time_gifting;
    TextView tv_time_gifting;
    EditText et_time_gifting;
    View v_time_gifting;
    
    // date_buying
    ImageView iv_date_buying;
    LinearLayout ll_date_buying;
    TextView tv_date_buying;
    EditText et_date_buying;
    View v_date_buying;
    CheckBox cb_date_buying_notify;
    LinearLayout ll_date_buying_notify;
    TextView tv_date_buying_notify;
    // time_buying
    LinearLayout ll_time_buying;
    TextView tv_time_buying;
    EditText et_time_buying;
    View v_time_buying;
    
    // link
    ImageView iv_link;
    LinearLayout ll_link;
    TextView tv_link;
    EditText et_link;
    View v_link;
    
    // aux
    int day_gifting;
    int month_gifting;
    int year_gifting;
    int h_gifting;
    int m_gifting;
    
    int day_buying;
    int month_buying;
    int year_buying;
    int h_buying;
    int m_buying;
    
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    
    private int choice = -1;
    private boolean added_photo = false;
    String stringUri = null;
    
    void init_views()
    {
        viewA = (ImageView) findViewById(R.id.viewA);
        viewB = (LinearLayout) findViewById(R.id.viewB);
        
        // gift
        iv_gift = (ImageView)findViewById(R.id.iv_gift);
        ll_gift = (LinearLayout)findViewById(R.id.ll_gift);
        tv_gift = (TextView)findViewById(R.id.tv_gift);
        et_gift = (EditText)findViewById(R.id.et_gift);
        v_gift = findViewById(R.id.v_gift);
        tv_gift_obligatory = (TextView)findViewById(R.id.tv_gift_obligatory);
    
        // price
        iv_price = (ImageView)findViewById(R.id.iv_price);
        ll_price = (LinearLayout)findViewById(R.id.ll_price);
        tv_price = (TextView)findViewById(R.id.tv_price);
        et_price = (EditText)findViewById(R.id.et_price);
        v_price = findViewById(R.id.v_price);
    
        // date_gifting
        iv_date_gifting = (ImageView)findViewById(R.id.iv_date_gifting);
        ll_date_gifting = (LinearLayout)findViewById(R.id.ll_date_gifting);
        tv_date_gifting = (TextView)findViewById(R.id.tv_date_gifting);
        et_date_gifting = (EditText)findViewById(R.id.et_date_gifting);
        v_date_gifting = findViewById(R.id.v_date_gifting);
        cb_date_gifting_notify = (CheckBox)findViewById(R.id.cb_date_gifting_notify);
        ll_date_gifting_notify = (LinearLayout)findViewById(R.id.ll_date_gifting_notify);
        tv_date_gifting_notify = (TextView)findViewById(R.id.tv_date_gifting_notify);
        // time_gifting
        ll_time_gifting = (LinearLayout)findViewById(R.id.ll_time_gifting);
        tv_time_gifting = (TextView)findViewById(R.id.tv_time_gifting);
        et_time_gifting = (EditText)findViewById(R.id.et_time_gifting);
        v_time_gifting = findViewById(R.id.v_time_gifting);
        
        // date_buying
        iv_date_buying = (ImageView)findViewById(R.id.iv_date_buying);
        ll_date_buying = (LinearLayout)findViewById(R.id.ll_date_buying);
        tv_date_buying = (TextView)findViewById(R.id.tv_date_buying);
        et_date_buying = (EditText)findViewById(R.id.et_date_buying);
        v_date_buying = findViewById(R.id.v_date_buying);
        cb_date_buying_notify = (CheckBox)findViewById(R.id.cb_date_buying_notify);
        ll_date_buying_notify = (LinearLayout)findViewById(R.id.ll_date_buying_notify);
        tv_date_buying_notify = (TextView)findViewById(R.id.tv_date_buying_notify);
        // time_buying
        ll_time_buying = (LinearLayout)findViewById(R.id.ll_time_buying);
        tv_time_buying = (TextView)findViewById(R.id.tv_time_buying);
        et_time_buying = (EditText)findViewById(R.id.et_time_buying);
        v_time_buying = findViewById(R.id.v_time_buying);
        
        // link
        iv_link = (ImageView)findViewById(R.id.iv_link);
        ll_link = (LinearLayout)findViewById(R.id.ll_link);
        tv_link = (TextView)findViewById(R.id.tv_link);
        et_link = (EditText)findViewById(R.id.et_link);
        v_link = findViewById(R.id.v_link);
    }
    void setup_views()
    {
        set_gift();
        set_price();
        set_date_gifting();
        set_time_gifting();
        set_date_buying();
        set_time_buying();
        set_link();
    
        // SETUP TOUCH ON EDITTEXT
        viewB.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        hide_keyboard();
                    }
                }
        );
    }
    void set_gift()
    {
        // gift
        ll_gift.setOnClickListener( // gift REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        touch_editText(et_gift);
                    }
                }
        );
        et_gift.setOnFocusChangeListener( // gift TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_gift, v_gift, tv_gift_obligatory);
                        else reset(tv_gift, v_gift, et_gift);
                    }
                }
        );
    }
    void set_price()
    {
        // price
        ll_price.setOnClickListener( // price REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        touch_editText(et_price);
                    }
                }
        );
        et_price.setOnFocusChangeListener( // price TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_price, v_price, null);
                        else reset(tv_price, v_price, et_price);
                    }
                }
        );
    }
    void set_date_gifting()
    {
        // date_gifting
        ll_date_gifting.setOnClickListener( // DATE REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        DatePickerDialog dialog = new DatePickerDialog(
                                ModifyGiftActivity.this,
                                new DatePickerDialog.OnDateSetListener()
                                {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month,
                                                          int dayOfMonth)
                                    {
                                        ModifyGiftActivity.this.day_gifting = dayOfMonth;
                                        ModifyGiftActivity.this.month_gifting = month;
                                        ModifyGiftActivity.this.year_gifting = year;
                                        et_date_gifting.setText(String.format("%02d.%02d.%02d", dayOfMonth, month + 1, year));
                                        touch_editText(et_date_gifting);
                                    }
                                },
                                year_gifting,
                                month_gifting,
                                day_gifting
                        );
                        dialog.show();
                        highlight_green(tv_date_gifting, v_date_gifting, null);
                    }
                }
        );
        et_date_gifting.setOnFocusChangeListener( // date_gifting TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_date_gifting, v_date_gifting, null);
                        else reset(tv_date_gifting, v_date_gifting, et_date_gifting);
                    }
                }
        );
    }
    void set_time_gifting()
    {
        // time_gifting
        ll_time_gifting.setOnClickListener( // time REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        TimePickerDialog dialog = new TimePickerDialog(
                                ModifyGiftActivity.this,
                                new TimePickerDialog.OnTimeSetListener()
                                {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute)
                                    {
                                        ModifyGiftActivity.this.h_gifting = hourOfDay;
                                        ModifyGiftActivity.this.m_gifting = minute;
                                        et_time_gifting.setText(String.format("%02d:%02d", hourOfDay, minute));
                                        touch_editText(et_time_gifting);
                                    }
                                }, h_gifting, m_gifting, true
                        );
                        dialog.show();
                        highlight_green(tv_time_gifting, v_time_gifting, null);
                    }
                }
        );
        et_time_gifting.setOnFocusChangeListener( // time_gifting TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_time_gifting, v_time_gifting, null);
                        else reset(tv_time_gifting, v_time_gifting, et_time_gifting);
                    }
                }
        );
    }
    void set_date_buying()
    {
        // date_buying
        ll_date_buying.setOnClickListener( // DATE REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        DatePickerDialog dialog = new DatePickerDialog(
                                ModifyGiftActivity.this,
                                new DatePickerDialog.OnDateSetListener()
                                {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month,
                                                          int dayOfMonth)
                                    {
                                        ModifyGiftActivity.this.day_buying = dayOfMonth;
                                        ModifyGiftActivity.this.month_buying = month;
                                        ModifyGiftActivity.this.year_buying = year;
                                        et_date_buying.setText(String.format("%02d.%02d.%02d", dayOfMonth, month + 1, year));
                                        touch_editText(et_date_buying);
                                    }
                                },
                                year_buying,
                                month_buying,
                                day_buying
                        );
                        dialog.show();
                        highlight_green(tv_date_buying, v_date_buying, null);
                    }
                }
        );
        et_date_buying.setOnFocusChangeListener( // date_buying TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_date_buying, v_date_buying, null);
                        else reset(tv_date_buying, v_date_buying, et_date_buying);
                    }
                }
        );
    }
    void set_time_buying()
    {
        // time_buying
        ll_time_buying.setOnClickListener( // time REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        TimePickerDialog dialog = new TimePickerDialog(
                                ModifyGiftActivity.this,
                                new TimePickerDialog.OnTimeSetListener()
                                {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute)
                                    {
                                        ModifyGiftActivity.this.h_buying = hourOfDay;
                                        ModifyGiftActivity.this.m_buying = minute;
                                        et_time_buying.setText(String.format("%02d:%02d", hourOfDay, minute));
                                        touch_editText(et_time_buying);
                                    }
                                }, h_buying, m_buying, true
                        );
                        dialog.show();
                        highlight_green(tv_time_buying, v_time_buying, null);
                    }
                }
        );
        et_time_buying.setOnFocusChangeListener( // time_buying TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_time_buying, v_time_buying, null);
                        else reset(tv_time_buying, v_time_buying, et_time_buying);
                    }
                }
        );
    }
    void set_link()
    {
        // link
        ll_link.setOnClickListener( // link REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        touch_editText(et_link);
                    }
                }
        );
        et_link.setOnFocusChangeListener( // link TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_link, v_link, null);
                        else reset(tv_link, v_link, et_link);
                    }
                }
        );
    }
    
    void fill_in_views()
    {
        et_gift.setText(g.getGift());
        et_gift.setVisibility(View.VISIBLE);
        //
        if (g.getPrice() != null)//
        {
            et_price.setText(g.getPrice());
            et_price.setVisibility(View.VISIBLE);
        }
        if (g.getDate_time_gifting() != null)
        {
            Date d = g.getDate_time_gifting();
            day_gifting = d.getDate();
            month_gifting = d.getMonth();
            year_gifting = d.getYear() + 1900;;
            h_gifting = d.getHours();
            m_gifting = d.getMinutes();
    
            et_date_gifting.setText(String.format("%02d.%02d.%02d", day_gifting, month_gifting + 1, year_gifting));
            et_date_gifting.setVisibility(View.VISIBLE);
            et_time_gifting.setText(String.format("%02d:%02d", h_gifting, m_gifting));
            et_time_gifting.setVisibility(View.VISIBLE);
        }
        if (g.getDate_time_buying() != null)
        {
            Date d = g.getDate_time_buying();
            day_buying = d.getDate();
            month_buying = d.getMonth();
            year_buying = d.getYear() + 1900;;
            h_buying = d.getHours();
            m_buying = d.getMinutes();
    
            et_date_buying.setText(String.format("%02d.%02d.%02d", day_buying, month_buying + 1, year_buying));
            et_date_buying.setVisibility(View.VISIBLE);
            et_time_buying.setText(String.format("%02d:%02d", h_buying, m_buying));
            et_time_buying.setVisibility(View.VISIBLE);
        }
        cb_date_gifting_notify.setChecked(g.isNotify_gifting());
        cb_date_buying_notify.setChecked(g.isNotify_buying());
        if (g.getLink() != null)//
        {
            et_link.setText(g.getLink());
            et_link.setVisibility(View.VISIBLE);
        }
    
        if (g.getPhoto() != null)
        {
            stringUri = g.getPhoto().getFilename();
            Uri uri = Uri.parse(stringUri);
            viewA.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getApplicationContext()).load(uri).into(viewA);
            added_photo = true;
        }
        else
        {
            stringUri = null;
            viewA.setScaleType(ImageView.ScaleType.CENTER);
            viewA.setImageDrawable(getResources().getDrawable(R.drawable.gift_no_photo));
            added_photo = false;
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_gift);
        
        // action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        // photo
        FloatingActionButton fab_photo = (FloatingActionButton) findViewById(R.id.fab_photo);
        fab_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                photo_dialog().show();
            }
        });
        
        g = PersonActivity.selectedGift;
    
        GregorianCalendar calendar = new GregorianCalendar();
        year_gifting = calendar.get(Calendar.YEAR);
        month_gifting = calendar.get(Calendar.MONTH);
        day_gifting = calendar.get(Calendar.DAY_OF_MONTH);
        h_gifting = calendar.get(Calendar.HOUR_OF_DAY);
        m_gifting = calendar.get(Calendar.MINUTE);
    
        year_buying = calendar.get(Calendar.YEAR);
        month_buying = calendar.get(Calendar.MONTH);
        day_buying = calendar.get(Calendar.DAY_OF_MONTH);
        h_buying = calendar.get(Calendar.HOUR_OF_DAY);
        m_buying = calendar.get(Calendar.MINUTE);
        
        // fields
        init_views();
        setup_views();
        fill_in_views();
    }
    void highlight_green(TextView tv, View v, TextView obligatory)
    {
        tv.setTextColor(getResources().getColor(R.color.colorAccent)); // greenify
        v.setBackgroundColor(getResources().getColor(R.color.colorAccent)); // all
        if (obligatory != null) obligatory.setVisibility(View.INVISIBLE); // them
    }
    void highlight_red(TextView tv, View v, TextView obligatory, EditText et)
    {
        tv.setTextColor(getResources().getColor(android.R.color.holo_red_light)); // redify
        v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light)); // all
        if (obligatory != null) obligatory.setVisibility(View.VISIBLE); // them
        et.setVisibility(View.VISIBLE);
    }
    void reset(TextView tv, View v, EditText et)
    {
        tv.setTextColor(getResources().getColor(R.color.tv_default));
        v.setBackgroundColor(getResources().getColor(R.color.v_default));
        if (et.getText().length() == 0) et.setVisibility(View.GONE); // hide if empty
    }
    void touch_editText(EditText et)
    {
        if (et.getVisibility() == View.GONE) et.setVisibility(View.VISIBLE); // show if hidden
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }
    void hide_keyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        findViewById(R.id.dummy).requestFocus();
    }
    AlertDialog photo_dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                ModifyGiftActivity.this);
        builder.setItems(added_photo ? R.array.fab_has_photo : R.array.fab_no_photo,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        boolean result = Utility.checkPermission(ModifyGiftActivity.this);
                        if (which == 0)
                        {
                            choice = SELECT_FILE;
                            galleryIntent();
                        }
                        else if (which == 1)
                        {
                            choice = REQUEST_CAMERA;
                            cameraIntent();
                        }
                        else if (which == 2)
                        {
                            choice = -1;
                            reset_image();
                        }
                    }
                }
        );
        return builder.create();
    }
    AlertDialog back_dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                ModifyGiftActivity.this);
        builder.setMessage("Сохранить изменения?")
                .setPositiveButton("Сохранить",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                boolean success = modifyGiftItem();
                                if (success) finish();
                            }
                        })
                .setNegativeButton("Отменить",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();
                            }
                        });
        return builder.create();
    }
    boolean modifyGiftItem()
    {
        hide_keyboard();
        
        String gift;//
        String price;//
        
        String sdate_gifting;//
        Date date_gifting = null;//
        String stime_gifting;//
        Date time_gifting = null;//
        
        String sdate_buying;//
        Date date_buying = null;//
        String stime_buying;//
        Date time_buying = null;//
        
        boolean notify_gifting;//
        boolean notify_buying;//
        
        String link;//
        Photo photo = null;//
        
        gift = et_gift.getText().toString();
        price = et_price.getText().toString();
        sdate_gifting = et_date_gifting.getText().toString();
        stime_gifting = et_time_gifting.getText().toString();
        sdate_buying = et_date_buying.getText().toString();
        stime_buying = et_time_buying.getText().toString();
        link = et_link.getText().toString();
        notify_gifting = cb_date_gifting_notify.isChecked();
        notify_buying = cb_date_buying_notify.isChecked();
        
        if (gift.equals(""))
        { // redify gift
            highlight_red(tv_gift, v_gift, tv_gift_obligatory, et_gift);
            return false;
        }
        
        if (!sdate_gifting.equals(""))
        {
            Date d = assign_date(sdate_gifting, tv_date_gifting, v_date_gifting, et_date_gifting);
            if (d == null) return false;
            date_gifting = d;
            if (!stime_gifting.equals(""))
            {
                Date d2 = assign_time(stime_gifting, tv_time_gifting, v_time_gifting, et_time_gifting);
                if (d2 == null) return false;
                time_gifting = d2;
                if (date_gifting != null)
                {
                    date_gifting.setHours(time_gifting.getHours());
                    date_gifting.setMinutes(time_gifting.getMinutes());
                }
            }
            else // if user set only date
                if (date_gifting != null)
                {
                    date_gifting.setHours(13);
                    date_gifting.setMinutes(0);
                }
        }
        
        if (!sdate_buying.equals(""))
        {
            Date d = assign_date(sdate_buying, tv_date_buying, v_date_buying, et_date_buying);
            if (d == null) return false;
            date_buying = d;
            if (!stime_buying.equals(""))
            {
                Date d2 = assign_time(stime_buying, tv_time_buying, v_time_buying, et_time_buying);
                if (d2 == null) return false;
                time_buying = d2;
                if (date_buying != null)
                {
                    date_buying.setHours(time_buying.getHours());
                    date_buying.setMinutes(time_buying.getMinutes());
                }
            }
            else // if user set only date
                if (date_buying != null)
                {
                    date_buying.setHours(13);
                    date_buying.setMinutes(0);
                }
        }
        // setup photo
        if (stringUri != null)
            photo = new Photo(stringUri);
        else
            photo = null;
    
        g.setGift(gift);
        g.setPrice(price);
        g.setDate_time_gifting(date_gifting);
        g.setDate_time_buying(date_buying);
        g.setNotify_gifting(notify_gifting);
        g.setNotify_buying(notify_buying);
        g.setLink(link);
        g.setPhoto(photo);
    
    
    
        if (notify_gifting)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(g.getDate_time_gifting());
            cal.add(Calendar.DATE, -10);
            Date dateBefore10Days = cal.getTime();
            Date now = new Date();
            if (now.getTime() > dateBefore10Days.getTime()) dateBefore10Days = g.getDate_time_gifting();
            
            Intent resultIntent = new Intent(this, MainActivity.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.whiteedited)
                    .setContentTitle("Название подарка: " + gift)
                    .setContentText("Нужно подарить " + gift
                            + " " + PersonActivity.p.getName()
                            + " " + "(" + sdate_gifting + ")")
                    .setContentInfo("Подарить " + sdate_gifting)
                    .setColor(getResources().getColor(R.color.colorAccent))
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setWhen(dateBefore10Days.getTime());
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.cancel(g.getId().hashCode());
            notificationManager.notify(g.getId().hashCode(), builder.build());
        }
        if (notify_buying)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(g.getDate_time_buying());
            cal.add(Calendar.DATE, -10);
            Date dateBefore10Days = cal.getTime();
            Date now = new Date();
            if (now.getTime() > dateBefore10Days.getTime()) dateBefore10Days = g.getDate_time_gifting();
        
            Intent resultIntent = new Intent(this, MainActivity.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.whiteedited)
                    .setContentTitle("Название подарка: " + gift)
                    .setContentText("Нужно купить " + gift
                            + " для " + PersonActivity.p.getName()
                            + " " + "(" + sdate_buying + ")")
                    .setContentInfo("Купить подарок " + sdate_buying)
                    .setColor(getResources().getColor(R.color.colorAccent))
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setWhen(dateBefore10Days.getTime());
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.cancel(g.getId().hashCode());
            notificationManager.notify(g.getId().hashCode() + 1, builder.build());
        }
    
    
    
        Toast t = Toast.makeText(getApplicationContext(), "Подарок добавлен", Toast.LENGTH_SHORT);
        t.show();
        return true;
    }
    Date assign_date(String sdate, TextView tv_date, View v_date, EditText et_date)
    {
        String[] pieces;
        pieces = sdate.split("[.]");
        if (pieces.length != 3)
        {
            // redify date
            highlight_red(tv_date, v_date, null, et_date);
            return null;
        }
        int day = Integer.parseInt(pieces[0]);
        int month = Integer.parseInt(pieces[1]) - 1;
        int year = Integer.parseInt(pieces[2]);
        return new GregorianCalendar(year, month, day).getTime();
    }
    Date assign_time(String stime, TextView tv_time, View v_time, EditText et_time)
    {
        String[] pieces;
        pieces = stime.split("[:]");
        if (pieces.length != 2)
        {
            // redify time
            highlight_red(tv_time, v_time, null, et_time);
            return null;
        }
        int h = Integer.parseInt(pieces[0]);
        int m = Integer.parseInt(pieces[1]);
        return new GregorianCalendar(0, 0, 0, h, m).getTime();
    }
    
    
    private void cameraIntent()
    {
//        Toast.makeText(AddPersonActivity.this, "Сделать снимок", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }
    private void galleryIntent()
    {
//        Toast.makeText(AddPersonActivity.this, "Загрузить из галереи", Toast.LENGTH_SHORT).show();
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }
    
    void load_uri_image()
    {
        Uri uri;
        uri = Uri.parse(stringUri);
        viewA.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getApplicationContext()).load(uri).into(viewA);
        added_photo = true;
    }
    void reset_image()
    {
        viewA.setScaleType(ImageView.ScaleType.CENTER);
        viewA.setImageDrawable(getResources().getDrawable(R.drawable.person_no_photo));
        stringUri = null;
        added_photo = false;
    }
    
    private void onSelectFromGalleryResult(Intent imageReturnedIntent)
    {
        Uri selectedImage = imageReturnedIntent.getData();
        stringUri = selectedImage.toString();
        load_uri_image();
    }
    private void onCaptureImageResult(Intent imageReturnedIntent)
    {
        Bitmap thumbnail = (Bitmap) imageReturnedIntent.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        
        try {
            destination.createNewFile();
            FileOutputStream fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        stringUri = Uri.fromFile(destination).toString();
        load_uri_image();
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(imageReturnedIntent);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(imageReturnedIntent);
        }
    }
    
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed()
    {
        if (et_gift.getText().toString().equals("")
                && et_price.getText().toString().equals("")
                && et_date_gifting.getText().toString().equals("")
                && et_time_gifting.getText().toString().equals("")
                && et_date_buying.getText().toString().equals("")
                && et_time_buying.getText().toString().equals("")
                && et_link.getText().toString().equals("")
                )
            finish();
        else
            back_dialog().show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    { // adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_person, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_confirm)
        {
            boolean success = modifyGiftItem();
            if (success) finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        PersonGen.get(this).save();
    }
}
