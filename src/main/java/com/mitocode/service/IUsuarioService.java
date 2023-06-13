package com.mitocode.service;

import com.mitocode.model.User;
import reactor.core.publisher.Mono;

public interface IUsuarioService extends ICRUD<User, String>{

    Mono<com.mitocode.security.User> searchByUser(String username);
}
