package com.deity.location.controller;

import com.deity.location.domain.mongo.Neighborhood;
import com.deity.location.service.NeighborhoodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "Location", description = "operations for neighborhoods - citys - department")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    public NeighborhoodController(NeighborhoodService neighborhoodService) {
        this.neighborhoodService = neighborhoodService;
    }

    @GetMapping("/neighborhoods")
    public ResponseEntity<Flux<Neighborhood>> getAllNeighborhoods() {
        return new ResponseEntity<>(this.neighborhoodService.getAllNeighborhood(), HttpStatus.OK);
    }

    @GetMapping("/neighborhoods/{id}")
    public ResponseEntity<Mono<Neighborhood>> getNeighborhoodsById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.neighborhoodService.getNeighborhoodById(id), HttpStatus.OK);
    }
}
