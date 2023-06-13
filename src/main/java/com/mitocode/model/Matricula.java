package com.mitocode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "matriculas")
public class Matricula {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private LocalDate fecha;

    @Field
    private Estudiante estudiante;

    @Field
    private List<MatriculaDetalle> detalle;

    @Field
    private Boolean estado;



}
