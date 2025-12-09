package BotDeAlta.ServicioAltas.Servicio;


import BotDeAlta.ServicioAltas.Modelo.Ciudad;
import BotDeAlta.ServicioAltas.Repositorio.CiudadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadServicio {

    @Autowired
    private CiudadRepositorio ciudadRepositorio;

    // Crear nueva ciudad
    public Ciudad crearCiudad(Ciudad ciudad) {
        return ciudadRepositorio.save(ciudad);
    }

    // Obtener todas
    public List<Ciudad> obtenerTodas() {
        return ciudadRepositorio.findAll();
    }

    // Buscar por ID
    public Ciudad buscarPorId(Long id) {
        return ciudadRepositorio.findById(id).orElse(null);
    }

    // Actualizar ciudad
    public Ciudad actualizarCiudad(Ciudad ciudad) {
        return ciudadRepositorio.save(ciudad);
    }

    // Eliminar ciudad
    public void eliminarCiudad(Long id) {
        ciudadRepositorio.deleteById(id);
    }
}


