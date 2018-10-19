package telegram

import deviantart.GalleryItem
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto

fun convertResultsToResponse(inlineQuery: InlineQuery, itemsFromResponse: List<GalleryItem>): AnswerInlineQuery {
    val answerInlineQuery = AnswerInlineQuery()
    answerInlineQuery.inlineQueryId = inlineQuery.id
    answerInlineQuery.cacheTime = 0
    answerInlineQuery.results = convertItemsResponse(itemsFromResponse)
    return answerInlineQuery
}

fun convertItemsResponse(itemsFromResponse: List<GalleryItem>): List<InlineQueryResult> {

    val results = mutableListOf<InlineQueryResult>()

    for (item in itemsFromResponse.withIndex()) {
        val itemValue = item.value
        val picture = InlineQueryResultPhoto()

        if (itemValue.content != null && itemValue.thumbs.isNotEmpty()) {
            picture.id = item.index.toString()
            picture.thumbUrl = item.value.thumbs[0].url
            picture.photoHeight = item.value.thumbs[0].height
            picture.photoWidth = item.value.thumbs[0].width
            picture.photoUrl = item.value.content?.artUrl
            results.add(picture)
        }
    }
    return results
}