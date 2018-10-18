package telegram

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class Bot : TelegramLongPollingBot() {

    private var TOKEN = ""

    private val logger: Logger = Logger.getLogger("[EchoBot]")

    init {
        val prop = Properties()
        prop.load(this::javaClass.javaClass.classLoader.getResourceAsStream("application.properties"))
        TOKEN = prop.getProperty("token")
    }

    override fun getBotUsername(): String = "AnimachBot"

    override fun getBotToken(): String = TOKEN

    override fun onUpdateReceived(update: Update?) {
        logger.log(Level.INFO, "Got update: $update")
        val commandHandler = CommandHandler(this)
        val inlineHandler = InlineHandler(this)

        when {

            update!!.hasInlineQuery() -> {
                inlineHandler.handleIncomingInlineQuery(update.inlineQuery)
            }

            update.hasMessage() -> {
                val chatId = update.message.chat.id
                val text = update.message.text

                when {
                    text?.startsWith(START_COMMAND) == true -> commandHandler.onStartCommand(chatId)
                    text?.startsWith(RANDOM_PICTURE_FROM_RANDOM_AUTHOR) == true -> commandHandler.onGetRandomPictureFromRandomAuthorCommand(chatId)
                }
            }
        }
    }


    fun sendMessage(chatId: Long, text: String) {
        val message = SendMessage()
                .setChatId(chatId)
                .setText(text)
        execute(message)
    }

    fun sendPhoto(chatId: Long, photo: String) {
        val lines = photo.lines()
        val message = SendPhoto()
                .setChatId(chatId)
                //pic url
                .setPhoto(lines[0])
                //text, if get onError()
                .setCaption(if (lines.size == 2) lines[1] else "")
        execute(message)
    }
}