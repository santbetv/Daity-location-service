package com.deity.location.domain.dto;

import com.deity.location.domain.mongo.CityCapital;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartmentDto {

    private Long id;
    private String name;
    private String description;
    private long cityCapitalID;
    private long municipalities;
    private long surface;
    private long population;
    private String phonePrefix;
    private long countryId;
    private CityCapital cityCapital;
    private String country;
    private String cities;
    private long regionId;
    private String region;
    private String naturalAreas;
    private String maps;
    private String indigenousReservations;
    private String airports;
}
