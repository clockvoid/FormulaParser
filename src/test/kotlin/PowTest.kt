import com.lucciola.operator.PowNumber
import com.lucciola.operator.PowUnit
import com.lucciola.termination.Number
import com.lucciola.termination.Unit
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PowTest {

    @Test
    fun powNumberTest() {
        val number1 = Number("10")
        val number2 = Number("2")

        assertThat(PowNumber(number1, number2).evaluate()).isEqualTo("100.0")
    }

    @Test
    fun powUnitTest() {
        val unit1 = Unit("m")
        val unit2 = Number("2")

        assertThat(PowUnit(unit1, unit2).evaluate()).isEqualTo("m^2")
    }
}
