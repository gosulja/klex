import java.io.File
import util.Util

fun main() {
    var util = Util()

    val testsDir = File("tests")
    if (!testsDir.exists() || !testsDir.isDirectory) {
        throw IllegalArgumentException("Cannot find tests directory")
    }

    val tests = testsDir.walk()
        .filter { it.isFile && it.name.endsWith(".ruf") }
        .map { it.readText() }
        .toList()

    try {
        tests.forEachIndexed { index, content ->
            println("File $index {")
            val lexer = Lexer(content)
            val tokens = lexer.generate()

            for (token in tokens) {
                var typ = util.padding("Type: ${token.type}", 20)
                println("    $typ | Value: ${token.value}")
            }
            println("}")
        }
    } catch (e: IllegalArgumentException) {
        println("Error: ${e.message}")
    }
}