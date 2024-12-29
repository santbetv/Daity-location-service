package com.deity.location.service;

import com.deity.location.common.client.ClientWebClient;
import com.deity.location.domain.dto.DepartmentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartmentService {

    @Value("${url_api_colombia}")
    private String CLIENT_API_COLOMBIA;

    private final ClientWebClient clientWebClient;

    public DepartmentService(ClientWebClient clientWebClient) {
        this.clientWebClient = clientWebClient;
    }

    public Flux<DepartmentDto> getAllDepartments() {
        return clientWebClient.getExtraccionData(CLIENT_API_COLOMBIA, DepartmentDto.class)
                .doOnSubscribe(s -> System.out.println("Obteniendo datos...Suscritos"))//Se ejecuta cuando se suscribe un observador
                //.doOnNext(e -> System.out.println("Datos: " + e))//Se ejecuta cuando se recibe un elemento
                .doOnComplete(() -> System.out.println("Datos obtenidos correctamente...FIN"))//Se ejecuta cuando se completa la secuencia
                .doOnError(e -> System.out.println("Error al obtener Datos: " + e.getMessage()));//Se ejecuta cuando se produce
    }

    @Transactional(readOnly = true)
    public Mono<DepartmentDto> getDepartmentById(Long id) {
        return clientWebClient.getExtraccionData(CLIENT_API_COLOMBIA, DepartmentDto.class)
                .filter(e -> e.getId().equals(id))
                .singleOrEmpty()
                .doOnError(e -> System.out.println("Error al obtener departamento por ID: " + e.getMessage()));
    }

}
