package com.test.spring.jwtoauth.controller;

import com.test.spring.jwtoauth.dtos.responses.ClientDetailsResponse;
import com.test.spring.jwtoauth.dtos.responses.ClientListResponse;
import com.test.spring.jwtoauth.dtos.responses.ErrorResponse;
import com.test.spring.jwtoauth.entities.Client;
import com.test.spring.jwtoauth.repositories.ClientRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "Clients", description = "Operations related to clients")
@RestController
@RequestMapping("/api/clients")
public class ClientsController {

    @Autowired
    private ClientRepository clientRepository;

    @ApiOperation(value = "Get all clients", notes = "Retrieve a list of all clients")
    @GetMapping
    public ResponseEntity<ClientListResponse> index() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDetailsResponse> clientResponses = clients.stream()
                .map(ClientDetailsResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ClientListResponse(clientResponses));
    }

    @ApiOperation(value = "Get a client by ID", notes = "Retrieve a specific client by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved client"),
            @ApiResponse(code = 404, message = "Client not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> get(
            @ApiParam(value = "ID of the client to be retrieved", required = true)
            @PathVariable("id") Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(new ClientDetailsResponse(client.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Client not found"));
        }
    }

    @ApiOperation(value = "Create a new client", notes = "Add a new client to the system")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Client created successfully")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER') and #oauth2.hasScope('write')")
    public ResponseEntity<?> create(
            @ApiParam(value = "Client details to be created", required = true)
            @Valid @RequestBody Client client) {
        Client createdClient = clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ClientDetailsResponse(createdClient));
    }

    @ApiOperation(value = "Update an existing client", notes = "Update the details of an existing client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client updated successfully"),
            @ApiResponse(code = 404, message = "Client not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and #oauth2.hasScope('write')")
    public ResponseEntity<?> update(
            @ApiParam(value = "ID of the client to be updated", required = true)
            @PathVariable("id") Long id,
            @ApiParam(value = "Updated client details", required = true)
            @Valid @RequestBody Client clientInput) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setName(clientInput.getName());
            client.setEmail(clientInput.getEmail());
            client.setPhoneNumber(clientInput.getPhoneNumber());
            client.setAddress(clientInput.getAddress());
            Client updatedClient = clientRepository.save(client);
            return ResponseEntity.ok(new ClientDetailsResponse(updatedClient));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Client does not exist"));
        }
    }

    @ApiOperation(value = "Delete a client", notes = "Remove a client from the system by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Client deleted successfully"),
            @ApiResponse(code = 404, message = "Client not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and #oauth2.hasScope('write')")
    public ResponseEntity<?> delete(
            @ApiParam(value = "ID of the client to be deleted", required = true)
            @PathVariable("id") Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            clientRepository.delete(client.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Client does not exist"));
        }
    }

    @ApiOperation(value = "Delete all clients", notes = "Remove all clients from the system")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "All clients deleted successfully")
    })
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> deleteAll() {
        clientRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
