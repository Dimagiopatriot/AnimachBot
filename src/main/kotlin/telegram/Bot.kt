package telegram

import com.mashape.unirest.http.exceptions.UnirestException
import deviantart.rest.RestManager
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.logging.Level
import java.util.logging.Logger

class Bot : TelegramLongPollingBot() {

    companion object {
        private const val TOKEN = "636023743:AAE6hsYJo6Oow9x6c-v0CyY9RjXDtaFipcc"
    }

    private val logger: Logger = Logger.getLogger("[EchoBot]")
    private val restManager: RestManager = RestManager()

    override fun getBotUsername(): String = "AnimachBot"

    override fun getBotToken(): String = TOKEN

    override fun onUpdateReceived(update: Update?) {
        logger.log(Level.INFO, "Got update: $update")

        if (update!!.message != null) {
            val chatId = update.message.chat.id
            val text = update.message.text

            when {
                text?.startsWith(START_COMMAND) == true -> onStartCommand(chatId)
                text?.startsWith(RANDOM_PICTURE_FROM_RANDOM_AUTHOR) == true -> onGetRandomPictureFromRandomAuthor(chatId)
            }
        }
    }

    private fun onStartCommand(chatId: Long) = try {
        sendMessage(chatId, "Hello! I'm AnimachBot. I was created to send you pretty anime pics from DeviantArt!\n" +
                "Write /get_random_pic to get picture")
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send START response!", e)
    }

    private fun onGetRandomPictureFromRandomAuthor(chatId: Long) = try {
        restManager.getRandomPicFromRandomAuthor { picUrl -> sendPhoto(chatId, picUrl) }
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send GET_RANDOM_PIC_RANDOM_GUY response!", e)
    }

    private fun sendMessage(chatId: Long, text: String) {
        val message = SendMessage()
                .setChatId(chatId)
                .setText(text)
        execute(message)
    }

    private fun sendPhoto(chatId: Long, photo: String) {
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