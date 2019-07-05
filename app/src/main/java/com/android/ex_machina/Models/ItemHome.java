package com.android.ex_machina.Models;

import android.widget.ImageView;

public class ItemHome {

    private String Title;
    private String Description;
    private int Background;
    private int Profile;

    public ItemHome(String title, String description, int profile, int background) {
        Title = title;
        Description = description;
        Background = background;
        Profile = profile;
    }

    public int getBackground() {
        return Background;
    }

    public void setBackground(int background) {
        Background = background;
    }

    public int getProfile() {
        return Profile;
    }

    public void setProfile(int profile) {
        Profile = profile;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
