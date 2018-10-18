package com.cloudstechnologies.android.giftapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
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
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GiftActivity extends AppCompatActivity
{
    Gift g;
    
    ImageView viewA;
    LinearLayout viewB;
    
    // gift
    TextView tv_gift;
    // price
    TextView tv_price;
    // date_gifting
    TextView tv_date_gifting;
    // time_gifting
    TextView tv_time_gifting;
    // date_buying
    TextView tv_date_buying;
    // time_buying
    TextView tv_time_buying;
    // link
    TextView tv_link;
    // ready
    TextView tv_ready;
    
    void init_views()
    {
        viewA = (ImageView) findViewById(R.id.viewA);
        viewB = (LinearLayout) findViewById(R.id.viewB);
        
        // gift
        tv_gift = (TextView)findViewById(R.id.tv_gift);
        tv_price = (TextView)findViewById(R.id.tv_price);
        tv_date_gifting = (TextView)findViewById(R.id.tv_date_gifting);
        tv_time_gifting = (TextView)findViewById(R.id.tv_time_gifting);
        tv_date_buying = (TextView)findViewById(R.id.tv_date_buying);
        tv_time_buying = (TextView)findViewById(R.id.tv_time_buying);
        tv_link = (TextView)findViewById(R.id.tv_link);
        tv_ready = (TextView)findViewById(R.id.tv_ready);
    }
    void fill_in_views()
    {
        tv_gift.setText(g.getGift());
        tv_price.setText(g.getPrice() != null ? g.getPrice() : "");
        if (g.getDate_time_gifting() != null)
        {
            Date d = g.getDate_time_gifting();
            int day_gifting = d.getDate();
            int month_gifting = d.getMonth();
            int year_gifting = d.getYear() + 1900;;
            int h_gifting = d.getHours();
            int m_gifting = d.getMinutes();
    
            tv_date_gifting.setText(String.format("%02d.%02d.%02d", day_gifting, month_gifting + 1, year_gifting));
            tv_time_gifting.setText(String.format("%02d:%02d", h_gifting, m_gifting));
        }
        else
        {
            tv_date_gifting.setText("");
            tv_time_gifting.setText("");
        }
        if (g.getDate_time_buying() != null)
        {
            Date d = g.getDate_time_buying();
            int day_buying = d.getDate();
            int month_buying = d.getMonth();
            int year_buying = d.getYear() + 1900;;
            int h_buying = d.getHours();
            int m_buying = d.getMinutes();
    
            tv_date_buying.setText(String.format("%02d.%02d.%02d", day_buying, month_buying + 1, year_buying));
            tv_time_buying.setText(String.format("%02d:%02d", h_buying, m_buying));
        }
        else
        {
            tv_date_buying.setText("");
            tv_time_buying.setText("");
        }
        tv_link.setText(g.getLink() != null ? g.getLink() : "");
    
        if (g.getPhoto() != null)
        {
            Uri uri = Uri.parse(g.getPhoto().getFilename());
            viewA.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getApplicationContext()).load(uri).into(viewA);
        }
        else
        {
            viewA.setScaleType(ImageView.ScaleType.CENTER);
            viewA.setImageDrawable(getResources().getDrawable(android.R.color.black));
        }
        
        tv_ready.setText(g.isReady() ? "Куплено" : "Не куплено");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        
        // action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        // photo
        FloatingActionButton fab_photo = (FloatingActionButton) findViewById(R.id.fab_gift);
        fab_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PopupMenu popupMenu = new PopupMenu(GiftActivity.this, view);
                try
                {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    Object menuHelper = fMenuHelper.get(popupMenu);
                    Class[] argTypes = new Class[]{boolean.class};
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                popupMenu.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener()
                        {
                            @Override
                            public boolean onMenuItemClick(MenuItem item)
                            {
                                switch (item.getItemId())
                                {
                                    case R.id.pop_ready_gift:
                                        g.setReady(!g.isReady());
                                        tv_ready.setText(g.isReady() ? "Куплено" : "Не куплено");
                                        return true;
                                    case R.id.pop_modify_gift:
                                        Intent i = new Intent(GiftActivity.this,
                                                ModifyGiftActivity.class);
                                        startActivityForResult(i, Activity.RESULT_OK);
                                        return true;
                                    case R.id.pop_delete_gift:
                                        delete_person_dialog().show();
                                        return true;
                                }
                                return false;
                            }
                        });
                popupMenu.inflate(g.isReady() ? R.menu.popup_menu_gift_already : R.menu.popup_menu_gift);
                popupMenu.show();
            }
        });
        
        g = PersonActivity.selectedGift;
    
        init_views();
        fill_in_views();
    }
    
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
    
    AlertDialog delete_person_dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                GiftActivity.this);
        builder.setMessage("Удалить подарок?")
                .setPositiveButton("Удалить",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.cancel(g.getId().hashCode());
                                notificationManager.cancel(g.getId().hashCode() + 1);
                                PersonActivity.p.delete_gift(g);
                                onBackPressed();
                            }
                        })
                .setNegativeButton("Отменить",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                            }
                        });
        return builder.create();
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        PersonGen.get(this).save();
    }
    
    @Override
    protected void onResume()
    {
        fill_in_views();
        super.onResume();
    }
}
