package FormulaParser;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalPlus {
	
	//////////////////////////////////////////////////////////////////////////
	// フィールド
	//////////////////////////////////////////////////////////////////////////

	public static int ROUND_CEILING = BigDecimal.ROUND_CEILING;
	public static int ROUND_DOWN = BigDecimal.ROUND_DOWN;
	public static int ROUND_FLOOR = BigDecimal.ROUND_FLOOR;
	public static int ROUND_HALF_DOWN = BigDecimal.ROUND_HALF_DOWN;
	public static int ROUND_HALF_EVEN = BigDecimal.ROUND_HALF_EVEN;
	public static int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;
	public static int ROUND_UNNECESSARY = BigDecimal.ROUND_UNNECESSARY;
	public static int ROUND_UP = BigDecimal.ROUND_UP;
	
	// デフォルトの丸めモード
	private static int DEFAULT_SIGN_ROUNDING_MODE = ROUND_DOWN;
	
	// 実際の値
	private BigDecimal actual;
	// 有効桁数
	private int significant;
	// 有効桁数適用時の丸めモード
	private int signRoundingMode;
	
	//////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	//////////////////////////////////////////////////////////////////////////

	public BigDecimalPlus(BigInteger val) {
		this.significant = 0;
		this.signRoundingMode = DEFAULT_SIGN_ROUNDING_MODE;

		actual = new BigDecimal( val );
	}

	public BigDecimalPlus(BigInteger unscaledVal, int scale) {
		this.significant = 0;
		this.signRoundingMode = DEFAULT_SIGN_ROUNDING_MODE;

		actual = new BigDecimal( unscaledVal, scale );
	}

	public BigDecimalPlus(double val) {
		this.significant = 0;
		this.signRoundingMode = DEFAULT_SIGN_ROUNDING_MODE;
		
		actual = new BigDecimal( val );
	}

	public BigDecimalPlus(String val) {
		this.significant = 0;
		this.signRoundingMode = DEFAULT_SIGN_ROUNDING_MODE;
		
		actual = new BigDecimal( val );
	}
	
	public BigDecimalPlus(String val, int significant) {
		this.significant = significant;
		this.signRoundingMode = DEFAULT_SIGN_ROUNDING_MODE;
		
		// 実際の値に有効桁数を適用する。
		this.actual = applySignificant( new BigDecimal( val ) );
	}

	public BigDecimalPlus(String val, int significant, int signRoundingMode) {
		this.significant = significant;
		this.signRoundingMode = signRoundingMode;
		
		// 実際の値に有効桁数を適用する。
		this.actual = applySignificant( new BigDecimal( val ) );
	}

	public BigDecimalPlus( BigDecimal actual ) {
		this.significant = 0;
		this.signRoundingMode = DEFAULT_SIGN_ROUNDING_MODE;
		
		this.actual = actual;
	}
	
	public BigDecimalPlus( BigDecimal actual, int significant, int signRoundingMode ) {
		this.significant = significant;
		this.signRoundingMode = signRoundingMode;
		
		// 実際の値に有効桁数を適用する。
		this.actual = applySignificant( actual );
	}
	
	//////////////////////////////////////////////////////////////////////////
	// 有効桁数の適用のためのメソッド
	//////////////////////////////////////////////////////////////////////////

	// BigDecimalに有効桁数を適用する。
	private BigDecimal applySignificant( BigDecimal val ) {
		
		// 有効桁数が1以上であることを確認する。
		if ( significant <= 0 ) {
			// 0以下である:
			return val;
		}
		
		// 整数部の桁数を取得する。
		int intLength = getIntLength( val );
		// 有効桁数と整数部の桁数の差を求める。
		int delta = intLength - significant;
		// 有効桁数が整数部より大きいことを確認する。
		if ( delta > 0 ) {
			// 10のdelta乗を求める。
			BigDecimal tenPower = new BigDecimal( new BigInteger( "10" ).pow( delta ) );
			// 整数部を取得する。
			BigDecimal unscaledValue = new BigDecimal( val.unscaledValue() );
			// 整数部の有効桁数以下を切り捨てる。
			// ※ 整数部 / 10のdelta乗 * 10のdelta乗
			BigDecimal bak = unscaledValue.divide( tenPower, 0, signRoundingMode ).multiply( tenPower );

			return new BigDecimal( bak.unscaledValue(), val.scale() );
		} else {
			return val;
		}
	}
	
	// 整数部の桁数を取得する。
	private static int getIntLength( BigDecimal val ) {
		// 整数部を取得する。
		BigInteger intVal =  val.unscaledValue();
		// 整数部の符号をなくす。
		intVal = intVal.abs();
		// 整数部を文字列にする。
		String bak = intVal.toString();
		// 文字列の長さを返す。
		return bak.length();
	}
	
	//////////////////////////////////////////////////////////////////////////
	// 有効桁数の設定・取得のためのメソッド
	//////////////////////////////////////////////////////////////////////////

	/**
	 * 指定した有効桁数を持つBigDecimalPlusのインスタンスを返す。
	 * なお、インスタンスの値と有効桁数適用時の丸めモードは、現在のインスタンスの値と同じ。
	 * @param significant 有効桁数
	 * @reutrn 新しいインスタンス
	 * @throws RuntimeException 有効桁数が負の場合
	 */
	public BigDecimalPlus setSignificant( int significant ) {
		if ( significant < 0 ) {
			// 0未満である:
			throw new RuntimeException();
		}
		
		// 指定された有効桁数を持つインスタンスを作る。
		// なお、有効桁数適用時の丸めモードは現在のインスタンスと同じ値を持たせる。
		int signRoundingMode = this.signRoundingMode;
		return new BigDecimalPlus( actual, significant, signRoundingMode );
	}
	
	public int significant() {
		return significant;
	}

	/**
	 * 指定した有効桁数適用時の丸めモードを持つBigDecimalPlusのインスタンスを返す。
	 * なお、インスタンスの値と有効桁数は、現在のインスタンスの値と同じ。
	 * @param signRoundingMode 有効桁数適用時の丸めモード
	 * @reutrn 新しいインスタンス
	 * @throws RuntimeException 有効桁数が負の場合
	 */
	public BigDecimalPlus setSignificantRoundingMode( int signRoundingMode ) {
		// 有効桁数が0以上であることを確認する。
		if ( significant < 0 ) {
			// 0未満である:
			throw new RuntimeException();
		}

		// 指定された有効桁数適用時の丸めモードを持つインスタンスを作る。
		// なお、有効桁数は現在のインスタンスと同じ値を持たせる。
		int significant = this.significant;
		return new BigDecimalPlus( actual, significant, signRoundingMode );
	}
	
	public int significantRoundingMode() {
		return signRoundingMode;
	}
	
	//////////////////////////////////////////////////////////////////////////
	// BigDecimalPlusインスタンス生成用の補助メソッド
	//////////////////////////////////////////////////////////////////////////

	// 新しいインスタンスを生成する。
	// 新しいインスタンスには、現在のインスタンスと同じ有効桁数と有効桁数適用時の丸めモードを持たせる。
	private BigDecimalPlus newInstance( BigDecimal actual ) {
		return new BigDecimalPlus( actual, significant, signRoundingMode );
	}

	
	//////////////////////////////////////////////////////////////////////////
	// BigDecimalと同様のメソッド
	//////////////////////////////////////////////////////////////////////////

	public BigDecimalPlus abs() {
		BigDecimal bak = actual.abs();
		return newInstance( bak );
	}

	public BigDecimalPlus add(BigDecimalPlus val) {
		BigDecimal bak = actual.add( val.actual );
		return newInstance( bak );
	}

	public int compareTo(BigDecimalPlus val) {
		return actual.compareTo( val.actual );
	}

	public int compareTo(Object o) {
		BigDecimalPlus val = (BigDecimalPlus) o;
		return actual.compareTo( val.actual );
	}

	public BigDecimalPlus divide(BigDecimalPlus val, int roundingMode) {
		BigDecimal bak = actual.divide( val.actual, roundingMode );
		return newInstance( bak );
	}

	public BigDecimalPlus divide(BigDecimalPlus val, int scale, int roundingMode) {
		BigDecimal bak = actual.divide( val.actual, scale, roundingMode );
		return newInstance( bak );
	}

	public double doubleValue() {
		return actual.doubleValue();
	}

	public boolean equals(Object x) {
		BigDecimalPlus val = (BigDecimalPlus) x;
		
		// 以下がすべて一致することを確認する。
		// ・実際の値
		// ・有効桁数
		// ・有効桁数適用時の丸めモード
		if ( actual.equals( val.actual )
			&& significant == val.significant
			&& signRoundingMode == val.signRoundingMode ) {
			
			return true;
		} else {
			return false;
		}
	}

	public float floatValue() {
		return actual.floatValue();
	}

	public int hashCode() {
		return actual.hashCode();
	}

	public int intValue() {
		return actual.intValue();
	}

	public long longValue() {
		return actual.longValue();
	}

	public BigDecimalPlus max(BigDecimalPlus val) {
		BigDecimal bak = actual.max( val.actual );
		return newInstance( bak );
	}

	public BigDecimalPlus min(BigDecimalPlus val) {
		BigDecimal bak = actual.min( val.actual );
		return newInstance( bak );
	}

	public BigDecimalPlus movePointLeft(int n) {
		BigDecimal bak = actual.movePointLeft( n );
		return newInstance( bak );
	}

	public BigDecimalPlus movePointRight(int n) {
		BigDecimal bak = actual.movePointRight( n );
		return newInstance( bak );
	}

	public BigDecimalPlus multiply(BigDecimalPlus val) {
		BigDecimal bak = actual.multiply( val.actual );
		return newInstance( bak );
	}

	public BigDecimalPlus negate() {
		BigDecimal bak = actual.negate();
		return newInstance( bak );
	}

	public int scale() {
		return actual.scale();
	}

	public BigDecimalPlus setScale(int scale) {
		BigDecimal bak = actual.setScale( scale );
		return newInstance( bak );
	}

	public BigDecimalPlus setScale(int scale, int roundingMode) {
		BigDecimal bak = actual.setScale( scale, roundingMode );
		return newInstance( bak );
	}

	public int signum() {
		return actual.signum();
	}

	public BigDecimalPlus subtract(BigDecimalPlus val) {
		BigDecimal bak = actual.subtract( val.actual );
		return newInstance( bak );
	}

	public BigInteger toBigInteger() {
		return actual.toBigInteger();
	}

	public String toString() {
		return actual.toString();
	}

	public BigInteger unscaledValue() {
		return actual.unscaledValue();
	}
	
	public byte byteValue() {
		return actual.byteValue();
	}
	
	public short shortValue() {
		return actual.shortValue();
	}

	public static BigDecimalPlus valueOf(long val) {
		BigDecimal bak = BigDecimal.valueOf( val );
		return new BigDecimalPlus( bak );
	}

	public static BigDecimalPlus valueOf(long unscaledVal, int scale) {
		BigDecimal bak = BigDecimal.valueOf( unscaledVal, scale );
		return new BigDecimalPlus( bak );
	}

}