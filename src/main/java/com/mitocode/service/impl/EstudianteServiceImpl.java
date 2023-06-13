package com.mitocode.service.impl;

import com.mitocode.model.Estudiante;
import com.mitocode.repo.IEstudianteRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IEstudianteService;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends CRUDImpl<Estudiante, String> implements IEstudianteService {

    private final IEstudianteRepo repo;

    @Override
    protected IGenericRepo<Estudiante, String> getRepo() {
        return repo;
    }

    @Override
    public Flux<Estudiante> findAllOrderedByEdadAsc() {
        Sort sort = Sort.by(Sort.Direction.ASC, "edad");
        return repo.findAll(sort);
    }

    @Override
    public Flux<Estudiante> findAllOrderedByEdadDesc() {
        Sort sort = Sort.by(Sort.Direction.DESC, "edad");
        return repo.findAll(sort);
    }
}
