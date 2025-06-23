package mx.edu.uteq.idgs09.idgs09_01.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.uteq.idgs09.idgs09_01.dto.ProgramaEducativoDTO;
import mx.edu.uteq.idgs09.idgs09_01.dto.ProfesorDTO;
import mx.edu.uteq.idgs09.idgs09_01.model.entity.Profesor;
import mx.edu.uteq.idgs09.idgs09_01.service.ProfesorService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


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

    // GET: Obtener un profesor por ID con información del programa educativo
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> getProfesorConPrograma(@PathVariable int id) {
        Optional<Profesor> profesorOpt = service.findById(id);
        if (profesorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Profesor profesor = profesorOpt.get();

        // Traer la información del programa educativo usando el cliente
        ProgramaEducativoDTO programa = null;
        if (profesor.getId_pe() != null) {
            programa = service.getProgramaEducativoDTO(profesor.getId_pe());
        }

        ProfesorDTO dto = new ProfesorDTO();
        dto.setId(profesor.getId());
        dto.setNombre(profesor.getNombre());
        dto.setApellidos(profesor.getApellidos());
        dto.setGenero(profesor.getGenero());
        dto.setId_pe(profesor.getId_pe());
        dto.setActivo(profesor.isActivo());
        dto.setProgramaEducativo(programa);

        return ResponseEntity.ok(dto);
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

    // PUT: Actualizar solo el id_pe (clave del programa educativo) de un profesor
    @PutMapping("/{id}/idpe")
    public ResponseEntity<?> actualizarIdPe(@PathVariable int id, @RequestBody Map<String, String> body) {
        String nuevoIdPe = body.get("idPe");
        Optional<Profesor> profesorOpt = service.findById(id);
        if (profesorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Profesor profesor = profesorOpt.get();
        profesor.setId_pe(nuevoIdPe);
        try {
            Profesor actualizado = service.actualizar(id, profesor);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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

    @PutMapping("/editarpro/{id}")
    public ResponseEntity<?> editarPro(@PathVariable int id, @RequestBody Profesor p) {
        try {
            return ResponseEntity.ok(service.actualizar(id, p));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
