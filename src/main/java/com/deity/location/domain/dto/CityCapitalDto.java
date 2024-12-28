package com.deity.location.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityCapitalDto {

    private long id;
    private String name;
    private String description;
    private long surface;
    private long population;
    private String postalCode;
    private long departmentID;
    private Object department;
    private Object touristAttractions;
    private Object presidents;
    private Object indigenousReservations;
    private Object airports;
    private Object radios;

}
