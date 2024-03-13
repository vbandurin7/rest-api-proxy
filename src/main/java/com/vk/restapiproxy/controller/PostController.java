package com.vk.restapiproxy.controller;

import com.vk.restapiproxy.database.service.AuditService;
import com.vk.restapiproxy.database.service.PostService;
import com.vk.restapiproxy.dto.request.AddPostRequest;
import com.vk.restapiproxy.dto.request.UpdatePostRequest;
import com.vk.restapiproxy.dto.response.client.PostResponse;
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
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final AuditService auditService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostResponse> getPost(@PathVariable("id") @NonNull Long id, HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return ResponseEntity.ok(postService.find(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostResponse>> getPosts(@RequestParam(name = "id", required = false) Long id,
                                                       HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        if (id == null) {
            return ResponseEntity.ok(postService.findAll());
        }
        return ResponseEntity.ok(List.of(postService.find(id)));
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostResponse> addPost(@RequestBody @NotNull AddPostRequest addPostRequest,
                                                HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return new ResponseEntity<>(postService.add(addPostRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostResponse> updatePost(@RequestBody @NotNull UpdatePostRequest updatePostRequest,
                                                   @PathVariable("id") Long id, HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return ResponseEntity.ok(postService.update(updatePostRequest, id));
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePost(@PathVariable("id") @NotNull Long id,
                                           HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}
