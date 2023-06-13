package com.mitocode.handler;

import com.mitocode.model.Matricula;
import com.mitocode.model.Matricula;
import com.mitocode.service.IMatriculaService;
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
public class MatriculaHandler {

    private final IMatriculaService service;

    public Mono<ServerResponse> create(ServerRequest req){
        Mono<Matricula> monoMatricula = req.bodyToMono(Matricula.class);

        return monoMatricula
                .flatMap(service::save)
                .flatMap(Matricula -> ServerResponse
                        .created(URI.create(req.uri().toString().concat("/").concat(Matricula.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(Matricula))
                );
    }
}
