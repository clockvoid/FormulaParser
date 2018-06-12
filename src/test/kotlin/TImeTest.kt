import com.lucciola.operator.TimeNumber
import com.lucciola.operator.TimeUnit
import com.lucciola.termination.Number
import com.lucciola.termination.Unit
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TimeTest {

    @Test
    fun timeNumberTest() {
        val number1 = Number("10")
        val number2 = Number("2")

        assertThat(TimeNumber(number1, number2).evaluate()).isEqualTo("20")
    }

    @Test
    fun timeUnitTest() {
        val unit1 = Unit("m")
        val unit2 = Unit("m")

        assertThat(TimeUnit(unit1, unit2).evaluate()).isEqualTo("m^2")
    }
}
