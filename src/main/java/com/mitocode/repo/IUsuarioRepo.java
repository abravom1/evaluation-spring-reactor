package com.mitocode.repo;

import com.mitocode.model.User;
import reactor.core.publisher.Mono;

public interface IUsuarioRepo extends IGenericRepo<User, String>{

    Mono<User> findOneByUsername(String username);
}
