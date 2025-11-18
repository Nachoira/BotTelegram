package BotDeAlta.ServicioAltas.Bot;

import BotDeAlta.ServicioAltas.Modelo.Oficio;
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

    /**
     * Método que se ejecuta cada vez que el bot de Telegram recibe una actualización (mensaje).
     * Procesa comandos enviados por el usuario para gestionar oficios.
     */
    @Override
    public void onUpdateReceived(Update update) {
        // Verifica que el mensaje recibido tenga texto
        if (update.hasMessage() && update.getMessage().hasText()) {
            String mensaje = update.getMessage().getText();
           long chatId = update.getMessage().getChatId();

            System.out.println("Mensaje recibido: " + mensaje);
           // Comando para crear un nuevo oficio
            if (mensaje.startsWith("/alta")) {
                String[] partes = mensaje.replace("/alta", "").split("\\|");
                if (partes.length < 2) {
                    sendMessage(chatId, "❌ Formato incorrecto. Usá: /alta Nombre | Descripción | Telefono");
                    return;
                }
// Extrae los datos del mensaje
                String nombre = partes[0].trim();
                String descripcion = partes[1].trim();
                String  telefono = partes[2].trim();
// Crea y guarda el oficio

                Oficio oficio = new Oficio();
                oficio.setNombre(nombre);
                oficio.setDescripcion(descripcion);
                oficio.setTelefono(telefono);

                oficioServicio.crearOficio(oficio);
                sendMessage(chatId, "✅ Oficio guardado:\nNombre: " + nombre + "\nDescripción: " + descripcion + "\nTelefono:" + telefono);

            } else if (mensaje.startsWith("/eliminar")) {
                try {
                    Long id = Long.parseLong(mensaje.replace("/eliminar", "").trim());
                    oficioServicio.deleteOficio(id);
                    sendMessage(chatId, "🗑️ Oficio eliminado (ID: " + id + ")");
                } catch (Exception e) {
                    sendMessage(chatId, "❌ Error al eliminar. Usá: /eliminar [id]");
                    e.printStackTrace();
                }

            } else if (mensaje.startsWith("/actualizar")) {
                String[] partes = mensaje.replace("/actualizar", "").split("\\|");
                if (partes.length < 3) {
                    sendMessage(chatId, "❌ Formato incorrecto. Usá: /actualizar ID | Nombre | Descripción | Telefono");
                    return;
                }

                try {
                    Long id = Long.parseLong(partes[0].trim());
                    String nombre = partes[1].trim();
                    String descripcion = partes[2].trim();
                    String telefono = partes[3].trim();

                    Oficio oficio = oficioServicio.buscarPorId(id);
                    if (oficio == null) {
                        sendMessage(chatId, "⚠️ No se encontró el oficio con ID: " + id);
                        return;
                    }

                    oficio.setNombre(nombre);
                    oficio.setDescripcion(descripcion);
                    oficio.setTelefono(telefono);
                    oficioServicio.updateOficio(oficio);

                    sendMessage(chatId, "✏️ Oficio actualizado:\nID: " + id + "\nNombre: " + nombre + "\nDescripción: " + descripcion  + "\nTelefono:" + telefono);
                } catch (Exception e) {
                    sendMessage(chatId, "❌ Error al actualizar. Usá: /actualizar ID | Nombre | Descripción | Telefono");
                    e.printStackTrace();
                }

            } else if (mensaje.startsWith("/buscar")) {
                String termino = mensaje.replace("/buscar", "").trim().toLowerCase();

                if (termino.isEmpty()) {
                    sendMessage(chatId, "🔍 Por favor, escribí el nombre del oficio que querés buscar. Ejemplo: /buscar carpintero");
                    return;
                }

                List<Oficio> resultados = oficioServicio.obtenerTodos().stream()
                        .filter(o -> o.getNombre().toLowerCase().contains(termino))
                        .collect(Collectors.toList());

                if (resultados.isEmpty()) {
                    sendMessage(chatId, "❌ No se encontraron oficios que coincidan con: " + termino);
                } else {
                    StringBuilder response = new StringBuilder("🔎 *Resultados encontrados:*\n\n");
                    for (Oficio oficio : resultados) {
                        response.append("🆔 ID: ").append(oficio.getId()).append("\n")
                                .append("🔹 Nombre: ").append(oficio.getNombre()).append("\n")
                                .append("📝 Descripción: ").append(oficio.getDescripcion()).append("\n\n")
                                .append("Telefono: ").append(oficio.getTelefono()).append("\n\n");
                    }
                    sendMessage(chatId, response.toString());
                }
            } else if (mensaje.startsWith("/ver-oficios")) {
                 List<Oficio> oficios = oficioServicio.obtenerTodos();

                 if (oficios.isEmpty()) {
                     sendMessage(chatId, "📋 No hay oficios registrados.");
                     return;
                 }

                 StringBuilder response = new StringBuilder("📋 *Oficios registrados:*\n\n");
                 for (Oficio oficio : oficios) {
                     response.append("🆔 ID: ").append(oficio.getId()).append("\n")
                             .append("🔹 Nombre: ").append(oficio.getNombre()).append("\n")
                             .append("📝 Descripción: ").append(oficio.getDescripcion()).append("\n\n")
                             .append("Telefono: ").append(oficio.getTelefono()).append("\n\n");
                 }

                 sendMessage(chatId, response.toString());

             } else {
                 sendMessage(chatId, "🤖 Comando no reconocido. Usá /alta, /eliminar, /actualizar o /ver-oficios");
             }

        }
    }


    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (org.telegram.telegrambots.meta.exceptions.TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "oficiooo_bot";
    }

    @Override
    public String getBotToken() {
        return "8381310145:AAFSQwZJ7JvvvtIWAkj5euBEVYRFXiyfMyI";
    }
}

