import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.lucciola.formulaparser.UnitParser

@RunWith(JUnit4::class)
class UnitParserTest {

    @Test
    fun parsePrefixTest() {
        assertThat(UnitParser.parsePrefix("m")).isEqualTo(arrayOf("0", "m"))
        assertThat(UnitParser.parsePrefix("k_m")).isEqualTo(arrayOf("3", "m"))
    }

    @Test
    fun createUnitMapTest() {
        assertThat(UnitParser.createUnitMap("m^2")).isEqualTo(mapOf("m" to "2"))
    }
}