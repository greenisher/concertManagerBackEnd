package com.green.developer.band;

import com.green.developer.band.model.Concert;
import com.green.developer.band.model.Group;
import com.green.developer.band.repository.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final GroupRepository repository;

    public Initializer(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Emmy the Great", "Taylor Swift", "Katy Perry", "Los Campesinos!").forEach(name -> repository.save(new Group(name)));
        Group egreat = repository.findByName("Emmy the Great");
        Concert c = Concert.builder().location("Union Chapel").date(Instant.parse("2019-06-07T19:00:00.000z")).build();
        egreat.setConcerts(Collections.singleton(c));
        egreat.setGenre("Anti Folk");
        repository.save(egreat);

        repository.findAll().forEach(System.out::println);
    }
}
