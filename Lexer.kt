import TokenType

data class Token(val type: TokenType, val value: String)

class Lexer(private val source: String) {

    private var tokens: MutableList<Token> = mutableListOf()
    private var position: Int = 0

    open fun generate(): MutableList<Token> {
        while (position < source.length) {
            val current = source[position]
            when {
                current.isWhitespace() -> position++
                current.isLetter() -> identifier()
                current.isDigit() -> number()
                current == '"' || current == '\'' -> string()
                current in "()[]{},.;=" -> delimiter()
                current in "+-*/%<>" -> operator()
                else -> throw IllegalArgumentException("Unexpected character '$current'")
            }
        }

        return tokens
    }

    private fun identifier() {
        val start = position
        while (position < source.length && (source[position].isLetterOrDigit() || source[position] == '_')) {
            position++
        }

        val value = source.substring(start, position)
        val type = when (value) {
            "int" -> TokenType.INT
            "str" -> TokenType.STR
            "float" -> TokenType.FLOAT
            "fun" -> TokenType.FUN
            "struct" -> TokenType.STRUCT
            "if" -> TokenType.IF
            "else" -> TokenType.ELSE
            else -> TokenType.IDENTIFIER
        }

        tokens.add(Token(type, value))
    }

    private fun number() {
        val start = position
        var hasDec = false
        while (position < source.length && (source[position].isDigit() || source[position] == '.')) {
            if (source[position] == '.') {
                if (hasDec) break
                hasDec = true
            }
            position++
        }

        tokens.add(Token(TokenType.NUMBER, source.substring(start, position)))
    }

    private fun string() {
        val start = position
        position++
        while (position < source.length && (source[position] != '"' || source[position] != '\'')) {
            position++
        }

        if (position >= source.length) {
            throw IllegalArgumentException("Unterminated string")
        }

        position++
        tokens.add(Token(TokenType.STRING, source.substring(start, position)))
    }

    private fun delimiter() {
        val type = when (source[position]) {
            '(' -> TokenType.LPAREN
            ')' -> TokenType.RPAREN
            '[' -> TokenType.LBRACKET
            ']' -> TokenType.RBRACKET
            '{' -> TokenType.LBRACE
            '}' -> TokenType.RBRACE
            ',' -> TokenType.COMMA
            ';' -> TokenType.SEMICOLON
            '.' -> TokenType.DOT
            '=' -> TokenType.EQUALS
            else -> throw IllegalArgumentException("Unexpected symbol '${source[position]}'")
        }

        tokens.add(Token(type, source[position].toString()))
        position++
    }

    private fun operator() {
        val start = position
        while (position < source.length && source[position] in "+-*/%<>") {
            position++
        }
        tokens.add(Token(TokenType.OPERATOR, source.substring(start, position)))
    }
}