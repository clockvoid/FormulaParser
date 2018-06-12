import com.lucciola.exception.RuntimeErrorException
import com.lucciola.operator.PlusNumber
import com.lucciola.operator.PlusUnit
import org.junit.Test
import com.lucciola.termination.Number
import com.lucciola.termination.Unit
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.rules.ExpectedException

class PlusTest {

    lateinit var thrown: ExpectedException

    @Before
    fun init() {
        thrown = ExpectedException.none()
    }

    @Test
    fun plusNumberTest() {
        val number1 = Number("10")
        val number2 = Number("10")

        assertThat(PlusNumber(number1, number2).evaluate()).isEqualTo("20")
    }

    @Test
    fun plusUnitTest() {
        val unit1 = Unit("m")
        val unit2 = Unit("m")
        assertThat(PlusUnit(unit1, unit2).evaluate()).isEqualTo("m")
    }

    @Test
    fun plusUnitExceptionTest() {
        val unit3 = Unit("sec")
        val unit2 = Unit("m")
        thrown.expect(RuntimeErrorException::class.java)
        thrown.expectMessage("It does not match unit ${unit2.evaluate()} and ${unit3.evaluate()}")

        PlusUnit(unit2, unit3)
    }
}
