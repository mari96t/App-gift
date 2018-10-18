package com.cloudstechnologies.android.giftapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<Person> persons;
    
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ListView listView;
    private PersonAdapter adapter;
    
    private class PersonAdapter extends ArrayAdapter<Person>
    {
        private MainActivity activity;
        
        public PersonAdapter(ArrayList<Person> persons, MainActivity activity)
        {
            super(activity, 0, persons);
            this.activity = activity;
        }
    
        private MainActivity getActivity() {return activity;}
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_person, null);
    
            // init
            Person p = persons.get(position);
            TextView textView_name = (TextView)convertView.findViewById(R.id.textView_name);
            TextView textView_desc = (TextView)convertView.findViewById(R.id.textView_desc);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
    
            // setup
            textView_name.setText(p.getName());
            textView_desc.setText("Количество подарков: " + p.getCount());
            
            if (p.getPhoto() != null)
            {
                Uri uri = Uri.parse(p.getPhoto().getFilename());
                Glide.with(getApplicationContext()).load(uri).into(imageView);
            }
            else
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_grey_72dp));
            }
            
            return convertView;
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        // init
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, AddPersonActivity.class);
                startActivityForResult(i, Activity.RESULT_OK);
            }
        });
        listView = (ListView) findViewById(R.id.list);
    
        // setup
        persons = PersonGen.get(this).getPersons();
        adapter = new PersonAdapter(persons, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Intent i = new Intent(MainActivity.this, PersonActivity.class);
                        selectedPerson = (Person)adapter.getItem(position);
                        startActivityForResult(i, Activity.RESULT_OK);
                    }
                });
        listView.setEmptyView(findViewById(R.id.empty_list));
        registerForContextMenu(listView);
    }
    
    public static Person selectedPerson;
    
    @Override
    protected void onResume()
    {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        this.getMenuInflater().inflate(R.menu.context_delete_person, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Person person = adapter.getItem(info.position);
        switch (item.getItemId())
        {
            case R.id.menu_item_delete_person:
                PersonGen.get(this).delete(person);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
