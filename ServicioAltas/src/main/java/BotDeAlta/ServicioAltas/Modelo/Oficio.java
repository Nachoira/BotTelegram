package BotDeAlta.ServicioAltas.Modelo;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="oficios")
public class Oficio {
    @Id
    @GeneratedValue
    private int id;
    private String  nombre;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name="Ciudad_id")
    private Ciudad ciudad;
    @ManyToOne
    private Trabajador trabajador;
}
