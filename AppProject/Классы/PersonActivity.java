package com.cloudstechnologies.android.giftapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PersonActivity extends AppCompatActivity
{
    static Person p;
    
    ImageView viewA;
    ListView lv_gifts;
    TextView tv_name;
    TextView tv_date;
    TextView tv_desc;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
    
        // action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        // photo
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(PersonActivity.this, AddGiftActivity.class);
                startActivityForResult(i, Activity.RESULT_OK);
            }
        });
        
        p = MainActivity.selectedPerson;
        if (p == null) finish();
    
        // init
        viewA = (ImageView)findViewById(R.id.viewA);
        lv_gifts = (ListView) findViewById(R.id.lv_gifts);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        
        lv_gifts.setEmptyView(findViewById(R.id.empty_list));
    
        giftArrayList = p.getGiftArrayList();
        giftAdapter = new GiftAdapter(giftArrayList, this);
        lv_gifts.setAdapter(giftAdapter);
        lv_gifts.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
//                        Intent i = new Intent(PersonActivity.this, ModifyGiftActivity.class);
                        Intent i = new Intent(PersonActivity.this, GiftActivity.class);
                        selectedGift = (Gift) giftAdapter.getItem(position);
                        startActivityForResult(i, Activity.RESULT_OK);
                    }
                });
    }
    
    @Override
    protected void onResume()
    {
        p.update_count();
        p.update_readygifts();
        giftAdapter.notifyDataSetChanged();
        super.onResume();
        
        String name = p.getName();
        tv_name.setText(name);
    
        String sdate = "Дата рождения: ";
        Date date = p.getDate();
        if (date != null)
            sdate += (new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)).format(date);
        tv_date.setText(sdate);
    
        int count = p.getCount();
        int readygifts = p.getReadygifts();
        tv_desc.setText("Куплено подарков: " + readygifts + " из " + count);
        
        Uri uri;
        if (p.getPhoto() != null)
        {
            uri = Uri.parse(p.getPhoto().getFilename());
            viewA.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getApplicationContext()).load(uri).into(viewA);
        }
        else
        {
            viewA.setScaleType(ImageView.ScaleType.CENTER);
            viewA.setImageDrawable(getResources().getDrawable(R.drawable.person_no_photo));
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person, menu);
        return true;
    }
    // >connected<
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.modify_person)
        {
            Intent i = new Intent(PersonActivity.this, ModifyPersonActivity.class);
            startActivityForResult(i, Activity.RESULT_OK);
            return true;
        }
        else if (id == R.id.delete_person)
        {
            delete_person_dialog().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    AlertDialog delete_person_dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                PersonActivity.this);
        builder.setMessage("Удалить персону?")
                .setPositiveButton("Удалить",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                PersonGen.get(PersonActivity.this).delete(PersonActivity.p);
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
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
    
    public static Gift selectedGift;
    
    private ArrayList<Gift> giftArrayList;
    private GiftAdapter giftAdapter;
    
    private class GiftAdapter extends ArrayAdapter<Gift>
    {
        private PersonActivity activity;
        
        public GiftAdapter(ArrayList<Gift> gifts, PersonActivity activity)
        {
            super(activity, 0, gifts);
            this.activity = activity;
        }
        
        private PersonActivity getActivity() {return activity;}
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_gift, null);
            
            // init
            Gift gift = giftArrayList.get(position);
            
            TextView textView_gift = (TextView)convertView.findViewById(R.id.textView_gift);
            TextView textView_price = (TextView)convertView.findViewById(R.id.textView_price);
            TextView textView_date_gifting = (TextView)convertView.findViewById(R.id.textView_date_gifting);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
            
            // setup
            String sgift;
            String sprice;
            String sdate;
            sgift = gift.getGift();
            textView_gift.setText(sgift);
            sprice = "Цена: ";
            if (gift.getPrice() != null)
                sprice += gift.getPrice();
            textView_price.setText(sprice);
            sdate = "Дата вручения: ";
            if (gift.getDate_time_gifting() != null)
                sdate += (new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)).format(gift.getDate_time_gifting());
            textView_date_gifting.setText(sdate);
            if (gift.getPhoto() != null)
            {
                Uri uri = Uri.parse(gift.getPhoto().getFilename());
                Glide.with(getApplicationContext()).load(uri).into(imageView);
            }
            else
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.empty_gifts_th));
            }
            if (gift.isReady())
            {
                convertView.findViewById(R.id.textView_ready).setVisibility(View.VISIBLE);
                convertView.setAlpha(.5f);
            }
            else
            {
                convertView.findViewById(R.id.textView_ready).setVisibility(View.GONE);
                convertView.setAlpha(1.0f);
            }
            
            return convertView;
        }
    }
}
