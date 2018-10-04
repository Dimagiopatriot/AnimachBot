import java.io.IOException
import java.io.InputStream
import java.util.*

private fun propertiesAccess(input: InputStream?, function: (Properties) -> Unit, prop: Properties = Properties()) {
    try {
        prop.load(input)
        return function(prop)

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

fun Properties.getAppProperty(propertyName: String): String {
    val fileName = "application.properties"
    val input = javaClass.classLoader.getResourceAsStream(fileName)
    var result = ""
    propertiesAccess(input, { properties -> result = properties.getProperty(propertyName) })
    return result
}

fun Properties.saveAppPropertyValue(propertyName: String, propertyValue: String) {
    val fileName = "application.properties"
    val input = javaClass.classLoader.getResourceAsStream(fileName)
    propertiesAccess(input, { properties -> properties.setProperty(propertyName, propertyValue) })
}