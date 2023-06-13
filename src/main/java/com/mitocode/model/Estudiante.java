package com.mitocode.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "estudiantes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Estudiante {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    @NotEmpty
    private String nombres;

    @Field
    @NotEmpty
    private String apellidos;

    @Field
    @NotEmpty
    private String dni;

    @Field
    private Integer edad;


}
