package BotDeAlta.ServicioAltas.Bot;

import BotDeAlta.ServicioAltas.Modelo.Ciudad;
import BotDeAlta.ServicioAltas.Modelo.Trabajador;
import BotDeAlta.ServicioAltas.Modelo.Oficio;
import BotDeAlta.ServicioAltas.Servicio.CiudadServicio;
import BotDeAlta.ServicioAltas.Servicio.TrabajadorServicio;
import BotDeAlta.ServicioAltas.Servicio.OficioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobBot extends TelegramLongPollingBot {

    @Autowired
    private OficioServicio oficioServicio;

    @Autowired
    private CiudadServicio ciudadServicio;

    @Autowired
    private TrabajadorServicio trabajadorServicio;


    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String mensaje = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        
        if (mensaje.startsWith("/alta")) {
            String[] partes = mensaje.replace("/alta", "").split("\\|");
            if (partes.length < 3) {
                sendMessage(chatId, "❌ Formato incorrecto. Usá: /alta Nombre | Descripción | Telefono");
                return;
            }

            String nombre = partes[0].trim();
            String descripcion = partes[1].trim();
            String telefono = partes[2].trim();

            Oficio oficio = new Oficio();
            oficio.setNombre(nombre);
            oficio.setDescripcion(descripcion);

            oficioServicio.crearOficio(oficio);
            sendMessage(chatId, "✅ Oficio guardado:\nNombre: " + nombre + "\nDescripción: " + descripcion + "\nTeléfono: " + telefono);

            return;
        }

        if (mensaje.startsWith("/eliminar")) {
            try {
                Long id = Long.parseLong(mensaje.replace("/eliminar", "").trim());
                oficioServicio.deleteOficio(id);
                sendMessage(chatId, "🗑 Oficio eliminado: " + id);
            } catch (Exception e) {
                sendMessage(chatId, "❌ Error. Usá: /eliminar ID");
            }
            return;
        }

        if (mensaje.startsWith("/actualizar")) {
            String[] partes = mensaje.replace("/actualizar", "").split("\\|");
            if (partes.length < 4) {
                sendMessage(chatId, "❌ Usá: /actualizar ID | Nombre | Descripción | Telefono");
                return;
            }

            try {
                Long id = Long.parseLong(partes[0].trim());
                String nombre = partes[1].trim();
                String descripcion = partes[2].trim();
                String telefono = partes[3].trim();

                Oficio oficio = oficioServicio.buscarPorId(id);

                if (oficio == null) {
                    sendMessage(chatId, "⚠ No existe el oficio con ID " + id);
                    return;
                }

                oficio.setNombre(nombre);
                oficio.setDescripcion(descripcion);
                oficioServicio.updateOficio(oficio);

                sendMessage(chatId, "✏ Oficio actualizado!");
            } catch (Exception e) {
                sendMessage(chatId, "❌ Error en datos");
            }

            return;
        }

        if (mensaje.startsWith("/ver-oficios")) {
            List<Oficio> oficios = oficioServicio.obtenerTodos();

            if (oficios.isEmpty()) {
                sendMessage(chatId, "📋 No hay oficios.");
                return;
            }

            StringBuilder r = new StringBuilder("📋 *Oficios registradas:*\n\n");
            for (Oficio o : oficios) {
                r.append("ID: ").append(o.getId()).append("\n")
                        .append("Nombre: ").append(o.getNombre()).append("\n")
                        .append("Descripción: ").append(o.getDescripcion()).append("\n\n");
            }
            sendMessage(chatId, r.toString());
            return;
        }

        // -------------------------------
        //           CIUDAD
        // -------------------------------

        if (mensaje.startsWith("/alta_ciudad")) {
            String[] partes = mensaje.replace("/alta_ciudad", "").split("\\|");
            if (partes.length < 2) {
                sendMessage(chatId, "❌ Usá: /alta_ciudad Nombre | idOficio");
                return;
            }

            String nombre = partes[0].trim();
            Long idOficio = Long.parseLong(partes[1].trim());

            Oficio oficio = oficioServicio.buscarPorId(idOficio);
            if (oficio == null) {
                sendMessage(chatId, "❌ No existe oficio con ID " + idOficio);
                return;
            }

            Ciudad ciudad = new Ciudad();
            ciudad.setNombre(nombre);
            ciudad.setOficio(oficio);

            ciudadServicio.crearCiudad(ciudad);
            sendMessage(chatId, "🏙 Ciudad creada correctamente.");
            return;
        }

        if (mensaje.startsWith("/listar_ciudades")) {
            List<Ciudad> ciudades = ciudadServicio.obtenerTodas();

            if (ciudades.isEmpty()) {
                sendMessage(chatId, "📭 No hay ciudades registradas.");
                return;
            }

            StringBuilder r = new StringBuilder("🏙 *Ciudades:*\n\n");
            for (Ciudad c : ciudades) {
                r.append("ID: ").append(c.getId()).append("\n")
                        .append("Nombre: ").append(c.getNombre()).append("\n")
                        .append("Oficio ID: ").append(c.getOficio().getId()).append("\n\n");
            }
            sendMessage(chatId, r.toString());
            return;
        }

        if (mensaje.startsWith("/eliminar_ciudad")) {
            try {
                Long id = Long.parseLong(mensaje.replace("/eliminar_ciudad", "").trim());
                ciudadServicio.eliminarCiudad(id);
                sendMessage(chatId, "🗑 Ciudad eliminada.");
            } catch (Exception e) {
                sendMessage(chatId, "❌ Formato incorrecto.");
            }
            return;
        }

        if (mensaje.startsWith("/actualizar_ciudad")) {
            String[] partes = mensaje.replace("/actualizar_ciudad", "").split("\\|");

            if (partes.length < 3) {
                sendMessage(chatId, "❌ Usá: /actualizar_ciudad ID | Nombre | idOficio");
                return;
            }

            try {
                Long id = Long.parseLong(partes[0].trim());
                String nombre = partes[1].trim();
                Long idOficio = Long.parseLong(partes[2].trim());

                Ciudad ciudad = ciudadServicio.buscarPorId(id);
                if (ciudad == null) {
                    sendMessage(chatId, "❌ No existe ciudad con ID " + id);
                    return;
                }

                Oficio oficio = oficioServicio.buscarPorId(idOficio);
                if (oficio == null) {
                    sendMessage(chatId, "❌ No existe oficio con ID " + idOficio);
                    return;
                }

                ciudad.setNombre(nombre);
                ciudad.setOficio(oficio);

                ciudadServicio.actualizarCiudad(ciudad);
                sendMessage(chatId, "✏ Ciudad actualizada correctamente.");
            } catch (Exception e) {
                sendMessage(chatId, "❌ Error en el formato.");
            }
            return;
        }

        // -------------------------------
        //          TRABAJADOR
        // -------------------------------
        if (mensaje.startsWith("/alta_trabajador")) {
            String[] partes = mensaje.replace("/alta_trabajador", "").split("\\|");
            if (partes.length < 3) {
                sendMessage(chatId, "❌ Usá: /alta_trabajador Nombre | Telefono | idCiudad");
                return;
            }

            String nombre = partes[0].trim();
            String telefono = partes[1].trim();
            Long idCiudad = Long.parseLong(partes[2].trim());

            Ciudad ciudad = ciudadServicio.buscarPorId(idCiudad);
            if (ciudad == null) {
                sendMessage(chatId, "❌ No existe ciudad con ID " + idCiudad);
                return;
            }

            Trabajador t = new Trabajador();
            t.setNombre(nombre);
            t.setNumTelefono(telefono);
            t.setCiudad(ciudad);

            trabajadorServicio.crearTrabajador(t);
            sendMessage(chatId, "👷 Trabajador creado correctamente.");
            return;
        }

        if (mensaje.startsWith("/listar_trabajadores")) {
            List<Trabajador> list = trabajadorServicio.obtenerTodos();

            if (list.isEmpty()) {
                sendMessage(chatId, "📭 No hay trabajadores registrados.");
                return;
            }

            StringBuilder r = new StringBuilder("👷 *Trabajadores:*\n\n");
            for (Trabajador t : list) {
                r.append("ID: ").append(t.getId()).append("\n")
                        .append("Nombre: ").append(t.getNombre()).append("\n")
                        .append("Teléfono: ").append(t.getNumTelefono()).append("\n")
                        .append("Ciudad ID: ").append(t.getCiudad().getId()).append("\n\n");
            }
            sendMessage(chatId, r.toString());
            return;
        }

        if (mensaje.startsWith("/eliminar_trabajador")) {
            try {
                Long id = Long.parseLong(mensaje.replace("/eliminar_trabajador", "").trim());
                trabajadorServicio.eliminarTrabajador(id);
                sendMessage(chatId, "🗑 Trabajador eliminado.");
            } catch (Exception e) {
                sendMessage(chatId, "❌ Error. Formato incorrecto.");
            }
            return;
        }

        if (mensaje.startsWith("/actualizar_trabajador")) {
            String[] partes = mensaje.replace("/actualizar_trabajador", "").split("\\|");

            if (partes.length < 4) {
                sendMessage(chatId, "❌ Usá: /actualizar_trabajador ID | Nombre | Telefono | idCiudad");
                return;
            }

            try {
                Long id = Long.parseLong(partes[0].trim());
                String nombre = partes[1].trim();
                String telefono = partes[2].trim();
                Long idCiudad = Long.parseLong(partes[3].trim());

                Trabajador t = trabajadorServicio.buscarPorId(id);
                if (t == null) {
                    sendMessage(chatId, "❌ No existe trabajador con ID " + id);
                    return;
                }

                Ciudad ciudad = ciudadServicio.buscarPorId(idCiudad);
                if (ciudad == null) {
                    sendMessage(chatId, "❌ No existe ciudad con ID " + idCiudad);
                    return;
                }

                t.setNombre(nombre);
                t.setNumTelefono(telefono);
                t.setCiudad(ciudad);

                trabajadorServicio.actualizarTrabajador(t);
                sendMessage(chatId, "✏ Trabajador actualizado correctamente.");

            } catch (Exception e) {
                sendMessage(chatId, "❌ Error en el formato.");
            }
            return;
        }

        if (mensaje.startsWith("/buscar_trabajador")) {
            String text = mensaje.replace("/buscar_trabajador", "").trim().toLowerCase();

            if (text.isEmpty()) {
                sendMessage(chatId, "Usá: /buscar_trabajador nombre");
                return;
            }

            List<Trabajador> encontrados =
                    trabajadorServicio.obtenerTodos().stream()
                            .filter(t -> t.getNombre().toLowerCase().contains(text))
                            .collect(Collectors.toList());

            if (encontrados.isEmpty()) {
                sendMessage(chatId, "❌ No se encontraron trabajadores con ese nombre.");
                return;
            }

            StringBuilder r = new StringBuilder("🔍 *Resultados:*\n\n");
            for (Trabajador t : encontrados) {
                r.append("ID: ").append(t.getId()).append("\n")
                        .append("Nombre: ").append(t.getNombre()).append("\n")
                        .append("Teléfono: ").append(t.getNumTelefono()).append("\n")
                        .append("Ciudad ID: ").append(t.getCiudad().getId()).append("\n\n");
            }
            sendMessage(chatId, r.toString());
            return;
        }

        // -------------------------------
        //  Si no coincide ningún comando
        // -------------------------------
        sendMessage(chatId, "🤖 Comandos disponibles:\n"
                + "/alta\n/eliminar\n/actualizar\n/ver-oficios\n\n"
                + "/alta_ciudad\n/listar_ciudades\n/actualizar_ciudad\n/eliminar_ciudad\n\n"
                + "/alta_trabajador\n/listar_trabajadores\n/eliminar_trabajador\n/actualizar_trabajador\n/buscar_trabajador");
    }


    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "oficiooo_bot";
    }

    @Override
    public String getBotToken() {
        return "AQUI_VA_TU_TOKEN";
    }
}
