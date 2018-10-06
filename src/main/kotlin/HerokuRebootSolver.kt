import telegram.Bot
import java.io.File

fun restartApplicationOnTimeout() {
    val javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java"
    val currentJar = File(Bot::class.java.protectionDomain.codeSource.location.toURI())

    if (!currentJar.name.endsWith(".jar"))
        return
    val command = listOf(javaBin, "-jar", currentJar.path)
    val builder = ProcessBuilder(command)
    builder.start()
    System.exit(0)

}