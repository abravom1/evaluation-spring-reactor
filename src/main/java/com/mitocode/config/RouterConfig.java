package com.mitocode.config;

import com.mitocode.handler.CursoHandler;
import com.mitocode.handler.EstudianteHandler;
import com.mitocode.handler.MatriculaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    //Functional Endpoints
    @Bean
    public RouterFunction<ServerResponse> routesCurso(CursoHandler handler){
        return route(GET("/v2/cursos"), handler::findAll) //req -> handler.findAll(req)
                .andRoute(GET("/v2/cursos/{id}"), handler::findById)
                .andRoute(POST("/v2/cursos"), handler::create)
                .andRoute(PUT("/v2/cursos/{id}"), handler::update)
                .andRoute(DELETE("/v2/cursos/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesEstudiante(EstudianteHandler handler){
        return route(GET("/v2/estudiantes"), handler::findAll) //req -> handler.findAll(req)
                .andRoute(GET("/v2/estudiantes/byEdadAsc"), handler::findAllOrderedByEdadAsc)
                .andRoute(GET("/v2/estudiantes/byEdadDesc"), handler::findAllOrderedByEdadDesc)
                .andRoute(POST("/v2/estudiantes"), handler::create)
                .andRoute(PUT("/v2/estudiantes/{id}"), handler::update)
                .andRoute(DELETE("/v2/estudiantes/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesInvoice(MatriculaHandler handler){
        return route(POST("/v2/matriculas"), handler::create); //req -> handler.findAll(req)

    }
}
