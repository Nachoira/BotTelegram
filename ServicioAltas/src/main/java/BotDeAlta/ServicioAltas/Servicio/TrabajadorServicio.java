package BotDeAlta.ServicioAltas.Servicio;
import BotDeAlta.ServicioAltas.Modelo.Trabajador;
import BotDeAlta.ServicioAltas.Repositorio.TrabajadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrabajadorServicio {

    @Autowired
    private TrabajadorRepositorio trabajadorRepositorio;

    // Crear trabajador
    public Trabajador crearTrabajador(Trabajador trabajador) {
        return trabajadorRepositorio.save(trabajador);
    }

    // Obtener todos
    public List<Trabajador> obtenerTodos() {
        return trabajadorRepositorio.findAll();
    }

    // Buscar por ID
    public Trabajador buscarPorId(Long id) {
        return trabajadorRepositorio.findById(id).orElse(null);
    }

    // Actualizar
    public Trabajador actualizarTrabajador(Trabajador trabajador) {
        return trabajadorRepositorio.save(trabajador);
    }

    // Eliminar
    public void eliminarTrabajador(Long id) {
        trabajadorRepositorio.deleteById(id);
    }
}
