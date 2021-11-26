package com.example.grouplist.toStore;
import android.util.Log;

import java.io.Serializable;

public class User implements Serializable {

    private String ide;
    private String name;
    private String sex;
    private String lang;

    public User (String name, String sex, String ide, String lang) {
        this.name = name;
        this.sex = sex;
        this.ide = ide;
        this.lang = lang;

        Log.e("Warning!", "Object created");
        Log.e("Warning!", name);
        Log.e("Warning!", sex);
    }

    public User()
    {
        this.name = "Vasya";
        this.sex = "Male";
        this.ide = "Android studio";
        this.lang = "Kotlin";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public static class ChildClass implements Serializable {

        public ChildClass() {}
    }
}