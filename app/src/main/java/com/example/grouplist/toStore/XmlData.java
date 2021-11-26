package com.example.grouplist.toStore;

public class XmlData {
    private String name;
    private String sex;
    private String ide;
    private String lang;


    public XmlData(String name, String sex, String ide, String lang)
    {
        this.name = name;
        this.sex = sex;
        this.ide = ide;
        this.lang = lang;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public void setIde(String ide)
    {
        this.ide = ide;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }

    public String getName()
    {
        return name;
    }

    public String getSex()
    {
        return sex;
    }

    public String getIde()
    {
        return ide;
    }

    public String getLang()
    {
        return lang;
    }
}
