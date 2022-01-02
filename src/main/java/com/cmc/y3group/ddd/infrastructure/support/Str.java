package com.cmc.y3group.ddd.infrastructure.support;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * String support
 */
public final class Str {
	private static HashMap<String, String[]> charsArray;

	private static final Object object = new Object();

	private static final char[] ALPHANUMERIC_UPPER = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final char[] ALPHANUMERIC_LOWER = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

	public static String random(int length) {
		return RandomStringUtils.random(length, 0, 0, true, true, null, new SecureRandom());
	}

	public static String randomLower(int length) {
		return RandomStringUtils.random(length, ALPHANUMERIC_LOWER);
	}

	public static String randomAscii(int length) {
		return RandomStringUtils.randomAscii(length);
	}

	public static String randomAlphabetic(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}

	public static String randomNumeric(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	public static String randomAlphanumeric(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	public static String numberToBase36(Number i) {

		long quot = i.longValue() / 36;
		long rem = i.longValue() % 36;

		char letter = ALPHANUMERIC_UPPER[(int) rem];
		if (quot == 0) return "" + letter;
		else return numberToBase36(quot - 1) + letter;
	}

	/**
	 * @param title Title.
	 */
	public static String ascii(String title) {
		HashMap<String, String[]> map = charsArray();

		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			for (String value : values)
				title = title.replaceAll(value, key);
		}
		return title.replaceAll("[^\\x20-\\x7E]", "");
	}

	/**
	 * @param title     Title.
	 * @param separator Separator.
	 */
	public static String slug(String title, String separator) {
		title = ascii(title);
		title = title.replaceAll("\\_", separator);
		title = title.replaceAll("@", separator + "at" + separator);
		title = title.toLowerCase();
		title = title.replaceAll("[^\\-\\pL\\pN\\s]+", separator);
		title = title.replaceAll("[\\-\\s]+", separator);
		title = title.replaceAll("^-|-$", "");
		return title;
	}

	private static HashMap<String, String[]> charsArray() {
		if (Objects.isNull(charsArray)) {
			synchronized (object) {
				if (Objects.isNull(charsArray)) {
					charsArray = new HashMap<>();
					charsArray.put("0", new String[]{"°", "₀", "۰", "０"});
					charsArray.put("1", new String[]{"¹", "₁", "۱", "１"});
					charsArray.put("2", new String[]{"²", "₂", "۲", "２"});
					charsArray.put("3", new String[]{"³", "₃", "۳", "３"});
					charsArray.put("4", new String[]{"⁴", "₄", "۴", "٤", "４"});
					charsArray.put("5", new String[]{"⁵", "₅", "۵", "٥", "５"});
					charsArray.put("6", new String[]{"⁶", "₆", "۶", "٦", "６"});
					charsArray.put("7", new String[]{"⁷", "₇", "۷", "７"});
					charsArray.put("8", new String[]{"⁸", "₈", "۸", "８"});
					charsArray.put("9", new String[]{"⁹", "₉", "۹", "９"});
					charsArray.put("a", new String[]{"à", "á", "ả", "ã", "ạ", "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ẩ", "ẫ", "ậ", "ā", "ą", "å", "α", "ά", "ἀ", "ἁ", "ἂ", "ἃ", "ἄ", "ἅ", "ἆ", "ἇ", "ᾀ", "ᾁ", "ᾂ", "ᾃ", "ᾄ", "ᾅ", "ᾆ", "ᾇ", "ὰ", "ά", "ᾰ", "ᾱ", "ᾲ", "ᾳ", "ᾴ", "ᾶ", "ᾷ", "а", "أ", "အ", "ာ", "ါ", "ǻ", "ǎ", "ª", "ა", "अ", "ا", "ａ", "ä"});
					charsArray.put("b", new String[]{"б", "β", "ب", "ဗ", "ბ", "ｂ"});
					charsArray.put("c", new String[]{"ç", "ć", "č", "ĉ", "ċ", "ｃ"});
					charsArray.put("d", new String[]{"ď", "ð", "đ", "ƌ", "ȡ", "ɖ", "ɗ", "ᵭ", "ᶁ", "ᶑ", "д", "δ", "د", "ض", "ဍ", "ဒ", "დ", "ｄ"});
					charsArray.put("e", new String[]{"é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ", "ë", "ē", "ę", "ě", "ĕ", "ė", "ε", "έ", "ἐ", "ἑ", "ἒ", "ἓ", "ἔ", "ἕ", "ὲ", "έ", "е", "ё", "э", "є", "ə", "ဧ", "ေ", "ဲ", "ე", "ए", "إ", "ئ", "ｅ"});
					charsArray.put("f", new String[]{"ф", "φ", "ف", "ƒ", "ფ", "ｆ"});
					charsArray.put("g", new String[]{"ĝ", "ğ", "ġ", "ģ", "г", "ґ", "γ", "ဂ", "გ", "گ", "ｇ"});
					charsArray.put("h", new String[]{"ĥ", "ħ", "η", "ή", "ح", "ه", "ဟ", "ှ", "ჰ", "ｈ"});
					charsArray.put("i", new String[]{"í", "ì", "ỉ", "ĩ", "ị", "î", "ï", "ī", "ĭ", "į", "ı", "ι", "ί", "ϊ", "ΐ", "ἰ", "ἱ", "ἲ", "ἳ", "ἴ", "ἵ", "ἶ", "ἷ", "ὶ", "ί", "ῐ", "ῑ", "ῒ", "ΐ", "ῖ", "ῗ", "і", "ї", "и", "ဣ", "ိ", "ီ", "ည်", "ǐ", "ი", "इ", "ی", "ｉ"});
					charsArray.put("j", new String[]{"ĵ", "ј", "Ј", "ჯ", "ج", "ｊ"});
					charsArray.put("k", new String[]{"ķ", "ĸ", "к", "κ", "Ķ", "ق", "ك", "က", "კ", "ქ", "ک", "ｋ"});
					charsArray.put("l", new String[]{"ł", "ľ", "ĺ", "ļ", "ŀ", "л", "λ", "ل", "လ", "ლ", "ｌ"});
					charsArray.put("m", new String[]{"м", "μ", "م", "မ", "მ", "ｍ"});
					charsArray.put("n", new String[]{"ñ", "ń", "ň", "ņ", "ŉ", "ŋ", "ν", "н", "ن", "န", "ნ", "ｎ"});
					charsArray.put("o", new String[]{"ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ", "ø", "ō", "ő", "ŏ", "ο", "ὀ", "ὁ", "ὂ", "ὃ", "ὄ", "ὅ", "ὸ", "ό", "о", "و", "ို", "ǒ", "ǿ", "º", "ო", "ओ", "ｏ", "ö"});
					charsArray.put("p", new String[]{"п", "π", "ပ", "პ", "پ", "ｐ"});
					charsArray.put("q", new String[]{"ყ", "ｑ"});
					charsArray.put("r", new String[]{"ŕ", "ř", "ŗ", "р", "ρ", "ر", "რ", "ｒ"});
					charsArray.put("s", new String[]{"ś", "š", "ş", "с", "σ", "ș", "ς", "س", "ص", "စ", "ſ", "ს", "ｓ"});
					charsArray.put("t", new String[]{"ť", "ţ", "т", "τ", "ț", "ت", "ط", "ဋ", "တ", "ŧ", "თ", "ტ", "ｔ"});
					charsArray.put("u", new String[]{"ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử", "ữ", "ự", "û", "ū", "ů", "ű", "ŭ", "ų", "µ", "у", "ဉ", "ု", "ူ", "ǔ", "ǖ", "ǘ", "ǚ", "ǜ", "უ", "उ", "ｕ", "ў", "ü"});
					charsArray.put("v", new String[]{"в", "ვ", "ϐ", "ｖ"});
					charsArray.put("w", new String[]{"ŵ", "ω", "ώ", "ဝ", "ွ", "ｗ"});
					charsArray.put("x", new String[]{"χ", "ξ", "ｘ"});
					charsArray.put("y", new String[]{"ý", "ỳ", "ỷ", "ỹ", "ỵ", "ÿ", "ŷ", "й", "ы", "υ", "ϋ", "ύ", "ΰ", "ي", "ယ", "ｙ"});
					charsArray.put("z", new String[]{"ź", "ž", "ż", "з", "ζ", "ز", "ဇ", "ზ", "ｚ"});
					charsArray.put("aa", new String[]{"ع", "आ", "آ"});
					charsArray.put("ae", new String[]{"æ", "ǽ"});
					charsArray.put("ai", new String[]{"ऐ"});
					charsArray.put("ch", new String[]{"ч", "ჩ", "ჭ", "چ"});
					charsArray.put("dj", new String[]{"ђ", "đ"});
					charsArray.put("dz", new String[]{"џ", "ძ"});
					charsArray.put("ei", new String[]{"ऍ"});
					charsArray.put("gh", new String[]{"غ", "ღ"});
					charsArray.put("ii", new String[]{"ई"});
					charsArray.put("ij", new String[]{"ĳ"});
					charsArray.put("kh", new String[]{"х", "خ", "ხ"});
					charsArray.put("lj", new String[]{"љ"});
					charsArray.put("nj", new String[]{"њ"});
					charsArray.put("oe", new String[]{"ö", "œ", "ؤ"});
					charsArray.put("oi", new String[]{"ऑ"});
					charsArray.put("oii", new String[]{"ऒ"});
					charsArray.put("ps", new String[]{"ψ"});
					charsArray.put("sh", new String[]{"ш", "შ", "ش"});
					charsArray.put("shch", new String[]{"щ"});
					charsArray.put("ss", new String[]{"ß"});
					charsArray.put("sx", new String[]{"ŝ"});
					charsArray.put("th", new String[]{"þ", "ϑ", "θ", "ث", "ذ", "ظ"});
					charsArray.put("ts", new String[]{"ц", "ც", "წ"});
					charsArray.put("ue", new String[]{"ü"});
					charsArray.put("uu", new String[]{"ऊ"});
					charsArray.put("ya", new String[]{"я"});
					charsArray.put("yu", new String[]{"ю"});
					charsArray.put("zh", new String[]{"ж", "ჟ", "ژ"});
					charsArray.put("(c)", new String[]{"©"});
					charsArray.put("A", new String[]{"Á", "À", "Ả", "Ã", "Ạ", "Ă", "Ắ", "Ằ", "Ẳ", "Ẵ", "Ặ", "Â", "Ấ", "Ầ", "Ẩ", "Ẫ", "Ậ", "Å", "Ā", "Ą", "Α", "Ά", "Ἀ", "Ἁ", "Ἂ", "Ἃ", "Ἄ", "Ἅ", "Ἆ", "Ἇ", "ᾈ", "ᾉ", "ᾊ", "ᾋ", "ᾌ", "ᾍ", "ᾎ", "ᾏ", "Ᾰ", "Ᾱ", "Ὰ", "Ά", "ᾼ", "А", "Ǻ", "Ǎ", "Ａ", "Ä"});
					charsArray.put("B", new String[]{"Б", "Β", "ब", "Ｂ"});
					charsArray.put("C", new String[]{"Ç", "Ć", "Č", "Ĉ", "Ċ", "Ｃ"});
					charsArray.put("D", new String[]{"Ď", "Ð", "Đ", "Ɖ", "Ɗ", "Ƌ", "ᴅ", "ᴆ", "Д", "Δ", "Ｄ"});
					charsArray.put("E", new String[]{"É", "È", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ế", "Ề", "Ể", "Ễ", "Ệ", "Ë", "Ē", "Ę", "Ě", "Ĕ", "Ė", "Ε", "Έ", "Ἐ", "Ἑ", "Ἒ", "Ἓ", "Ἔ", "Ἕ", "Έ", "Ὲ", "Е", "Ё", "Э", "Є", "Ə", "Ｅ"});
					charsArray.put("F", new String[]{"Ф", "Φ", "Ｆ"});
					charsArray.put("G", new String[]{"Ğ", "Ġ", "Ģ", "Г", "Ґ", "Γ", "Ｇ"});
					charsArray.put("H", new String[]{"Η", "Ή", "Ħ", "Ｈ"});
					charsArray.put("I", new String[]{"Í", "Ì", "Ỉ", "Ĩ", "Ị", "Î", "Ï", "Ī", "Ĭ", "Į", "İ", "Ι", "Ί", "Ϊ", "Ἰ", "Ἱ", "Ἳ", "Ἴ", "Ἵ", "Ἶ", "Ἷ", "Ῐ", "Ῑ", "Ὶ", "Ί", "И", "І", "Ї", "Ǐ", "ϒ", "Ｉ"});
					charsArray.put("J", new String[]{"Ｊ"});
					charsArray.put("K", new String[]{"К", "Κ", "Ｋ"});
					charsArray.put("L", new String[]{"Ĺ", "Ł", "Л", "Λ", "Ļ", "Ľ", "Ŀ", "ल", "Ｌ"});
					charsArray.put("M", new String[]{"М", "Μ", "Ｍ"});
					charsArray.put("N", new String[]{"Ń", "Ñ", "Ň", "Ņ", "Ŋ", "Н", "Ν", "Ｎ"});
					charsArray.put("O", new String[]{"Ó", "Ò", "Ỏ", "Õ", "Ọ", "Ô", "Ố", "Ồ", "Ổ", "Ỗ", "Ộ", "Ơ", "Ớ", "Ờ", "Ở", "Ỡ", "Ợ", "Ø", "Ō", "Ő", "Ŏ", "Ο", "Ό", "Ὀ", "Ὁ", "Ὂ", "Ὃ", "Ὄ", "Ὅ", "Ὸ", "Ό", "О", "Ө", "Ǒ", "Ǿ", "Ｏ", "Ö"});
					charsArray.put("P", new String[]{"П", "Π", "Ｐ"});
					charsArray.put("Q", new String[]{"Ｑ"});
					charsArray.put("R", new String[]{"Ř", "Ŕ", "Р", "Ρ", "Ŗ", "Ｒ"});
					charsArray.put("S", new String[]{"Ş", "Ŝ", "Ș", "Š", "Ś", "С", "Σ", "Ｓ"});
					charsArray.put("T", new String[]{"Ť", "Ţ", "Ŧ", "Ț", "Т", "Τ", "Ｔ"});
					charsArray.put("U", new String[]{"Ú", "Ù", "Ủ", "Ũ", "Ụ", "Ư", "Ứ", "Ừ", "Ử", "Ữ", "Ự", "Û", "Ū", "Ů", "Ű", "Ŭ", "Ų", "У", "Ǔ", "Ǖ", "Ǘ", "Ǚ", "Ǜ", "Ｕ", "Ў", "Ü"});
					charsArray.put("V", new String[]{"В", "Ｖ"});
					charsArray.put("W", new String[]{"Ω", "Ώ", "Ŵ", "Ｗ"});
					charsArray.put("X", new String[]{"Χ", "Ξ", "Ｘ"});
					charsArray.put("Y", new String[]{"Ý", "Ỳ", "Ỷ", "Ỹ", "Ỵ", "Ÿ", "Ῠ", "Ῡ", "Ὺ", "Ύ", "Ы", "Й", "Υ", "Ϋ", "Ŷ", "Ｙ"});
					charsArray.put("Z", new String[]{"Ź", "Ž", "Ż", "З", "Ζ", "Ｚ"});
					charsArray.put("AE", new String[]{"Æ", "Ǽ"});
					charsArray.put("Ch", new String[]{"Ч"});
					charsArray.put("Dj", new String[]{"Ђ"});
					charsArray.put("Dz", new String[]{"Џ"});
					charsArray.put("Gx", new String[]{"Ĝ"});
					charsArray.put("Hx", new String[]{"Ĥ"});
					charsArray.put("Ij", new String[]{"Ĳ"});
					charsArray.put("Jx", new String[]{"Ĵ"});
					charsArray.put("Kh", new String[]{"Х"});
					charsArray.put("Lj", new String[]{"Љ"});
					charsArray.put("Nj", new String[]{"Њ"});
					charsArray.put("Oe", new String[]{"Œ"});
					charsArray.put("Ps", new String[]{"Ψ"});
					charsArray.put("Sh", new String[]{"Ш"});
					charsArray.put("Shch", new String[]{"Щ"});
					charsArray.put("Ss", new String[]{"ẞ"});
					charsArray.put("Th", new String[]{"Þ", "Θ"});
					charsArray.put("Ts", new String[]{"Ц"});
					charsArray.put("Ya", new String[]{"Я"});
					charsArray.put("Yu", new String[]{"Ю"});
					charsArray.put("Zh", new String[]{"Ж"});
					charsArray.put(" ", new String[]{"\\xC2\\xA0", "\\xE2\\x80\\x80", "\\xE2\\x80\\x81", "\\xE2\\x80\\x82", "\\xE2\\x80\\x83", "\\xE2\\x80\\x84", "\\xE2\\x80\\x85", "\\xE2\\x80\\x86", "\\xE2\\x80\\x87", "\\xE2\\x80\\x88", "\\xE2\\x80\\x89", "\\xE2\\x80\\x8A", "\\xE2\\x80\\xAF", "\\xE2\\x81\\x9F", "\\xE3\\x80\\x80", "\\xEF\\xBE\\xA0"});
				}
			}
		}
		return charsArray;
	}
}
