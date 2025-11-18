package BotDeAlta.ServicioAltas.Servicio;

import BotDeAlta.ServicioAltas.Modelo.Oficio;

import java.util.List;

public interface servicioOficio {
    List<Oficio> getallOficio();
    Oficio crearOficio(Oficio oficio);
    Oficio udpateOficio(Oficio oficio);
    Oficio buscarPorId(long id);
    void deleteOficio(Long id);
}
