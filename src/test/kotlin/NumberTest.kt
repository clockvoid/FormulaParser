import com.lucciola.exception.SyntaxErrorException
import com.lucciola.termination.Number
import com.lucciola.termination.Unit
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NumberTest {

    @Test
    fun evaluationTest() {
        val num = "10"

        assertThat(Number(num).evaluate()).isEqualTo(num)
    }

    @Test
    fun constructExceptionTest() {
        val num = "hello"

        try {
            Number(num)
        } catch (e: SyntaxErrorException) {
            assertThat(e.message).isEqualTo("Value \"$num\" is not Numeric")
        }
        try {
            Number(Unit(num))
        } catch (e: SyntaxErrorException) {
            assertThat(e.message).isEqualTo("Value \"$num\" is not Numeric")
        }
    }

}