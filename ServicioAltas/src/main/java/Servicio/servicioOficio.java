package Servicio;

import Modelo.Oficio;

import java.util.List;

public interface servicioOficio {
    List<Oficio> getallOficio();
    Oficio crearOficio(Oficio oficio);
    Oficio udpateOficio(Oficio oficio);
}
