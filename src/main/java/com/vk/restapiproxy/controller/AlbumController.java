package com.vk.restapiproxy.controller;

import com.vk.restapiproxy.database.service.AuditService;
import com.vk.restapiproxy.dto.request.AddAlbumRequest;
import com.vk.restapiproxy.dto.request.UpdateAlbumRequest;
import com.vk.restapiproxy.dto.response.client.AlbumResponse;
import com.vk.restapiproxy.database.service.AlbumService;
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
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumController {
    
    private final AlbumService albumService;

    private final AuditService auditService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AlbumResponse> getAlbum(@PathVariable("id") @NonNull Long id,
                                                  HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return ResponseEntity.ok(albumService.find(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AlbumResponse>> getAlbums(@RequestParam(name = "id", required = false) Long id,
                                                         HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        if (id == null) {
            return ResponseEntity.ok(albumService.findAll());
        }
        return ResponseEntity.ok(List.of(albumService.find(id)));
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AlbumResponse> addAlbums(@RequestBody @NotNull AddAlbumRequest addAlbumRequest,
                                                   HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return new ResponseEntity<>(albumService.add(addAlbumRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AlbumResponse> updateAlbum(@RequestBody @NotNull UpdateAlbumRequest updateAlbumRequest,
                                                     @PathVariable("id") Long id, HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        return ResponseEntity.ok(albumService.update(updateAlbumRequest, id));
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteAlbum(@PathVariable("id") @NotNull Long id,
                                            HttpServletRequest request) {
        auditService.save(auditService.create(request, true));
        albumService.delete(id);
        return ResponseEntity.ok().build();
    }
}
