import com.lucciola.operator.DivideNumber
import com.lucciola.operator.DivideUnit
import com.lucciola.termination.Number
import com.lucciola.termination.Unit
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DivideTest {

    @Test
    fun divideNumberTest() {
        val number1 = Number("10")
        val number2 = Number("2")

        assertThat(DivideNumber(number1, number2).evaluate()).isEqualTo("5.0")
    }

    @Test
    fun divideUnitTest() {
        val unit1 = Unit("m")
        val unit2 = Unit("m")

        assertThat(DivideUnit(unit1, unit2).evaluate()).isEqualTo("void^0")
    }
}