package BotDeAlta.ServicioAltas.Repositorio;

import BotDeAlta.ServicioAltas.Modelo.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabajadorRepositorio extends JpaRepository<Trabajador, Long> {
}
