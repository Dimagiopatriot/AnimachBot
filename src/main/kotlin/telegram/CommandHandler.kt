package telegram

import com.mashape.unirest.http.exceptions.UnirestException
import deviantart.rest.RestManager
import java.util.logging.Level
import java.util.logging.Logger

class CommandHandler(private val bot: Bot) {

    private val logger: Logger = Logger.getLogger("[CommandHandler]")
    private val restManager = RestManager.instance

    fun onStartCommand(chatId: Long) = try {
        bot.sendMessage(chatId, "Hello! I'm AnimachBot. I was created to send you pretty anime pics from DeviantArt!\n" +
                "Write /get_random_pic to get picture")
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send START response!", e)
    }

    fun onGetRandomPictureFromRandomAuthorCommand(chatId: Long) = try {
        restManager.getRandomPicFromRandomAuthor { picUrl -> bot.sendPhoto(chatId, picUrl) }
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send GET_RANDOM_PIC_RANDOM_GUY response!", e)
    }
}