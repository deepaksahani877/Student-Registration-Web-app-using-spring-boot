package com.st.studentregistration.models.users;

public class Admin {
    private String name;
    private String city;
    private String email;
    private String phone;

    public Admin(String name, String city, String email, String phone) {
        this.name = name;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}

