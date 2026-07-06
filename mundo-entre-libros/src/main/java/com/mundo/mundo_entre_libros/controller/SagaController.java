package com.mundo.mundo_entre_libros.controller;

import com.mundo.mundo_entre_libros.model.Saga;
import com.mundo.mundo_entre_libros.service.SagaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sagas")
public class SagaController {

    private final SagaService service;

    public SagaController(SagaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Saga> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Saga getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Saga save(@RequestBody Saga saga) {
        return service.save(saga);
    }

    @PutMapping("/{id}")
    public Saga update(@PathVariable Integer id,
                       @RequestBody Saga saga) {
        return service.update(id, saga);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}