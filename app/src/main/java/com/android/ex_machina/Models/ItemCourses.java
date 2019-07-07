package com.android.ex_machina.Models;

public class ItemCourses {

    private String fid;
    private String titre;
    private int background;

    public ItemCourses(String fid, String titre, int background) {
        this.fid = fid;
        this.titre = titre;
        this.background = background;
    }

    public ItemCourses(){

    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
