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

	//////////////////////////////////////////////////////////////////////////
	// �ȉ��A���ؗp�R�[�h(�폜���Ă����v)
	//////////////////////////////////////////////////////////////////////////

	public static void main( String[] args ) {
		
		System.out.println( "[�m�F�|�C���g1] �e�R���X�g���N�^�Ő������L���������K�p����邱�ƁB" );

		print( "BigDecimalPlus(BigInteger val)",
			new BigDecimalPlus( new BigInteger( "1234" ) ),
			new BigDecimal( new BigInteger( "1234" ) ) ); 

		print( "BigDecimalPlus(BigInteger unscaledVal, int scale)",
			new BigDecimalPlus( new BigInteger( "1234" ), 3 ),
			new BigDecimal( new BigInteger( "1234" ), 3 ) ); 

		print( "BigDecimalPlus(double val)",
			new BigDecimalPlus( 12.34 ),
			new BigDecimal( 12.34 ) ); 

		print( "BigDecimalPlus(String val)",
			new BigDecimalPlus( "0.1234" ),
			new BigDecimal( "0.1234" ) ); 

		print( "BigDecimalPlus(String val, int significant)",
			new BigDecimalPlus( "1234", 2 ),
			new BigDecimal( "1234" ) ); 

		print( "BigDecimalPlus(String val, int significant, int signRoundingMode)",
			new BigDecimalPlus( "1234", 2, BigDecimal.ROUND_UP ),
			new BigDecimal( "1234" ) ); 

		print( "BigDecimalPlus( BigDecimal actual )",
			new BigDecimalPlus( new BigDecimal( "1234" ) ),
			new BigDecimal( "1234" ) ); 

		print( "BigDecimalPlus( BigDecimal actual, int significant, int signRoundingMode )",
			new BigDecimalPlus( new BigDecimal( "1234" ), 3, BigDecimal.ROUND_CEILING ),
			new BigDecimal( "1234" ) ); 

		System.out.println();
		
		System.out.println( "[�m�F�|�C���g2] BigDecimalPlus��Ԃ����\�b�h�̖߂�l�ŁA �L�������ƗL�������K�p���̊ۂ߃��[�h�������p����Ă��邱�ƁB" );
	
		BigDecimalPlus bdp1 = new BigDecimalPlus( "-1.234", 3, BigDecimal.ROUND_CEILING );
		BigDecimal bd1 = new BigDecimal( "-1.234" );
		print( "bdp1",
			bdp1,
			bd1 );

		BigDecimalPlus bdp2 = new BigDecimalPlus( "789", 2, BigDecimal.ROUND_UP );
		BigDecimal bd2 = new BigDecimal( "789" );
		print( "bdp2",
			bdp2,
			bd2 );
		System.out.println();

		print( "bdp1.abs()",
			bdp1.abs(),
			bd1.abs() );
		
		print( "bdp1.add( bdp2 )",
			bdp1.add( bdp2 ),
			bd1.add( bd2 ) );
		
		print( "bdp1.divide( bdp2, BigDecimal.ROUND_DOWN )",
			bdp1.divide( bdp2, BigDecimal.ROUND_DOWN ),
			bd1.divide( bd2, BigDecimal.ROUND_DOWN ) );
		
		print( "bdp1.divide( bdp2, 7, BigDecimal.ROUND_UP )",
			bdp1.divide( bdp2, 7, BigDecimal.ROUND_UP ),
			bd1.divide( bd2, 7, BigDecimal.ROUND_UP ) );
		
		print( "bdp1.max( bdp2 )",
			bdp1.max( bdp2 ),
			bd1.max( bd2 ) );
		
		print( "bdp1.min( bdp2 )",
			bdp1.min( bdp2 ),
			bd1.min( bd2 ) );
		
		print( "bdp1.movePointLeft( 2 )",
			bdp1.movePointLeft( 2 ),
			bd1.movePointLeft( 2 ) );
		
		print( "bdp1.movePointRight( 2 )",
			bdp1.movePointRight( 2 ),
			bd1.movePointRight( 2 ) );
		
		print( "bdp1.multiply( bdp2 )",
			bdp1.multiply( bdp2 ),
			bd1.multiply( bd2 ) );
		
		print( "bdp1.negate()",
			bdp1.negate(),
			bd1.negate() );
		
		print( "bdp1.setScale( 4 )",
			bdp1.setScale( 4 ),
			bd1.setScale( 4 ) );
		
		print( "bdp1.setScale( 1, BigDecimal.ROUND_FLOOR )",
			bdp1.setScale( 1, BigDecimal.ROUND_FLOOR  ),
			bd1.setScale( 1, BigDecimal.ROUND_FLOOR ) );
		
		print( "bdp1.subtract( bdp2 )",
			bdp1.subtract( bdp2 ),
			bd1.subtract( bd2 ) );
		
		System.out.println();
		
		System.out.println( "[�m�F�|�C���g3] equal���\�b�h�̓���" );
		
		BigDecimal b1 = new BigDecimal( "124" );
		BigDecimal b2 = new BigDecimal( "7.38" );
		int s1 = 2;
		int s2 = 3;
		int r1 = BigDecimal.ROUND_UP;
		int r2 = BigDecimal.ROUND_DOWN;
		print2( "new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b1, s1, r1 ) )",
			new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b1, s1, r1 ) ) );
		print2( "new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b1, s1, r2 ) )",
			new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b1, s1, r2 ) ) );
		print2( "new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b1, s2, r1 ) )",
			new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b1, s2, r1 ) ) );
		print2( "new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b2, s1, r1 ) )",
			new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b2, s1, r1 ) ) );
		print2( "new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b1, s2, r2 ) )",
			new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b1, s2, r2 ) ) );
		print2( "new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b2, s1, r2 ) )",
			new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b2, s1, r2 ) ) );
		print2( "new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b2, s2, r1 ) )",
			new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b2, s2, r1 ) ) );
		print2( "new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b2, s2, r2 ) )",
			new BigDecimalPlus( b1, s1, r1 ).equals( new BigDecimalPlus( b2, s2, r2 ) ) );
		
		System.out.println();
	}
		
	private static void print( String msg, BigDecimalPlus bdp, BigDecimal bd ) {
		String significantStr;
		if ( bdp.significant() > 0 ) {
			significantStr = "�L������ " + bdp.significant() + "��";
		} else {
			significantStr = "�L�������w��Ȃ�";
		}
		
		String roundingModeStr = roundingModeToString( bdp.significantRoundingMode() );
		
		System.out.println( msg + ": " );
		System.out.println( " " + significantStr + " " + roundingModeStr );
		System.out.println( " (BigDecimalPlus) " + bdp.toString() );
		System.out.println( " (BigDecimal)     " + bd.toString() );
		System.out.println();
	}
	
	private static void print2( String msg, boolean result ) {
		System.out.println( msg + ":" );
		System.out.println( " " + result );
		System.out.println();
	}

	private static String roundingModeToString( int roundingMode ) {
		switch ( roundingMode ){
			case BigDecimal.ROUND_CEILING:
				return "ROUND_CEILING";
			case BigDecimal.ROUND_DOWN:
				return "ROUND_DOWN";
			case BigDecimal.ROUND_FLOOR:
				return "ROUND_FLOOR";
			case BigDecimal.ROUND_HALF_DOWN:
				return "ROUND_HALF_DOWN";
			case BigDecimal.ROUND_HALF_EVEN:
				return "ROUND_HALF_EVEN";
			case BigDecimal.ROUND_HALF_UP:
				return "ROUND_HALF_UP";
			case BigDecimal.ROUND_UNNECESSARY:
				return "ROUND_UNNECESSARY";
			case BigDecimal.ROUND_UP:
				return "ROUND_UP";
			default:
				return "�s���Ȋۂ߃��[�h";
		}
	}
}