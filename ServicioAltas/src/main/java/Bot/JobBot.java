package Bot;

import Modelo.Oficio;
import Servicio.OficioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
public class JobBot extends TelegramLongPollingBot {
    @Autowired
    private OficioServicio oficioServicio;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText(); //  esto es lo que escribió el usuario
            long chatId = update.getMessage().getChatId(); //  esto es para responderle

            if (text.startsWith("/alta")) {
                String[] parts = text.replace("/alta", "").split("\\|");
                if (parts.length < 2) {
                    sendMessage(chatId, "Formato incorrecto. Usa: /alta Nombre | Descripción");
                    return;
                }

                String nombre = parts[0].trim();
                String descripcion = parts[1].trim();

                Oficio oficio = new Oficio();
                oficioServicio.crearOficio(oficio);

                sendMessage(chatId, "Oficio guardado correctamente:\nNombre: " + nombre + "\nDescripción: " + descripcion);
            } else {
                sendMessage(chatId, "Comando no reconocido. Usa /alta Nombre | Descripción");
            }
        }
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try{
            execute(message);
        } catch (org.telegram.telegrambots.meta.exceptions.TelegramApiException e){
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername(){
        return "oficiooo_bot";
    }
    @Override
    public String getBotToken(){
        return "8381310145:AAFSQwZJ7JvvvtIWAkj5euBEVYRFXiyfMyI";
    }

}
