package com.example.amazon_server.models;

import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auth")
public class authData {
    private String name;
    private String email;
    private String password;
    private Collection<Role> roles;


    public authData() {
    }

    
    public authData(String name, String email, String password, Collection<Role> role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = role;
    }


    @Override
    public String toString() {
        return "authData [name=" + name + ", email=" + email + ", password=" + password + ", role=" + roles + "]";
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Collection<Role> getRoles() {
        return roles;
    }


    public void setRoles(Collection<Role> role) {
        this.roles = role;
    }

}
