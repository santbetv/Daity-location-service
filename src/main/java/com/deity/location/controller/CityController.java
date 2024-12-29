package com.deity.location.controller;

import com.deity.location.domain.mongo.CityCapital;
import com.deity.location.service.CitysService;
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
@Tag(name = "City - Ciudades", description = "operations for citys")
public class CityController {
    private final CitysService citysService;


    public CityController(CitysService citysService) {
        this.citysService = citysService;
    }

    @GetMapping("/citys")
    public ResponseEntity<Flux<CityCapital>> getAllCitys() {
        return new ResponseEntity<>(this.citysService.getAllCitys(), HttpStatus.OK);
    }

    @GetMapping("/citys/{id}")
    public ResponseEntity<Mono<CityCapital>> getAllCitysById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.citysService.getCityById(id), HttpStatus.OK);
    }

    @GetMapping("/citys/department/{id}")
    public ResponseEntity<Flux<CityCapital>> getAllCitysByDepartmentId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.citysService.getCityByDepartmentId(id), HttpStatus.OK);
    }
}
