
fun restartApplicationOnTimeout() {
    val command = "heroku restart"
    Runtime.getRuntime().exec(command)
    System.exit(0)
}
