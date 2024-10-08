package com.test.spring.jwtoauth.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Lob;

@Entity
@Table(name = "clients")
public class Client extends TimestampedEntity {

    private String name;

    private String email;

    private String phoneNumber;

    // To avoid Caused by: org.h2.jdbc.JdbcSQLException: Value too long for column "ADDRESS VARCHAR(255)
    @Lob
    private String address;

    // Assuming other fields like createdAt, updatedAt are inherited from TimestampedEntity

    public Client() {
    }

    public Client(String name, String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Client(Long id, String name, String email, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
