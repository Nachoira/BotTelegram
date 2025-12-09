package BotDeAlta.ServicioAltas.Repositorio;

import BotDeAlta.ServicioAltas.Modelo.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepositorio extends JpaRepository<Ciudad, Long> {
}
