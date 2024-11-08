package framework.helpers.telegram;

import framework.helpers.general.PropertyHelper;
import framework.runners.TestNgRunner;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static framework.constants.GeneralConstants.*;

public class TelegramBotHelper extends TelegramLongPollingBot {
    public static final String BOT_NAME = PropertyHelper.initAndGetProperty(INIT_PROPERTIES,TELEGRAM_BOT_NAME);
    public static final String BOT_TOKEN = PropertyHelper.initAndGetProperty(INIT_PROPERTIES,TELEGRAM_BOT_TOKEN);

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();

            if (command.equals("/run_smoke_tests")) {
                String chatId = update.getMessage().getChatId().toString();
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Running TestNG smoke tests...");

                try {
                    String testResults = TestNgRunner.runSmoke();
                    message.setText(testResults);
                } catch (Exception e) {
                    message.setText("Error while running tests: " + e.getMessage());
                }

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
