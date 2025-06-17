//Interfaz
package mx.edu.uteq.idgs09.idgs09_01.service;

import java.util.List;
import java.util.Optional;

import mx.edu.uteq.idgs09.idgs09_01.dto.ProfesorDTO;
import mx.edu.uteq.idgs09.idgs09_01.dto.ProgramaEducativoDTO;
import mx.edu.uteq.idgs09.idgs09_01.model.entity.Profesor;

public interface ProfesorService {

    List<Profesor> findAll();

    Optional<Profesor> findById(int id);

    Optional<ProfesorDTO> findDTOById(int id);

    Profesor crear(Profesor profesor);

    Profesor actualizar(int id, Profesor profesor);

    void deleteById(int id);

    ProgramaEducativoDTO getProgramaEducativoDTO(String clavePE);
}
