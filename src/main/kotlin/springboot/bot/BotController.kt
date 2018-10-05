package springboot.bot

import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import deviantart.Update
import deviantart.rest.RestManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger

@RestController
class BotController {

    companion object {
        private val API_ENDPOINT = "https://api.telegram.org/bot"

        private val START_COMMAND = "/start"
        private val RANDOM_PICTURE_FROM_RANDOM_AUTHOR = "/get_random_pic"
    }

    private val logger: Logger = Logger.getLogger("[EchoBot]")
    private val restManager: RestManager = RestManager()

    @Value("\${token}")
    lateinit var token: String

    @PostMapping("/\${token}")
    fun onUpdate(@RequestBody update: Update) {
        logger.log(Level.INFO, "Got update: $update")

        if (update.message != null) {
            val chatId = update.message.chat.id
            val text = update.message.text

            when {
                text?.startsWith(START_COMMAND) == true -> onStartCommand(chatId)
                text?.startsWith(RANDOM_PICTURE_FROM_RANDOM_AUTHOR) == true -> onGetRandomPictureFromRaandomAuthor(chatId)
            }
        }
    }

    private fun onStartCommand(chatId: Long) = try {
        sendMessage(chatId, "Hello! I'm EchoBot.")
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send START response!", e)
    }

    private fun onGetRandomPictureFromRaandomAuthor(chatId: Long) = try {
        restManager.getRamdomPicFromRandomAuthor { picUrl -> sendMessage(chatId, picUrl) }
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send GET_RANDOM_PIC_RANDOM_GUY response!", e)
    }

    @Throws(UnirestException::class)
    private fun sendMessage(chatId: Long, text: String) {
        Unirest.post("$API_ENDPOINT$token/sendMessage")
                .field("chat_id", chatId)
                .field("text", text)
                .asJson()
    }
}