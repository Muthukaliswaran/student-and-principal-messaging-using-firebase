package com.prasanthkalis.gce_bodi.model;

public class Datasnapshot_Model {

    private String body;
    private String title;

    private Datasnapshot_Model() {}

    public Datasnapshot_Model(String body,String title) {
        this.body = body;
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }


}
