package com.example.grouplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class EditPage extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    Button btnOk;
    Button backBtn;
    Spinner langList;
    Spinner ideList;
    ConstraintLayout constraint;
    CheckBox maleCheck;
    CheckBox femaleCheck;
    String position = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        constraint = (ConstraintLayout) findViewById(R.id.constraint);
        etName = (EditText) findViewById(R.id.etName);

        btnOk = (Button)findViewById(R.id.btnOk);
        backBtn =(Button)findViewById(R.id.backBtn);

        maleCheck = (CheckBox)findViewById(R.id.maleCheck);
        femaleCheck =(CheckBox)findViewById(R.id.femaleCheck);
        ideList = (Spinner) findViewById(R.id.ideList);
        langList = (Spinner) findViewById(R.id.langList);


        btnOk.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        backBtn.setOnClickListener(v -> finish());

        etName.setOnClickListener(v -> etName.getText().clear());

        String[] arraySpinner = new String[] {
                "Click to select IDE", "Android Studio", "IntelliJ IDEA", "VS Code", "Visual Studio 2019", "Eclipse", "SublimeText",
        };
        Spinner ideList = (Spinner) findViewById(R.id.ideList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ideList.setAdapter(adapter);

        String[] arraySecondSpinner = new String[] {
                "Click to select Language", "Python", "C++", "Java", "Basic", "Kotlin", "C#",
        };
        Spinner langList = (Spinner) findViewById(R.id.langList);
        ArrayAdapter<String> secAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySecondSpinner);
        secAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langList.setAdapter(secAdapter);

        try {
            getInfo();
        } catch (Exception ignore)
        {}

    }

    public void hideKeyboard(View v)
    {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    public void checker(View v) {
        try {
            if (maleCheck.isChecked() && femaleCheck.isChecked()) {
                femaleCheck.toggle();
                maleCheck.toggle();
            }

        } catch (Exception ignore)
        {}
    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i = 0;i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    protected void getInfo()
    {
        Intent i = getIntent();
        Person person = (Person) i.getSerializableExtra("student");
        //Log.d("myTag", person.getName());

        etName.setText(person.getName());

        if (person.getSex().equals("Male")){
            maleCheck.setChecked(true);
        } else if (person.getSex().equals("Female")){
            femaleCheck.setChecked(true);
        }

        ideList.setSelection(getIndex(ideList, person.getIde()));
        langList.setSelection(getIndex(langList, person.getLang()));

        position = i.getStringExtra("position");

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        String name = etName.getText().toString();
        String ide = ideList.getSelectedItem().toString();
        String lang = langList.getSelectedItem().toString();
        String sex = "";

        String check;

        if (maleCheck.isChecked()) {
            sex = "Male";
        }
        if (femaleCheck.isChecked()) {
            sex = "Female";
        }
        if (!(femaleCheck.isChecked()) && !(maleCheck.isChecked())) {
            sex = "None";
        }

        if (!(name.equals("ФИО")) &&
                !(ide.equals("Click to select IDE")) &&
                !(lang.equals("Click to select Language"))) {

            if (!position.isEmpty()) {
                // Значит элемент в режиме редактирования
                intent.putExtra("position", position);
                position = "";
            }

            intent.putExtra("name", name);
            intent.putExtra("sex", sex);
            intent.putExtra("ide", ide);
            intent.putExtra("language", lang);

            setResult(RESULT_OK, intent);

        } else {
            Toast.makeText(this, "The fields were not filled in...", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, null);
        }

        finish();
    }

}