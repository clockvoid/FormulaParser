import com.lucciola.factor.SqrtNumber
import com.lucciola.factor.SqrtUnit
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.assertj.core.api.Assertions.assertThat
import com.lucciola.termination.Number
import com.lucciola.termination.Unit

@RunWith(JUnit4::class)
class SqrtTest {

    @Test
    fun sqrtNumberTest() {
        val number = SqrtNumber(Number("4.0"))
        assertThat(number.evaluate()).isEqualTo("2.0")
    }

    @Test
    fun sqrtUnitTest() {
        val unit = SqrtUnit(Unit("m^2"))
        assertThat(unit.evaluate()).isEqualTo("m")
    }

}
