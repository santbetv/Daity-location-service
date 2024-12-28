package com.deity.location.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "neighborhood_manizales")
public class Neighborhood {

    @Id
    private String id; // MongoDB utiliza cadenas para los IDs

    @Field("FID")
    private long fid;

    @Field("OBJECTID_1")
    private long objectid1;

    @Field("BARRIO")
    private String barrio;

    @Field("X")
    private double x;

    @Field("Y")
    private double y;

    @Field("NOMBRE_JUN")
    private String nombreJun;

    @Field("nro")
    private long nro;

    @Field("COMUNA")
    private String comuna;

    @Field("Shape_Leng")
    private double shapeLeng;

    @Field("Shape_Area")
    private double shapeArea;

    @Field("Shape__Area")
    private double neighborhoodShapeArea;

    @Field("Shape__Length")
    private double shapeLength;

    @Field("city")
    private long city;
}
