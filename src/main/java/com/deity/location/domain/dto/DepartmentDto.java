package com.deity.location.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private long id;
    private String name;
    private String description;
    private long cityCapitalID;
    private long municipalities;
    private long surface;
    private long population;
    private String phonePrefix;
    private long countryID;
    private CityCapitalDto cityCapitalDto;
    private Object country;
    private Object cities;
    private long regionID;
    private Object region;
    private Object naturalAreas;
    private Object maps;
    private Object indigenousReservations;
    private Object airports;
}
