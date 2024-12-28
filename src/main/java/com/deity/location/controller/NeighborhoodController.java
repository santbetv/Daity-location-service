package com.deity.location.controller;

import com.deity.location.domain.mongo.Neighborhood;
import com.deity.location.service.NeighborhoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    public NeighborhoodController(NeighborhoodService neighborhoodService) {
        this.neighborhoodService = neighborhoodService;
    }

    @GetMapping("/neighborhoods")
    public ResponseEntity<Flux<Neighborhood>> getAllNeighborhoods() {
        return new ResponseEntity<>(neighborhoodService.getAllNeighborhoods(), HttpStatus.OK);
    }
}
