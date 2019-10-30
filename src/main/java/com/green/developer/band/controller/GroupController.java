package com.green.developer.band.controller;

import com.green.developer.band.model.Group;
import com.green.developer.band.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupController {

    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    private GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/v1/groups")
    Collection<Group> groups() {
        return groupRepository.findAll();
    }

    @GetMapping("/v1/group/{id}")
    ResponseEntity<?> getGenre(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/v1/group")
    ResponseEntity<Group> createGenre(@Valid @RequestBody Group group) throws URISyntaxException {
        log.info("Request to update list of genres: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.created(new URI("/api/genre" + result.getId()))
                .body(result);
    }

    @PutMapping("/v1/group")
    ResponseEntity<Group> updateGenre(@Valid @RequestBody Group group) {
        log.info("Request to update group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/v1/group/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id) {
        log.info("Request to delete genre: {}", id);
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
