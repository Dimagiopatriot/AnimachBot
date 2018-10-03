import java.io.IOException
import java.io.InputStream
import java.util.*

fun Properties.getAppProperty(propertyName: String) : String {
    val prop = Properties()
    var input: InputStream? = null

    return try {
        val fileName = "application.properties"
        input = javaClass.classLoader.getResourceAsStream(fileName)
        prop.load(input)

        prop.getProperty(propertyName)

    } catch (e: IOException) {
        e.stackTrace.toString()
    } finally {
        if (input != null) {
            try {
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}