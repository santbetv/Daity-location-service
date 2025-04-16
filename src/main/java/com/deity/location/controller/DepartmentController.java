package com.deity.location.controller;

import com.deity.location.domain.dto.DepartmentDto;
import com.deity.location.service.DepartmentService;
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
@Tag(name = "Location")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public ResponseEntity<Flux<DepartmentDto>> getAllDepartments() {
        return new ResponseEntity<>(this.departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Mono<DepartmentDto>> getAllDepartmentsById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.departmentService.getDepartmentById(id), HttpStatus.OK);
    }
}
