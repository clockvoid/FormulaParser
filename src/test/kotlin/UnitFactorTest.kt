import com.lucciola.factor.UnitFactor
import org.junit.Test
import com.lucciola.termination.Unit
import org.assertj.core.api.Assertions.assertThat
import com.lucciola.exception.RuntimeErrorException

class UnitFactorTest {
    @Test
    fun testDefaultEvaluate() {
        val unitFactor = UnitFactor(Unit("void^0"), Unit("m"))
        assertThat(unitFactor.evaluate()).isEqualTo("m")
    }

    @Test
    fun testException() {
        val unitFactor = UnitFactor(Unit("m"), Unit("m"))

        try {
            unitFactor.evaluate()
        } catch (e: RuntimeErrorException) {
            assertThat(e.message).isEqualTo("A unit that is in brackets must be void.")
        }
    }
}
