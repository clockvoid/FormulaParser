package FormulaParser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class UnitParser {
	
	// Map‚ÅÚ“ª«‚ğ’è‹`‚µ‚Ä,ŠÈ’P‚ÉŒvZ‚Å‚«‚é‚æ‚¤‚É‚·‚é
	@SuppressWarnings("serial")
	private static final Map<String, String> prefix = new HashMap<String, String>() {
		{
			put("T", "12");
			put("G", "9");
			put("M", "6");
			put("k", "3");
			put("h", "2");
			put("da", "1");
			put("d", "-1");
			put("c", "-2");
			put("m", "-3");
			put("ƒÊ", "-6");
			put("n", "-9");
			put("p", "-12");
		}
	};
	
	private static String[] parsePrefix(String arg0) {
		String[] unit = arg0.split("_");
		if (unit.length == 1) {
			String[] ans = {"0", arg0};
			return ans;
		} else {
			String prefixString = unit[0];
			String[] array = unit[1].split("\\^");
			if (array.length == 1) {
				prefixString = prefix.get(prefixString);
			} else {
				prefixString = new BigDecimal(prefix.get(prefixString)).multiply(new BigDecimal(array[1])).toString();
			}
			String[] ans = {prefixString, unit[1]};
			return ans;
		}
	}
	
	public static List<String> evaluatePrefix(String arg0, String arg1) {
		String[] unitList = arg1.split("\\.");
		String number = "0";
		String unit = "";
		for (String str : unitList) {
			String[] array = parsePrefix(str);
			number = new BigDecimal(number).add(new BigDecimal(array[0])).toString();
			unit += (unit.length() != 0 ? "." : "") + array[1];
		}
		ArrayList<String> ans = new ArrayList<String>();
		ans.add(new BigDecimal(arg0).multiply(new BigDecimal(Double.toString(Math.pow(10, Double.parseDouble(number))))).toString());
		ans.add(unit);
		return ans;
	}
	
	public static Map<String, String> createUnitMap(String arg0) {
		String[] devidedUnit = arg0.split("\\.");
		Map<String, String> unitMap = new HashMap<String, String>();
		for (String str : devidedUnit) {
			String[] array = str.split("\\^");
			if (array.length == 1) {
				unitMap.put(array[0], "1");
			} else {
				unitMap.put(array[0], array[1]);
			}
		}
		return unitMap;
	}
	
	public static String createUnitString(Map<String, String> arg0) {
		String unitString = "";
		for (Map.Entry<String, String> set : arg0.entrySet()) {
			unitString += set.getKey() + (set.getValue().equals("1") ? "" : "^" + (set.getKey().equals("void") ? "0" : set.getValue())) + ".";
		}
		return unitString.replaceAll("\\.$", "");
	}
	
}
