import deviantart.rest.Rest
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class RestTest {

    private val lock = CountDownLatch(1)

    @Test
    fun testUserGallery() {
        val rest = Rest()
        rest.getUserGallery("chubymi", { response ->
            print(response)
            lock.countDown()
        })

        lock.await(30000, TimeUnit.MILLISECONDS)
    }
}