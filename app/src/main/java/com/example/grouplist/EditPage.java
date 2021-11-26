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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.grouplist.toStore.Person;
import com.example.grouplist.toStore.csvWorker;
import com.example.grouplist.toStore.jsonWorker;
import com.example.grouplist.toStore.txtWorker;
import com.example.grouplist.toStore.xmlWorker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


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

    CheckBox csvCheck;
    CheckBox txtCheck;
    CheckBox jsonCheck;
    CheckBox xmlCheck;
    TextView outputTxt;

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

        txtCheck = (CheckBox)findViewById(R.id.txtBox);
        csvCheck = (CheckBox) findViewById(R.id.csvCheck);
        jsonCheck = (CheckBox) findViewById(R.id.jsonCheck);
        xmlCheck = (CheckBox)findViewById(R.id.xmlCheck);

        btnOk.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        etName.setOnClickListener(v -> etName.getText().clear());

        String[] arraySpinner = new String[] {
                "Click to select IDE", "Android Studio", "IntelliJ IDEA", "VS Code", "Visual Studio 2019", "Eclipse", "SublimeText",
        };
        Spinner ideList = (Spinner) findViewById(R.id.ideList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ideList.setAdapter(adapter);

        String[] arraySecondSpinner = new String[] {
                "Click to select Language", "Python", "C++", "Java", "Basic", "Kotlin", "C#",
        };
        Spinner langList = (Spinner) findViewById(R.id.langList);
        ArrayAdapter<String> secAdapter = new ArrayAdapter<String>(this,
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

    public String Boxes(String name, String sex, String ide, String lang)
    {
        String data = "";
        String check = "";

        if (txtCheck.isChecked())
        {
            data = name + ";" + sex + ";" + ide + ";" + lang + ";\n";
            txtWorker.txtWrite(data, this);
            check += "txt ";
        }

        if (csvCheck.isChecked())
        {
            data = name + ";" + sex + ";" + ide + ";" + lang + ";\n";
            csvWorker.WriteToCsv(data, this);
            check += "csv ";
        }

        if (jsonCheck.isChecked())
        {
            //json save
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", name);
                jsonObject.put("Sex", sex);
                jsonObject.put("IDE", ide);
                jsonObject.put("Lang", lang);
                jsonWorker.writeJson(jsonObject, this);

                check += "json ";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (xmlCheck.isChecked())
        {
            // xml write
            Person person = new Person(name, sex, ide, lang);
            try {
                xmlWorker.CreateXMLString(person, this);

                check += "xml ";
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return check;

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

            check = Boxes(name, sex, ide, lang);

            if (!position.isEmpty()) {
                // Значит элемент в режиме редактирования
                intent.putExtra("position", position);
                position = "";
            }

            intent.putExtra("name", name);
            intent.putExtra("sex", sex);
            intent.putExtra("ide", ide);
            intent.putExtra("language", lang);
            intent.putExtra("file", check);

            setResult(RESULT_OK, intent);

            Toast.makeText(this, "A new item was created!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "The fields were not filled in...", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, null);
        }

        finish();
    }

}