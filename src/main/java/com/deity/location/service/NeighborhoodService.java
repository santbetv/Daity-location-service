package com.deity.location.service;

import com.deity.location.common.exceptions.CustomNotFoundException;
import com.deity.location.domain.mongo.Neighborhood;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class NeighborhoodService {


    @Qualifier("primaryMongoTemplate")
    private final ReactiveMongoTemplate primaryMongoTemplate;

    public NeighborhoodService(ReactiveMongoTemplate primaryMongoTemplate) {
        this.primaryMongoTemplate = primaryMongoTemplate;
    }

    @Transactional(readOnly = true)
    public Flux<Neighborhood> getAllNeighborhood() {

        return this.primaryMongoTemplate.findAll(Neighborhood.class)
                .doOnSubscribe(s -> log.info("Obteniendo datos...Suscritos"))//Se ejecuta cuando se suscribe un observador
                //.doOnNext(e -> System.out.println("Datos: " + e))//Se ejecuta cuando se recibe un elemento
                .doOnComplete(() -> log.info("Datos obtenidos correctamente...FIN"))//Se ejecuta cuando se completa la secuencia
                .doOnError(e -> log.error("Error al obtener Datos: " + e.getMessage()));//Se ejecuta cuando se produce
    }

    @Transactional(readOnly = true)
    public Mono<Neighborhood> getNeighborhoodById(Long id) {
        return this.primaryMongoTemplate
                .findOne(Query.query(Criteria.where("fid").is(id)), Neighborhood.class)
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Barrio no encontrada con ID: " + id)))
                .doOnError(e -> log.error("Error al obtener empleado por ID: " + e.getMessage()));
    }
}
