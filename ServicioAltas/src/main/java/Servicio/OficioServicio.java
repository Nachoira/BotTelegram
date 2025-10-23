
package Servicio;

import Modelo.Oficio;
import Repositorio.OficioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OficioServicio {

    @Autowired
    private OficioRepositorio oficioRepositorio;

    // Crear un oficio
    public Oficio crearOficio(Oficio oficio) {
        return oficioRepositorio.save(oficio);
    }

    // Obtener todos los oficios
    public List<Oficio> obtenerTodos() {
        return oficioRepositorio.findAll();
    }
}