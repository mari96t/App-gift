package com.cloudstechnologies.android.giftapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class AddPersonActivity extends AppCompatActivity
{
    ImageView viewA;
    LinearLayout viewB;
    
    // name
    ImageView iv_name;
    LinearLayout ll_name;
    TextView tv_name;
    EditText et_name;
    View v_name;
    TextView tv_name_obligatory;
    
    // date
    ImageView iv_date;
    LinearLayout ll_date;
    TextView tv_date;
    EditText et_date;
    View v_date;
    TextView tv_date_obligatory;
    
    // aux
    int day;
    int month;
    int year;
    
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    
    private int choice = -1;
    private boolean added_photo = false;
    String stringUri = null;
    
    void init_views()
    {
        viewA = (ImageView) findViewById(R.id.viewA);
        viewB = (LinearLayout) findViewById(R.id.viewB);
    
        // NAME
        iv_name = (ImageView) findViewById(R.id.iv_name);
        ll_name = (LinearLayout) findViewById(R.id.ll_name);
        tv_name = (TextView) findViewById(R.id.tv_name);
        et_name = (EditText) findViewById(R.id.et_name);
        v_name = findViewById(R.id.v_name);
        tv_name_obligatory = (TextView) findViewById(R.id.tv_name_obligatory);
        // DATE
        iv_date = (ImageView) findViewById(R.id.iv_date);
        ll_date = (LinearLayout) findViewById(R.id.ll_date);
        tv_date = (TextView) findViewById(R.id.tv_date);
        et_date = (EditText) findViewById(R.id.et_date);
        v_date = findViewById(R.id.v_date);
        tv_date_obligatory = (TextView) findViewById(R.id.tv_date_obligatory);
    }
    void setup_views()
    {
        // name region LL
        ll_name.setOnClickListener( // NAME REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        touch_editText(et_name);
                    }
                }
        );
        // name editable
        et_name.setOnFocusChangeListener( // NAME TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_name, v_name, tv_name_obligatory);
                        else reset(tv_name, v_name, et_name);
                    }
                }
        );
        // date region LL
        ll_date.setOnClickListener( // DATE REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        DatePickerDialog dialog = new DatePickerDialog(
                                AddPersonActivity.this,
                                new DatePickerDialog.OnDateSetListener()
                                {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month,
                                                          int dayOfMonth)
                                    {
                                        AddPersonActivity.this.day = dayOfMonth;
                                        AddPersonActivity.this.month = month;
                                        AddPersonActivity.this.year = year;
                                        et_date.setText(String.format("%02d.%02d.%02d", dayOfMonth, month + 1, year));
                                        touch_editText(et_date);
                                    }
                                },
                                year,
                                month,
                                day
                        );
                        dialog.show();
                        highlight_green(tv_date, v_date, tv_date_obligatory);
                    }
                }
        );
        // date editable
        et_date.setOnFocusChangeListener( // DATE TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_date, v_date, tv_date_obligatory);
                        else reset(tv_date, v_date, et_date);
                    }
                }
        ); // name region LL
        ll_name.setOnClickListener( // NAME REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        touch_editText(et_name);
                    }
                }
        );
        // name editable
        et_name.setOnFocusChangeListener( // NAME TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_name, v_name, tv_name_obligatory);
                        else reset(tv_name, v_name, et_name);
                    }
                }
        );
        // date region LL
        ll_date.setOnClickListener( // DATE REGION
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        DatePickerDialog dialog = new DatePickerDialog(
                                AddPersonActivity.this,
                                new DatePickerDialog.OnDateSetListener()
                                {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month,
                                                          int dayOfMonth)
                                    {
                                        AddPersonActivity.this.day = dayOfMonth;
                                        AddPersonActivity.this.month = month;
                                        AddPersonActivity.this.year = year;
                                        et_date.setText(String.format("%02d.%02d.%02d", dayOfMonth, month + 1, year));
                                        touch_editText(et_date);
                                    }
                                },
                                year,
                                month,
                                day
                        );
                        dialog.show();
                        highlight_green(tv_date, v_date, tv_date_obligatory);
                    }
                }
        );
        // date editable
        et_date.setOnFocusChangeListener( // DATE TEXT
                new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (hasFocus) highlight_green(tv_date, v_date, tv_date_obligatory);
                        else reset(tv_date, v_date, et_date);
                    }
                }
        );
    
    
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        
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
        
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        
        // fields
        init_views();
        setup_views();
    }
    void highlight_green(TextView tv, View v, TextView obligatory)
    {
        tv.setTextColor(getResources().getColor(R.color.colorAccent)); // greenify
        v.setBackgroundColor(getResources().getColor(R.color.colorAccent)); // all
        obligatory.setVisibility(View.INVISIBLE); // them
    }
    void highlight_red(TextView tv, View v, TextView obligatory, EditText et)
    {
        tv.setTextColor(getResources().getColor(android.R.color.holo_red_light)); // redify
        v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light)); // all
        obligatory.setVisibility(View.VISIBLE); // them
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
                AddPersonActivity.this);
        builder.setItems(added_photo ? R.array.fab_has_photo : R.array.fab_no_photo,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                boolean result = Utility.checkPermission(AddPersonActivity.this);
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
                AddPersonActivity.this);
        builder.setMessage("Сохранить изменения?")
                .setPositiveButton("Сохранить",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                boolean success = addPersonItem();
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
    boolean addPersonItem()
    {
        hide_keyboard();
    
        String name;
        String sdate;
        Date date;
        Photo photo;
        int count;
        
        // setup name
        name = et_name.getText().toString();
        if (name.equals(""))
        {
            // redify name
            highlight_red(tv_name, v_name, tv_name_obligatory, et_name);
            return false;
        }
        // setup date
        sdate = et_date.getText().toString();
        if (!sdate.equals(""))
        {
            String[] pieces;
            pieces = sdate.split("[.]");
            if (pieces.length != 3)
            {
                // redify date
                highlight_red(tv_date, v_date, tv_date_obligatory, et_date);
                return false;
            }
    
            int day = Integer.parseInt(pieces[0]);
            int month = Integer.parseInt(pieces[1]) - 1;
            int year = Integer.parseInt(pieces[2]);
            date = new GregorianCalendar(year, month, day).getTime();
        }
        else
            date = null;
        // setup photo
        if (stringUri != null)
            photo = new Photo(stringUri);
        else
            photo = null;
        // setup count
        count = 0;
    
        // add person
        PersonGen.get(this).add(new Person(name, date, photo, count));
        Toast t = Toast.makeText(getApplicationContext(), "Персона добавлена", Toast.LENGTH_SHORT);
        t.show();
    
        return true;
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
        if (et_name.getText().toString().equals("")
                && et_date.getText().toString().equals("")) finish();
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
            boolean success = addPersonItem();
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
    
    @Override
    protected void onDestroy()
    {
        PersonGen.get(this).save();
        super.onDestroy();
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (choice == REQUEST_CAMERA)
                        cameraIntent();
                    else if (choice == SELECT_FILE)
                        galleryIntent();
                }
                else
                {
                    // denied
                    Toast.makeText(this, "Пользователем не было дано прав доступа", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
