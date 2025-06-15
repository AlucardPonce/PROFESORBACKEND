package mx.edu.uteq.idgs09.idgs09_01.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.edu.uteq.idgs09.idgs09_01.model.entity.Profesor;
import mx.edu.uteq.idgs09.idgs09_01.model.repository.ProfesorRepository;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ProgramaEducativoClient programaEducativoClient;

    @Override
    public List<Profesor> findAll() {
        return profesorRepository.findAll();
    }

    @Override
    public Optional<Profesor> findById(int id) {
        return profesorRepository.findById(id);
    }

    @Override
    public Profesor crear(Profesor profesor) {
        // Validar que la clave del programa educativo exista
        if (!programaEducativoClient.existsByClave(profesor.getId_pe())) {
            throw new IllegalArgumentException("La clave del programa educativo no existe.");
        }
        return profesorRepository.save(profesor);
    }

    @Override
    public Profesor actualizar(int id, Profesor profesor) {
        if (!profesorRepository.existsById(id)) {
            throw new NoSuchElementException("No existe el profesor con ID: " + id);
        }
        // Validar que la clave del programa educativo exista
        if (!programaEducativoClient.existsByClave(profesor.getId_pe())) {
            throw new IllegalArgumentException("La clave del programa educativo no existe.");
        }
        profesor.setId(id); // Asegurar que se está actualizando el registro correcto
        return profesorRepository.save(profesor);
    }

    @Override
    public void deleteById(int id) {
        if (!profesorRepository.existsById(id)) {
            throw new NoSuchElementException("No existe el profesor con ID: " + id);
        }
        profesorRepository.deleteById(id);
    }
}
