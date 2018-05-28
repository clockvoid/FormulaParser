package FormulaParser;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalPlus {
	
	//////////////////////////////////////////////////////////////////////////
	// �t�B�[���h
	//////////////////////////////////////////////////////////////////////////

	public static int ROUND_CEILING = BigDecimal.ROUND_CEILING;
	public static int ROUND_DOWN = BigDecimal.ROUND_DOWN;
	public static int ROUND_FLOOR = BigDecimal.ROUND_FLOOR;
	public static int ROUND_HALF_DOWN = BigDecimal.ROUND_HALF_DOWN;
	public static int ROUND_HALF_EVEN = BigDecimal.ROUND_HALF_EVEN;
	public static int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;
	public static int ROUND_UNNECESSARY = BigDecimal.ROUND_UNNECESSARY;
	public static int ROUND_UP = BigDecimal.ROUND_UP;
	
	// �f�t�H���g�̊ۂ߃��[�h
	private static int DEFAULT_SIGN_ROUNDING_MODE = ROUND_DOWN;
	
	// ���ۂ̒l
	private BigDecimal actual;
	// �L������
	private int significant;
	// �L�������K�p���̊ۂ߃��[�h
	private int signRoundingMode;
	
	//////////////////////////////////////////////////////////////////////////
	// �R���X�g���N�^
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
		
		// ���ۂ̒l�ɗL��������K�p����B
		this.actual = applySignificant( new BigDecimal( val ) );
	}

	public BigDecimalPlus(String val, int significant, int signRoundingMode) {
		this.significant = significant;
		this.signRoundingMode = signRoundingMode;
		
		// ���ۂ̒l�ɗL��������K�p����B
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
		
		// ���ۂ̒l�ɗL��������K�p����B
		this.actual = applySignificant( actual );
	}
	
	//////////////////////////////////////////////////////////////////////////
	// �L�������̓K�p�̂��߂̃��\�b�h
	//////////////////////////////////////////////////////////////////////////

	// BigDecimal�ɗL��������K�p����B
	private BigDecimal applySignificant( BigDecimal val ) {
		
		// �L��������1�ȏ�ł��邱�Ƃ��m�F����B
		if ( significant <= 0 ) {
			// 0�ȉ��ł���:
			return val;
		}
		
		// �������̌������擾����B
		int intLength = getIntLength( val );
		// �L�������Ɛ������̌����̍������߂�B
		int delta = intLength - significant;
		// �L�����������������傫�����Ƃ��m�F����B
		if ( delta > 0 ) {
			// 10��delta������߂�B
			BigDecimal tenPower = new BigDecimal( new BigInteger( "10" ).pow( delta ) );
			// ���������擾����B
			BigDecimal unscaledValue = new BigDecimal( val.unscaledValue() );
			// �������̗L�������ȉ���؂�̂Ă�B
			// �� ������ / 10��delta�� * 10��delta��
			BigDecimal bak = unscaledValue.divide( tenPower, 0, signRoundingMode ).multiply( tenPower );

			return new BigDecimal( bak.unscaledValue(), val.scale() );
		} else {
			return val;
		}
	}
	
	// �������̌������擾����B
	private static int getIntLength( BigDecimal val ) {
		// ���������擾����B
		BigInteger intVal =  val.unscaledValue();
		// �������̕������Ȃ����B
		intVal = intVal.abs();
		// �������𕶎���ɂ���B
		String bak = intVal.toString();
		// ������̒�����Ԃ��B
		return bak.length();
	}
	
	//////////////////////////////////////////////////////////////////////////
	// �L�������̐ݒ�E�擾�̂��߂̃��\�b�h
	//////////////////////////////////////////////////////////////////////////

	/**
	 * �w�肵���L������������BigDecimalPlus�̃C���X�^���X��Ԃ��B
	 * �Ȃ��A�C���X�^���X�̒l�ƗL�������K�p���̊ۂ߃��[�h�́A���݂̃C���X�^���X�̒l�Ɠ����B
	 * @param significant �L������
	 * @reutrn �V�����C���X�^���X
	 * @throws RuntimeException �L�����������̏ꍇ
	 */
	public BigDecimalPlus setSignificant( int significant ) {
		if ( significant < 0 ) {
			// 0�����ł���:
			throw new RuntimeException();
		}
		
		// �w�肳�ꂽ�L�����������C���X�^���X�����B
		// �Ȃ��A�L�������K�p���̊ۂ߃��[�h�͌��݂̃C���X�^���X�Ɠ����l����������B
		int signRoundingMode = this.signRoundingMode;
		return new BigDecimalPlus( actual, significant, signRoundingMode );
	}
	
	public int significant() {
		return significant;
	}

	/**
	 * �w�肵���L�������K�p���̊ۂ߃��[�h������BigDecimalPlus�̃C���X�^���X��Ԃ��B
	 * �Ȃ��A�C���X�^���X�̒l�ƗL�������́A���݂̃C���X�^���X�̒l�Ɠ����B
	 * @param signRoundingMode �L�������K�p���̊ۂ߃��[�h
	 * @reutrn �V�����C���X�^���X
	 * @throws RuntimeException �L�����������̏ꍇ
	 */
	public BigDecimalPlus setSignificantRoundingMode( int signRoundingMode ) {
		// �L��������0�ȏ�ł��邱�Ƃ��m�F����B
		if ( significant < 0 ) {
			// 0�����ł���:
			throw new RuntimeException();
		}

		// �w�肳�ꂽ�L�������K�p���̊ۂ߃��[�h�����C���X�^���X�����B
		// �Ȃ��A�L�������͌��݂̃C���X�^���X�Ɠ����l����������B
		int significant = this.significant;
		return new BigDecimalPlus( actual, significant, signRoundingMode );
	}
	
	public int significantRoundingMode() {
		return signRoundingMode;
	}
	
	//////////////////////////////////////////////////////////////////////////
	// BigDecimalPlus�C���X�^���X�����p�̕⏕���\�b�h
	//////////////////////////////////////////////////////////////////////////

	// �V�����C���X�^���X�𐶐�����B
	// �V�����C���X�^���X�ɂ́A���݂̃C���X�^���X�Ɠ����L�������ƗL�������K�p���̊ۂ߃��[�h����������B
	private BigDecimalPlus newInstance( BigDecimal actual ) {
		return new BigDecimalPlus( actual, significant, signRoundingMode );
	}

	
	//////////////////////////////////////////////////////////////////////////
	// BigDecimal�Ɠ��l�̃��\�b�h
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
		
		// �ȉ������ׂĈ�v���邱�Ƃ��m�F����B
		// �E���ۂ̒l
		// �E�L������
		// �E�L�������K�p���̊ۂ߃��[�h
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