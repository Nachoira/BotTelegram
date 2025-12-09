package BotDeAlta.ServicioAltas.Bot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        // Solo crea el objeto API. No registra nada aún.
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    public JobBot registerBot(TelegramBotsApi telegramBotsApi, JobBot jobBot) {
        try {
            telegramBotsApi.registerBot(jobBot);  // ya no rompe el arranque
        } catch (Exception e) {
            System.out.println("No se pudo borrar webhook anterior (normal en LongPolling). Se continúa igual.");
        }
        return jobBot;
    }
}
