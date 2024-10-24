package framework.helpers.telegram;

import framework.helpers.general.PropertiesReaderHelper;
import framework.helpers.testng.TestNgRunner;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotHelper extends TelegramLongPollingBot {
    public static final String BOT_NAME = new PropertiesReaderHelper("init.properties").getProperty("bot.name");
    public static final String BOT_TOKEN = new PropertiesReaderHelper("init.properties").getProperty("bot.token");

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
