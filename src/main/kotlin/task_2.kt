fun main() {
    val alphabet = "абвгдежзиклмнопрстуфхцчшщъыьэюя"
    fun addCombinations(): List<Pair<Char, Char>> { //выходные данные - коллекия, разбитая на пары элементов
        val combinations = mutableListOf<Pair<Char, Char>>() //list("A","B","C") -> list(Pair(A,B),Pair(A,C),Pair(B,C))
        for (i in 0 until alphabet.length) { //перебор элементов, исключая последний символ
            for (j in 0 until alphabet.length) {
                combinations.add(alphabet[i] to alphabet[j]) //добавление комбинаций (Pair(A,B))
            }
        }
        return combinations
    }

    fun addTable(combinations: List<Pair<Char, Char>>): Map<String, String> {
        return combinations.mapIndexed { index, pair ->
            val value = "%03d".format(index + 1) //формат: 3-ч значное число, начиная с 001 (0 + 1 = 1)
            val key = "${pair.first}${pair.second}" //формирование пар букв
            key to value//присваивание паре букв индекса
        }.toMap() //преобразование в Map(), поскольку map представляет сопоставление ключа со значением и не допускает нулевых значений
    }

    fun printTable(table: Map<String, String>) { //вывод таблицы
        var counter = 1
        for ((key, value) in table) {
            print("$key: $value ")
            if (counter % alphabet.length == 0) {
                println()
            }
            counter++
        }
    }

    fun addPairs(message: String, helperSymbol: Char): List<String> { //разбиение символа на группы букв
        return (
                if (message.length % 2 == 1) //если слова содержит нечетное кол-во букв, то добавляем вспомогательный символ
                    message + helperSymbol
                else message).chunked(2) //иначе просто разбиваем на пары по 2 элемента
    }

    fun shifr(message: List<String>, table: Map<String, String>): String { //шифрование
        var res = ""
        for (i in message.indices) {
            res += "${table[message[i]]} "
        }
        return res.substring(0, res.length - 1) // Удалить последний пробел
    }

    fun deshifr(shifrMessage: String, table: Map<String, String>): String { //дешифрование
        fun getKey(value: String): String? {
            return table.entries.find { it.value == value }?.key
        }
        var res = ""
        val pairs = shifrMessage.replace(" ", "").chunked(3) //удаление пробела между вхлдными числами
        for (pair in pairs) { //и разбиение их на пары по три элемента
            res += getKey(pair)
        }
        return res
    }

    fun getValue(valueOneorTwo: String): String {
        print(valueOneorTwo)
        return readln()
    }

    when (getValue("Выберите действие:\n 1 - зашифровать\n 2 - расшифровать\n")) {
        "1" -> {
            val message = getValue("Введите исходное сообщение: ").lowercase().replace("ё", "е").replace("й", "и")
            val helperSymbol = getValue("Введите вспомогательный символ: ").lowercase()[0]
            val typeTable = getValue("Использовать типовую таблицу? (да/нет): ").lowercase() == "да"
            var combinations = addCombinations()
            if (!typeTable) combinations = combinations.shuffled()
            val table = addTable(combinations)
            println("Таблица шифр ПОРТЫ:")
            printTable(table)
            val pairs = addPairs(message, helperSymbol)
            println("Разбиение сообщения на пары символов...\n ${pairs.joinToString(", ")}")
            println("Зашифрованное сообщение:\n ${shifr(pairs, table)}")
        }
        "2" -> {
            val typeTable = getValue("Использовать типовую таблицу? (да/нет): ").lowercase() == "да"
            var combinations = addCombinations()
            if (!typeTable) combinations = combinations.shuffled()  //Возвращает новый список, элементы которого перемешаны случайным образом
            val table = addTable(combinations)
            println("Таблица шифр ПОРТЫ:")
            printTable(table)
            val cipher = getValue("Введите зашифрованное сообщение: ").lowercase()
            println("Расшифрованное сообщение: ${deshifr(cipher, table)}")
        }
    }
}