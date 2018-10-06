package telegram

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import restartApplicationOnTimeout
import java.util.*

const val RESTART_TIME = 50000

fun main(args: Array<String>) {
    ApiContextInitializer.init()

    val telegramBotsApi = TelegramBotsApi()
    try {
        telegramBotsApi.registerBot(Bot())
    } catch (e: TelegramApiException) {
        e.stackTrace
    }
    //Timer in new thread
    Thread(Runnable {
        val currentTime = Calendar.getInstance().timeInMillis
        while (true) {
            if (RESTART_TIME < Calendar.getInstance().timeInMillis - currentTime) {
                restartApplicationOnTimeout()
            }

        }
    }).start()
}