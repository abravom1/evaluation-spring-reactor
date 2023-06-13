package com.mitocode.handler;

import com.mitocode.model.Curso;
import com.mitocode.service.ICursoService;
import jakarta.validation.Valid;
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
public class CursoHandler {

    private final ICursoService service;

    public Mono<ServerResponse> findAll(ServerRequest req){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), Curso.class);
    }

    public Mono<ServerResponse> findById(ServerRequest req){
        String id = req.pathVariable("id");

        return service.findById(id)
                .flatMap(Curso -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(Curso))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(@Valid ServerRequest req){
        Mono<Curso> monoCurso = req.bodyToMono(Curso.class);

        return monoCurso
                .flatMap(service::save)
                .flatMap(Curso -> ServerResponse
                        .created(URI.create(req.uri().toString().concat("/").concat(Curso.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(Curso))
                );
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        String id = req.pathVariable("id");

        Mono<Curso> monoCurso = req.bodyToMono(Curso.class);
        Mono<Curso> monoDB = service.findById(id);

        return monoDB
                .zipWith(monoCurso, (db, cl)-> {
                    db.setId(id);
                    db.setNombre(cl.getNombre());
                    db.setSiglas(cl.getSiglas());
                    db.setEstado(cl.getEstado());
                    return db;
                })
                .flatMap(service::update)
                .flatMap(Curso -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(Curso))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req){
        String id = req.pathVariable("id");

        return service.findById(id)
                .flatMap(Curso -> service.delete(Curso.getId())
                        .then(ServerResponse.noContent().build())
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
