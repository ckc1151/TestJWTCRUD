package com.test.spring.jwtoauth.dtos.responses;

import com.test.spring.jwtoauth.entities.Client;  // Adjust import according to your package

import java.time.ZonedDateTime;

public class ClientSummaryDto {
    private final String name;
    private final String email;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final Long id;

    public ClientSummaryDto(Long id, String name, String email, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getId() {
        return id;
    }

    public static ClientSummaryDto build(Client client) {
        return new ClientSummaryDto(client.getId(), client.getName(), client.getEmail(), client.getCreatedAt(), client.getUpdatedAt());
    }
}
