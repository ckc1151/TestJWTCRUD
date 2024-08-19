package com.test.spring.jwtoauth.dtos.responses;

import com.test.spring.jwtoauth.entities.Client;

import java.time.ZonedDateTime;

public class ClientDetailsResponse extends SuccessResponse {
    private final Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;

    public ClientDetailsResponse(Long id, String name, String email, String phoneNumber, String address, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ClientDetailsResponse(Client client, String message) {
        this(client.getId(), client.getName(), client.getEmail(), client.getPhoneNumber(), client.getAddress(), client.getCreatedAt(), client.getUpdatedAt());
        addFullMessage(message);
    }

    public ClientDetailsResponse(Client client) {
        this(client, null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
