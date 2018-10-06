
fun restartApplicationOnTimeout() {
    val command = ("java -jar build/libs/AnimachBot-1.0-SNAPSHOT.jar")
    Runtime.getRuntime().exec(command)
    System.exit(0)

}