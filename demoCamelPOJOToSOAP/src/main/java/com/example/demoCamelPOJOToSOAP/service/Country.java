package com.example.demoCamelPOJOToSOAP.service;

public class Country {
    private String name;
    private String capital;
    private String president;
    private Boolean haveNukes;

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public Boolean getHaveNukes() {
        return haveNukes;
    }

    public void setHaveNukes(Boolean haveNukes) {
        this.haveNukes = haveNukes;
    }
}
