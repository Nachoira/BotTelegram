
package BotDeAlta.ServicioAltas.Servicio;

import BotDeAlta.ServicioAltas.Modelo.Oficio;
import BotDeAlta.ServicioAltas.Repositorio.OficioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteOficio(Long id){
        oficioRepositorio.deleteById(id);
    }

    public Oficio buscarPorId(Long id) {
        return oficioRepositorio.findById(id).orElse(null);
    }
    public Oficio updateOficio(Oficio oficio){
        return oficioRepositorio.save(oficio);
    }

}
