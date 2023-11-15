fun main() {
    val alphabet = "абвгдежзиклмнопрстуфхцчшщъыьэюя"
    fun generateCombinations(): List<Pair<Char, Char>> {
        val combinations = mutableListOf<Pair<Char, Char>>()
        for (i in 0 until alphabet.length) {
            for (j in 0 until alphabet.length) {
                combinations.add(alphabet[i] to alphabet[j])
            }
        }
        return combinations
    }

    fun generateTable(combinations: List<Pair<Char, Char>>): Map<String, String> {
        return combinations.mapIndexed { index, pair ->
            val value = "%03d".format(index + 1)
            val key = "${pair.first}${pair.second}"
            key to value
        }.toMap()
    }

    fun printTable(table: Map<String, String>) {
        var counter = 1
        for ((key, value) in table) {
            print("$key: $value ")
            if (counter % alphabet.length == 0) {
                println()
            }
            counter++
        }
    }

    fun splitIntoPairs(message: String, helperSymbol: Char): List<String> {
        return (if (message.length % 2 == 1) message + helperSymbol else message).chunked(2)
    }

    fun encrypt(message: List<String>, table: Map<String, String>): String {
        var res = ""
        for (i in message.indices) {
            res += "${table[message[i]]} "
        }
        return res.substring(0, res.length - 1) // Удалить последний пробел
    }

    fun decrypt(cipher: String, table: Map<String, String>): String {
        fun getKey(value: String): String? {
            return table.entries.find { it.value == value }?.key
        }
        var res = ""
        val pairs = cipher.replace(" ", "").chunked(3)
        for (pair in pairs) {
            res += getKey(pair)
        }
        return res
    }

    fun getInput(prompt: String): String {
        print(prompt)
        return readln()
    }

    when (getInput("1 - зашифровать\n2 - расшифровать\nВыберите действие: ")) {
        "1" -> {
            val message = getInput("Введите исходное сообщение: ").lowercase().replace("ё", "е").replace("й", "и")
            val helperSymbol = getInput("Введите вспомогательный символ: ").lowercase()[0]
            val typeTable = getInput("Использовать типовую таблицу? (y/n): ").lowercase() == "y"
            var combinations = generateCombinations()
            if (!typeTable) combinations = combinations.shuffled()
            val table = generateTable(combinations)
            println("Таблица:")
            printTable(table)
            val pairs = splitIntoPairs(message, helperSymbol)
            println("Исходное сообщение, разбитое по парам со вспомогательным символом: ${pairs.joinToString(", ")}")
            println("Зашифрованное сообщение: ${encrypt(pairs, table)}")
        }
        "2" -> {
            val typeTable = getInput("Использовать типовую таблицу? (y/n): ").lowercase() == "y"
            var combinations = generateCombinations()
            if (!typeTable) combinations = combinations.shuffled()
            val table = generateTable(combinations)
            println("Таблица:")
            printTable(table)
            val cipher = getInput("Введите шифр: ").lowercase()
            println("Расшифрованное сообщение: ${decrypt(cipher, table)}")
        }
    }
}