package com.mitocode.repo;

import com.mitocode.model.Estudiante;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IEstudianteRepo extends IGenericRepo<Estudiante, String>{

}
