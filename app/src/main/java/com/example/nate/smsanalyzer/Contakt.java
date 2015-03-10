package com.example.nate.smsanalyzer;

/**
 * Contact entity class
 * Created by Nate on 2015-02-16.
 */
public class Contakt {
    private int id;
    private String name;
    private String uriString;


    public Contakt(int id, String name, String uriString) {
        super();
        this.id = id;
        this.name = name;
        this.uriString = uriString;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUriString() {
        return uriString;
    }
}
