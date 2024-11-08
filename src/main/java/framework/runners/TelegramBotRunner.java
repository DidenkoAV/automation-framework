package framework.runners;

import framework.helpers.log.LogHelper;
import framework.helpers.telegram.TelegramBotHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotRunner {
    private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            botsApi.registerBot(new TelegramBotHelper());
            logger.info("Bot is running...");
        } catch (TelegramApiException e) {
            logger.error("Failed to register bot: " + e.getMessage());
        }
    }
}
