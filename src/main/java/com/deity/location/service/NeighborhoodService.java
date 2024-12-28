package com.deity.location.service;

import com.deity.location.domain.mongo.Neighborhood;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
public class NeighborhoodService {


    @Qualifier("primaryMongoTemplate")
    private final ReactiveMongoTemplate primaryMongoTemplate;

    public NeighborhoodService(ReactiveMongoTemplate primaryMongoTemplate) {
        this.primaryMongoTemplate = primaryMongoTemplate;
    }

    @Transactional(readOnly = true)
    public Flux<Neighborhood> getAllNeighborhoods() {

        return primaryMongoTemplate.findAll(Neighborhood.class)
                .doOnSubscribe(s -> System.out.println("Obteniendo datos...Suscritos"))//Se ejecuta cuando se suscribe un observador
                .doOnNext(e -> System.out.println("Datos: " + e))//Se ejecuta cuando se recibe un elemento
                .doOnComplete(() -> System.out.println("Datos obtenidos correctamente...FIN"))//Se ejecuta cuando se completa la secuencia
                .doOnError(e -> System.out.println("Error al obtener Datos: " + e.getMessage()));//Se ejecuta cuando se produce
    }
}
