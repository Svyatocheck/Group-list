package com.example.grouplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyAdapter.OnNoteListener {

    Button addUser;

    private final ArrayList<Person> personList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUser = (Button) findViewById(R.id.addUser);
        addUser.setOnClickListener(this);

    }

    public int findUsingIterator(String name,
                                     int position, ArrayList<Person> personList) {
        for (Person person : personList) {
            if (person.getName().equals(name)){
                int check = personList.indexOf(person);
                if (check == position) return position;
            }
        }

        return -1;
    }

    public void addUser(String name, String sex, String ide, String lang, int position)
    {
        RecyclerView recycler = findViewById(R.id.personsRecycle);
        MyAdapter listAdapter = new MyAdapter(personList, this);
        recycler.setAdapter(listAdapter);

        try {
           // int finder = findUsingIterator(name, position, personList);
            if (position > -1)
            {
                personList.get(position).setName(name);
                personList.get(position).setSex(sex);
                personList.get(position).setIde(ide);
                personList.get(position).setLang(lang);

            } else {
                Person person = new Person(name, sex, ide, lang);
                personList.add(0, person);
            }

        } catch (Exception ignore)
        {}

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditPage.class);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {

            String name = data.getStringExtra("name");
            String sex = data.getStringExtra("sex");
            String ide = data.getStringExtra("ide");
            String lang = data.getStringExtra("language");

            int position;

            try {
                position = Integer.parseInt(data.getStringExtra("position"));
                Toast.makeText(this, "Item updated!!", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "A new item was created!", Toast.LENGTH_SHORT).show();
                position = -1;
            }

            addUser(name, sex, ide, lang, position);

        }

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, EditPage.class);
        intent.putExtra("student", personList.get(position));
        intent.putExtra("position", String.valueOf(position));

        //personList.set(position, null);
        //personList.remove(position);

        startActivityForResult(intent, 2);
    }


    public void hideKeyboard(View v)
    {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }


}