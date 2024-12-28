package com.deity.location.controller;

import com.deity.location.domain.mongo.CityCapital;
import com.deity.location.domain.mongo.Neighborhood;
import com.deity.location.service.CitysService;
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
    private final CitysService citysService;

    public NeighborhoodController(NeighborhoodService neighborhoodService, CitysService citysService) {
        this.neighborhoodService = neighborhoodService;
        this.citysService = citysService;
    }

    @GetMapping("/neighborhoods")
    public ResponseEntity<Flux<Neighborhood>> getAllNeighborhoods() {
        return new ResponseEntity<>(neighborhoodService.getAllCitys(), HttpStatus.OK);
    }

    @GetMapping("/citys")
    public ResponseEntity<Flux<CityCapital>> getAllCitysService() {
        return new ResponseEntity<>(citysService.getAllCitys(), HttpStatus.OK);
    }
}
