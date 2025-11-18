package BotDeAlta.ServicioAltas.Controlador;


import BotDeAlta.ServicioAltas.Modelo.Oficio;
import BotDeAlta.ServicioAltas.Servicio.OficioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oficios")

public class OficioControlador {

    private final OficioServicio oficioServicio;

    //  Constructor con inyección de dependencias
    @Autowired
    public OficioControlador(OficioServicio oficioServicio) {
        this.oficioServicio = oficioServicio;
    }

    //  Crear un oficio (POST)
    @PostMapping
    public Oficio crearOficio(@RequestBody Oficio oficio) {
        return oficioServicio.crearOficio(oficio);
    }

    @GetMapping
    public List<Oficio> oficios(){
        return oficioServicio.obtenerTodos();
    }
}




