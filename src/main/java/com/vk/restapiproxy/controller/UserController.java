package com.vk.restapiproxy.controller;

import com.vk.restapiproxy.database.service.AuditService;
import com.vk.restapiproxy.dto.request.AddUserRequest;
import com.vk.restapiproxy.dto.request.UpdateUserRequest;
import com.vk.restapiproxy.dto.response.client.UserResponse;
import com.vk.restapiproxy.database.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuditService auditService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") @NonNull Long id, HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return ResponseEntity.ok(userService.find(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(name = "id", required = false) Long id,
                                                       HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        if (id == null) {
            return ResponseEntity.ok(userService.findAll());
        }
        return ResponseEntity.ok(List.of(userService.find(id)));
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> addUser(@RequestBody @NotNull AddUserRequest addUserRequest,
                                                HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return new ResponseEntity<>(userService.add(addUserRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> updateUser(@RequestBody @NotNull UpdateUserRequest updateUserRequest,
                                                   @PathVariable("id") Long id, HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return ResponseEntity.ok(userService.update(updateUserRequest, id));
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") @NotNull Long id,
                                           HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
