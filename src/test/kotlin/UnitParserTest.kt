import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.lucciola.formulaparser.UnitParser
import com.lucciola.formulaparser.UnitParser.Companion.evaluatePrefix

@RunWith(JUnit4::class)
class UnitParserTest {

    @Test
    fun parsePrefixTest() {
        assertThat(UnitParser.parsePrefix("m")).isEqualTo(arrayOf("0", "m"))
        assertThat(UnitParser.parsePrefix("k_m")).isEqualTo(arrayOf("3", "m"))
        assertThat(UnitParser.parsePrefix("T_m")).isEqualTo(arrayOf("12", "m"))
        assertThat(UnitParser.parsePrefix("G_m")).isEqualTo(arrayOf("9", "m"))
        assertThat(UnitParser.parsePrefix("h_m")).isEqualTo(arrayOf("2", "m"))
        assertThat(UnitParser.parsePrefix("da_m")).isEqualTo(arrayOf("1", "m"))
        assertThat(UnitParser.parsePrefix("d_m")).isEqualTo(arrayOf("-1", "m"))
        assertThat(UnitParser.parsePrefix("c_m")).isEqualTo(arrayOf("-2", "m"))
        assertThat(UnitParser.parsePrefix("m_m")).isEqualTo(arrayOf("-3", "m"))
        assertThat(UnitParser.parsePrefix("u_m")).isEqualTo(arrayOf("-6", "m"))
        assertThat(UnitParser.parsePrefix("n_m")).isEqualTo(arrayOf("-9", "m"))
        assertThat(UnitParser.parsePrefix("p_m")).isEqualTo(arrayOf("-12", "m"))
    }

    @Test
    fun createUnitMapTest() {
        assertThat(UnitParser.createUnitMap("m^2")).isEqualTo(mapOf("m" to "2"))
        assertThat(UnitParser.createUnitMap("sec^2")).isEqualTo(mapOf("sec" to "2"))
        assertThat(UnitParser.createUnitMap("m")).isEqualTo(mapOf("m" to "1"))
    }

    @Test
    fun createUnitStringTest() {
        assertThat(UnitParser.createUnitString(hashMapOf("m" to "2"))).isEqualTo("m^2")
    }

    @Test
    fun evaluatePrefixTest() {
        println(evaluatePrefix("10", "m^2'sec"))
    }

}