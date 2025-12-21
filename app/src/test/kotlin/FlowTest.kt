import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowTest {
    fun children(): Sequence<Int> = sequence { // sequence builder
        for (i in 1..3) {
            Thread.sleep(3000) // pretend we are computing it
            yield(i) // yield next value
        }
    }

    fun you(): Sequence<String> = sequence { // sequence builder
        Thread.sleep(1000)
        yield("가")
        Thread.sleep(1000)
        yield("나")
        Thread.sleep(1000)
        yield("다")
    }

    @Test
    fun main() = runBlocking<Unit> {
        launch(Dispatchers.Default) {
            children().forEach { value -> println(value) }
        }
        launch(Dispatchers.Default) {
            you().forEach { value -> println(value) }
        }
    }
}