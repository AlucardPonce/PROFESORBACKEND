package mx.edu.uteq.idgs09.idgs09_01.model.entity;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data

public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellidos;
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "division")
    // @JoinColumn(name = "id_division")
    private List<ProgramaEducativo> programasEducativos;
    
}
