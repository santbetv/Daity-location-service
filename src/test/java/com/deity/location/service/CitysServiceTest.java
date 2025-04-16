package com.deity.location.service;

import com.deity.location.common.exceptions.CustomNotFoundException;
import com.deity.location.domain.mongo.CityCapital;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitysServiceTest {

    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @InjectMocks
    private CitysService citysService;

    private CityCapital createCity(Long id, String name, Long departmentId) {
        return CityCapital.builder()
                .idCity(id)
                .name(name)
                .departmentId(departmentId)
                .build();
    }

    @Test
    void testGetAllCitys() {
        CityCapital city1 = createCity(1L, "Manizales", 10L);
        CityCapital city2 = createCity(2L, "Medellín", 20L);

        when(reactiveMongoTemplate.findAll(CityCapital.class))
                .thenReturn(Flux.just(city1, city2));

        Flux<CityCapital> result = citysService.getAllCitys();

        StepVerifier.create(result)
                .expectNext(city1, city2)
                .verifyComplete();
    }

    @Test
    void testGetCityById_found() {
        Long id = 1L;
        CityCapital city = createCity(id, "Bogotá", 30L);

        when(reactiveMongoTemplate.findOne(any(Query.class), any(Class.class)))
                .thenReturn(Mono.just(city));

        Mono<CityCapital> result = citysService.getCityById(id);

        StepVerifier.create(result)
                .expectNext(city)
                .verifyComplete();
    }

    @Test
    void testGetCityById_notFound() {
        Long id = 99L;

        when(reactiveMongoTemplate.findOne(any(Query.class), any(Class.class)))
                .thenReturn(Mono.empty());

        Mono<CityCapital> result = citysService.getCityById(id);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof CustomNotFoundException &&
                        throwable.getMessage().equals("Ciudad no encontrada con ID: " + id))
                .verify();
    }

    @Test
    void testGetCityByDepartmentId_found() {
        Long departmentId = 10L;
        CityCapital city1 = createCity(1L, "Manizales", departmentId);
        CityCapital city2 = createCity(2L, "Pereira", departmentId);

        when(reactiveMongoTemplate.find(any(Query.class), any(Class.class)))
                .thenReturn(Flux.just(city1, city2));

        Flux<CityCapital> result = citysService.getCityByDepartmentId(departmentId);

        StepVerifier.create(result)
                .expectNext(city1, city2)
                .verifyComplete();
    }

    @Test
    void testGetCityByDepartmentId_notFound() {
        Long departmentId = 99L;

        when(reactiveMongoTemplate.find(any(Query.class), any(Class.class)))
                .thenReturn(Flux.empty());

        Flux<CityCapital> result = citysService.getCityByDepartmentId(departmentId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof CustomNotFoundException &&
                        throwable.getMessage().equals("Ciudad no encontrada con ID de Department: " + departmentId))
                .verify();
    }
}