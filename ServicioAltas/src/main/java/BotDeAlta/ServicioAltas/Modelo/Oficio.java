package BotDeAlta.ServicioAltas.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String telefono;

}