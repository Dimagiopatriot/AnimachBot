import deviantart.rest.Rest
import deviantart.rest.RestManager
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class RestTest {

    @Test
    fun testUserGallery() {
        val rest = Rest()
        val lock = CountDownLatch(1)
        rest.getUserGallery("chubymi", { response ->
            print(response)
            lock.countDown()
        })

        lock.await(30000, TimeUnit.MILLISECONDS)
    }

    @Test
    fun testRestManagerPictureUrlFroUserGalleryContentNull() {
        val restManager = RestManager()
        val lock = CountDownLatch(1)


        restManager.getPictureURLFromUserGallery("johnsu", 412, 61) { response ->
            println(response)
            lock.countDown()
        }

        lock.await(30000, TimeUnit.MILLISECONDS)
    }

    @Test
    fun testRestManagerRandomPicRandomUser() {
        val restManager = RestManager()
        val lock = CountDownLatch(1)

        restManager.getRandomPicFromRandomAuthor { response ->
            println(response)
            lock.countDown()
        }

        lock.await(30000, TimeUnit.MILLISECONDS)
    }
}