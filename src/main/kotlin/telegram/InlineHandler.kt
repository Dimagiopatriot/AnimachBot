package telegram

import deviantart.GalleryItem
import deviantart.rest.RestManager
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery
import java.net.URLEncoder
import java.util.logging.Level
import java.util.logging.Logger

class InlineHandler(private val bot: TelegramLongPollingBot) {

    private val logger: Logger = Logger.getLogger("[CommandHandler]")
    private val restManager = RestManager.instance

    private var offset: Int = 0


    fun handleIncomingInlineQuery(inlineQuery: InlineQuery) {
        val query = inlineQuery.query
        logger.log(Level.INFO, "Searching: $query, id ${inlineQuery.id}")

        if (query.isNotEmpty()) {

            if (inlineQuery.offset.isNotBlank()) {
                offset += inlineQuery.offset.toInt()
            } else {
                offset = 0
            }

            restManager.getInlineResults(URLEncoder.encode(query, "UTF-8"), offset) { items ->
                onResult(inlineQuery, items)
                logger.log(Level.INFO, "Items: $items")
            }
        } else {
            bot.execute(convertResultsToResponse(inlineQuery, mutableListOf()))
        }
    }

    fun onResult(inlineQuery: InlineQuery, items: List<GalleryItem>) {
        bot.execute(convertResultsToResponse(inlineQuery, items))
    }
}