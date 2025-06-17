package mx.edu.uteq.idgs09.idgs09_01.dto;

import lombok.Data;

@Data
public class ProgramaEducativoDTO {
    private int id_pe;
    private String programaEducativo;
    private String clave;
    private boolean activo;
}