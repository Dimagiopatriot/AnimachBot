package telegram

import deviantart.GalleryItem
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto

fun convertResultsToResponse(inlineQuery: InlineQuery, itemsFromResponse: List<GalleryItem>): AnswerInlineQuery {
    val answerInlineQuery = AnswerInlineQuery()
    answerInlineQuery.inlineQueryId = inlineQuery.id
    answerInlineQuery.cacheTime = 0
    answerInlineQuery.nextOffset = "24"
    val filteredResults = itemsFromResponse.filter { it.thumbs.isNotEmpty() && it.content != null }
    answerInlineQuery.results = filteredResults.mapIndexed { index, galleryItem ->
        InlineQueryResultPhoto().apply {
            this.id = index.toString()
            this.thumbUrl = galleryItem.thumbs[0].url
            this.photoHeight = galleryItem.thumbs[0].height
            this.photoWidth = galleryItem.thumbs[0].width
            this.photoUrl = galleryItem.content?.artUrl
        }
    }
    return answerInlineQuery
}