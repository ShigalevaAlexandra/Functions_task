// Создать консольную игру "Камень-Ножницы-Бумага" (три события). На консоль выводится выбор компьютера (случайное значение) и выбор игрока.
// Для ускорения ввода выбора игрока можно условиться, что, например, 1 - это камень, 2 - ножницы, 3 - бумага.

// Победитель определяется по следующим правилам:

//     Бумага побеждает камень («бумага обёртывает камень»).
//     Камень побеждает ножницы («камень затупляет или ломает ножницы»).
//     Ножницы побеждают бумагу («ножницы разрезают бумагу»).

// Если игроки показали одинаковый знак, то засчитывается ничья и игра переигрывается.

fun main () {
    fun input_znak(znak: Int): String { //объявление функции, которая возвращает значение выбранного элемента
        if (znak == 1) {
            return " камень"
        } else if (znak == 2) {
            return " ножницы"
        } else if (znak == 3) {
            return " бумага"
        } else {
            return "ERROR: действия нумеруются 1 - 3"
        }
    }

    fun game_win(user_znak:Int, bot_znak:Int): String {
        if (((user_znak == 3) && (bot_znak == 1)) //бумага обёртывает камень
            || ((user_znak == 1) && (bot_znak == 2))  //камень ломает ножницы
            || ((user_znak == 2) && (bot_znak == 3))  //ножницы разрезают бумагу
        ) {
            return "\n" +
                    " You WINNER!"
        } else if (user_znak == bot_znak) {
            return "\n" +
                    " Dead Heat..."
        } else {
            return " Game OVER"
        }
    }

    println("Выберите элемент (цифру): 1 - камень, 2 - ножницы, 3 - бумага ")
    var user_znak = readLine()?.toInt() ?: 0

    var bot_znak = (Math.random() * 3).toInt() + 1

    println (input_znak(user_znak) + " VS " + input_znak(bot_znak))
    println(game_win(user_znak, bot_znak))
}