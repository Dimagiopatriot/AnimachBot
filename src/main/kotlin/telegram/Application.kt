package telegram

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

fun main(args: Array<String>) {
    ApiContextInitializer.init()

    val telegramBotsApi = TelegramBotsApi()
    try {
        telegramBotsApi.registerBot(Bot())
    } catch (e: TelegramApiException) {
        e.stackTrace
    }
}