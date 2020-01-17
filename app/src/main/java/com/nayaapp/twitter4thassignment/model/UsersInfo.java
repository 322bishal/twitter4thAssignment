package com.nayaapp.twitter4thassignment.model;

public class UsersInfo {
    String email;
    String _id;
    String username;
    String image;

    public UsersInfo(String email, String _id, String username, String image) {
        this.email = email;
        this._id = _id;
        this.username = username;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public String get_id() {
        return _id;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }
}
