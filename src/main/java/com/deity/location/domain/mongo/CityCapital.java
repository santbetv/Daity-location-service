package com.deity.location.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "citys")
public class CityCapital {


    @Id
    private String id;
    private String name;
    private String description;
    private Long surface;
    private Long population;
    private String postalCode;
    private Long departmentId;
    private String department;
    private String touristAttractions;
    private String presidents;
    private String indigenousReservations;
    private String airports;
    private String radios;

}
