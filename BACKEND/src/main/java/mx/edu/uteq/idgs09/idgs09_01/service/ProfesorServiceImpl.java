package mx.edu.uteq.idgs09.idgs09_01.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.edu.uteq.idgs09.idgs09_01.model.entity.Profesor;
import mx.edu.uteq.idgs09.idgs09_01.model.repository.ProfesorRepository;
import mx.edu.uteq.idgs09.idgs09_01.dto.ProfesorDTO;
import mx.edu.uteq.idgs09.idgs09_01.dto.ProgramaEducativoDTO;

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
            throw new IllegalArgumentException("El profesor no existe.");
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

    @Override
    public Optional<ProfesorDTO> findDTOById(int id) {
        // Implementación básica: busca el profesor y convierte a DTO si existe
        return profesorRepository.findById(id)
            .map(profesor -> {
                ProfesorDTO dto = new ProfesorDTO();
                // Asigna los campos necesarios del profesor al DTO
                dto.setId(profesor.getId());
                dto.setNombre(profesor.getNombre());
                dto.setApellidos(profesor.getApellidos());
                dto.setGenero(profesor.getGenero());
                dto.setId_pe(profesor.getId_pe());
                dto.setActivo(profesor.isActivo());
                return dto;
            });
    }

    @Override
    public ProgramaEducativoDTO getProgramaEducativoDTO(String clavePE) {
        return programaEducativoClient.getProgramaEducativoByClave(clavePE);
    }
}
