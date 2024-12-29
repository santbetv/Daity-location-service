package com.deity.location.service;

import com.deity.location.domain.mongo.CityCapital;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CitysService {

    @Qualifier("primaryMongoTemplate")
    private final ReactiveMongoTemplate primaryMongoTemplate;

    public CitysService(ReactiveMongoTemplate primaryMongoTemplate) {
        this.primaryMongoTemplate = primaryMongoTemplate;
    }

    @Transactional(readOnly = true)
    public Flux<CityCapital> getAllCitys() {

        return this.primaryMongoTemplate.findAll(CityCapital.class)
                .doOnSubscribe(s -> System.out.println("Obteniendo datos...Suscritos"))//Se ejecuta cuando se suscribe un observador
                //.doOnNext(e -> System.out.println("Datos: " + e))//Se ejecuta cuando se recibe un elemento
                .doOnComplete(() -> System.out.println("Datos obtenidos correctamente...FIN"))//Se ejecuta cuando se completa la secuencia
                .doOnError(e -> System.out.println("Error al obtener Datos: " + e.getMessage()));//Se ejecuta cuando se produce
    }

    @Transactional(readOnly = true)
    public Mono<CityCapital> getCityById(Long id) {
        return this.primaryMongoTemplate
                .findOne(Query.query(Criteria.where("idCity").is(id)), CityCapital.class)
                .doOnError(e -> System.out.println("Error al obtener empleado por ID: " + e.getMessage()));
    }

    @Transactional(readOnly = true)
    public Flux<CityCapital> getCityByDepartmentId(Long id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("departmentId").is(id));

        return this.primaryMongoTemplate
                .find(query, CityCapital.class) // Filtra según el criterio definido
                .doOnError(e -> System.out.println("Error al obtener ciudades por ID de departamento: " + e.getMessage()));
    }
}
