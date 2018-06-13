import com.lucciola.exception.RuntimeErrorException
import com.lucciola.operator.MinusNumber
import com.lucciola.operator.MinusUnit
import org.junit.Test
import com.lucciola.termination.Number
import com.lucciola.termination.Unit
import org.assertj.core.api.Assertions.assertThat

class MinusTest {

    @Test
    fun minusNumberTest() {
        val number1 = Number("10")
        val number2 = Number("10")

        assertThat(MinusNumber(number1, number2).evaluate()).isEqualTo("0")
    }

    @Test
    fun minusUnitTest() {
        val unit1 = Unit("m")
        val unit2 = Unit("m")
        assertThat(MinusUnit(unit1, unit2).evaluate()).isEqualTo("m")
    }

    @Test
    fun minusUnitExceptionTest() {
        val unit3 = Unit("sec")
        val unit2 = Unit("m")

        try {
            MinusUnit(unit2, unit3).evaluate()
        } catch (e: RuntimeErrorException) {
            assertThat(e.message).isEqualTo("It does not match unit ${unit2.evaluate()} and ${unit3.evaluate()}.")
        }
    }
}