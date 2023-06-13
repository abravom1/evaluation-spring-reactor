package com.mitocode.handler;

import com.mitocode.model.Estudiante;
import com.mitocode.service.IEstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class EstudianteHandler {

    private final IEstudianteService service;

    public Mono<ServerResponse> findAll(ServerRequest req){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), Estudiante.class);
    }

    public Mono<ServerResponse> findAllOrderedByEdadAsc(ServerRequest req){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAllOrderedByEdadAsc(), Estudiante.class);
    }

    public Mono<ServerResponse> findAllOrderedByEdadDesc(ServerRequest req){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAllOrderedByEdadDesc(), Estudiante.class);
    }


    public Mono<ServerResponse> findById(ServerRequest req){
        String id = req.pathVariable("id");

        return service.findById(id)
                .flatMap(Estudiante -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(Estudiante))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest req){
        Mono<Estudiante> monoEstudiante = req.bodyToMono(Estudiante.class);

        return monoEstudiante
                .flatMap(service::save)
                .flatMap(Estudiante -> ServerResponse
                        .created(URI.create(req.uri().toString().concat("/").concat(Estudiante.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(Estudiante))
                );
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        String id = req.pathVariable("id");

        Mono<Estudiante> monoEstudiante = req.bodyToMono(Estudiante.class);
        Mono<Estudiante> monoDB = service.findById(id);

        return monoDB
                .zipWith(monoEstudiante, (db, cl)-> {
                    db.setId(id);
                    db.setApellidos(cl.getApellidos());
                    db.setNombres(cl.getNombres());
                    db.setDni(cl.getDni());
                    db.setEdad(cl.getEdad());
                    return db;
                })
                .flatMap(service::update)
                .flatMap(Estudiante -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(Estudiante))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req){
        String id = req.pathVariable("id");

        return service.findById(id)
                .flatMap(Estudiante -> service.delete(Estudiante.getId())
                        .then(ServerResponse.noContent().build())
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
