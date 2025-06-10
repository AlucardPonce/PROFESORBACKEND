package mx.edu.uteq.idgs09.idgs09_01.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.uteq.idgs09.idgs09_01.model.entity.Profesor;
import mx.edu.uteq.idgs09.idgs09_01.service.ProfesorService;

@RestController
@RequestMapping("/api/profesores")
@CrossOrigin(origins = "*")
public class ProfesorController {

    private final ProfesorService service;

    @Autowired
    public ProfesorController(ProfesorService service) {
        this.service = service;
    }

    // ✅ GET: Obtener todos los profesores
    @GetMapping
    public List<Profesor> getAllProfesores() {
        return service.findAll();
    }

    // ✅ POST: Crear un nuevo profesor
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Profesor p) {
        try {
            return ResponseEntity.ok(service.crear(p));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ GET: Obtener un profesor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable int id) {
        Optional<Profesor> profesor = service.findById(id);
        return profesor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ PUT: Editar un profesor
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable int id, @RequestBody Profesor p) {
        try {
            return ResponseEntity.ok(service.actualizar(id, p));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ DELETE: Eliminar un profesor
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesor(@PathVariable int id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
