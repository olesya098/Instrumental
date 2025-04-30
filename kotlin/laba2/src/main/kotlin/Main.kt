import kotlin.math.abs
import kotlin.math.min

// класс для представления точки с координатами
data class Point(val x: Int, val y: Int) {
    // метод для вычисления минимального расстояния до осей координат
    fun minDistance(): Int = min(abs(x), abs(y))
}

fun main() {
    // чтение количества точек из консоли
    val n = readLine()?.toIntOrNull() ?: run {
        // если ввод не является числом то сообщение об ошибке и завершаем программу
        println("Ошибка: введите целое число точек")
        return
    }

    // чтение координат точек и их обработка
    val points = List(n) {
        readLine()?.split(" ")?.mapNotNull { it.toIntOrNull() }?.let { numbers ->
            // проверяем, что введено ровно 2 координаты
            if (numbers.size == 2) Point(numbers[0], numbers[1]) else null
        } ?: run {
            // если формат неверный
            println("Ошибка: неверный формат координат точки")
            return
        }
    }.filter { point ->
        // фильтруем точки, которые не лежат на осях координат (x ≠ 0 и y ≠ 0)
        point.x != 0 && point.y != 0
    }

    //для хранения точек по четвертям:
    val quadrants = mapOf(
        1 to mutableListOf<Point>(),
        2 to mutableListOf<Point>(),
        3 to mutableListOf<Point>(),
        4 to mutableListOf<Point>()
    )

    // распределяем точки по соответствующим четвертям
    points.forEach { point ->
        when {
            point.x > 0 && point.y > 0 -> quadrants[1]?.add(point)  // первая четверть
            //quadrants[1] — обращение к списку точек первого квадранта.
            point.x < 0 && point.y > 0 -> quadrants[2]?.add(point)  // вторая четверть
            point.x < 0 && point.y < 0 -> quadrants[3]?.add(point)   // третья четверть
            point.x > 0 && point.y < 0 -> quadrants[4]?.add(point)   // четвертая четверть
        }
    }

    // находим лучшую четверть по критериям:
    // 1. максимальное количество точек в четверти
    // 2. если количество одинаковое - берем четверть с минимальным расстоянием до осей
    val bestEntry = quadrants.entries
        .sortedBy { it.key }  // сортируем четверти по номеру
        .maxWithOrNull(//ищет максимальный элемент по заданному
            compareBy<Map.Entry<Int, MutableList<Point>>> { entry ->
                // сравниваем по количеству точек в четверти
                entry.value.size
            }.thenBy { entry ->
                // если количество точек одинаковое, сравниваем по минимальному расстоянию до осей
                entry.value.minOfOrNull { point -> point.minDistance() } ?: Int.MAX_VALUE
            }
        )

    // вывод результатов
    if (bestEntry != null && bestEntry.value.isNotEmpty()) {//проверка на пустоту
        val bestQuadrant = bestEntry.key         // номер лучшей четверти
        val bestPoints = bestEntry.value         // список точек в лучшей четверти
        val bestPoint = bestPoints.minByOrNull { it.minDistance() }!!  // точка с минимальным расстоянием

        // выводим результаты:
        println("K = $bestQuadrant")                     // номер четверти
        println("M = ${bestPoints.size}")                // количество точек
        println("A = (${bestPoint.x}, ${bestPoint.y})")  // координаты лучшей точки
        println("R = ${bestPoint.minDistance()}")  // минимальное расстояние до осей
    } else {
        println("Нет точек в четвертях")
    }
}
//7
//-3 4
//1 2
//1 1
//0 4
//-2 -3
//-6 8
//-12 1