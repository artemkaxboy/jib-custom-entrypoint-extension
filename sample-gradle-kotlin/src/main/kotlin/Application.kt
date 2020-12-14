// The most simple example of application

fun main(args: Array<String>) {
    args.joinToString(" ").ifEmpty { "sample-gradle-kotlin" }
        .let { println("Hello from $it") }
}
