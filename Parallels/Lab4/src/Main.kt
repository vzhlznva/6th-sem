import java.util.Collections.sort
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import kotlin.collections.ArrayList
import kotlin.random.Random


const val SIZE: Int = 15

fun main(args: Array<String>) {
    // Создаю и заполняю 2 массива случаными числами
    val a1 = IntArray(SIZE) { Random.nextInt(SIZE)}.toList()
    val a2 = IntArray(SIZE) { Random.nextInt(SIZE)}.toList()

    // Вывод массивов
    println(a1)
    println(a2)

    // Первый фьючерс
    val firstFuture: CompletableFuture<List<Int>?> = CompletableFuture
        .supplyAsync { a1 }
        .thenApplyAsync { first: List<Int>? ->
            // Создаю промежуточную переменную, присваиваю ей
            // массив содержащий элементы, меньше максимального значения массива
            val a: List<Int>? = first
                ?.filter { it < first.maxOrNull()!! }
            // Сортируем
            sort(a)
            a
        }

    // Создание второго фьючерса

    val secondFuture: CompletableFuture<List<Int>?> = CompletableFuture
        .supplyAsync { a2 }
        .thenApplyAsync { second: List<Int>? ->
            // Создаю промежуточную переменную, присваиваю ей
            // массив содержащий элементы, кратные 3-м
            val a: List<Int>? = second?.filter { it % 3 == 0 }
            // Сортируем
            sort(a)
            a
        }

//    println()
//   println("Result: " + firstFuture.get())
//   println("Result: " + secondFuture.get())


    // Финальный фьючерс, в котором получаем финальний массив
    val finalResultFuture: CompletableFuture<List<Int>> = firstFuture.thenCombine(
        secondFuture
    ) { first: List<Int>?, second: List<Int>? ->
        // Промежуточная переменная равная значению первого массива
        val a: MutableList<Int> = ArrayList(second!!)
        // Удаляю элементы со второго массива, которые содержатся в первом
        first.let {
            if (it != null) {
                a.removeAll(it)
            }
        }
        a
    }

    // Вывод значений с последнего фьючерса
    try {
        println("\nFinal Result: " + finalResultFuture.get())
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } catch (e: ExecutionException) {
        e.printStackTrace()
    }
}
