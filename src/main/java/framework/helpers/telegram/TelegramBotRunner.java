package framework.helpers.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotRunner {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            botsApi.registerBot(new TelegramBotHelper());
            System.out.println("Bot is running...");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.err.println("Failed to register bot: " + e.getMessage());
        }
    }
}
