import deviantart.Rest
import deviantart.UserGalleryResponse
import org.junit.Test

class RestTest {

    @Test
    fun testUserGallery() {
        val rest = Rest()
        val response = rest.getUserGallery("chubymi")
        assert(true)
    }
}