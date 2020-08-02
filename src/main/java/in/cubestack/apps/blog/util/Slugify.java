package in.cubestack.apps.blog.util;


import java.io.InputStream;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * https://github.com/slugify/slugify
 */
public class Slugify {
    private static final Properties REPLACEMENTS = new Properties();

    private final static String ASCII = "Cyrillic-Latin; Any-Latin; Latin-ASCII; [^\\p{Print}] Remove; ['\"] Remove; Any-Lower";
    private final static String EMPTY = "";
    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";

    private final static Pattern PATTERN_NORMALIZE_NON_ASCII = Pattern.compile("[^\\p{ASCII}]+");
    private final static Pattern PATTERN_NORMALIZE_HYPHEN_SEPARATOR = Pattern.compile("[\\W\\s+]+");
    private final static Pattern PATTERN_NORMALIZE_UNDERSCORE_SEPARATOR = Pattern.compile("[[^a-zA-Z0-9\\-]\\s+]+");
    private final static Pattern PATTERN_NORMALIZE_TRIM_DASH = Pattern.compile("^-|-$");


    private final Map<String, String> customReplacements = new HashMap<String, String>();
    private final Map<Character, String> builtinReplacements = new HashMap<Character, String>();

    private boolean underscoreSeparator = false;
    private boolean lowerCase = true;

    @Deprecated
    public Slugify(boolean lowerCase) {
        this();

        withLowerCase(lowerCase);
    }

    public Slugify() {
        loadReplacements();
        createPatternCache();
    }

    public Slugify withCustomReplacement(final String from, final String to) {
        customReplacements.put(from, to);
        return this;
    }

    public Slugify withCustomReplacements(final Map<String, String> customReplacements) {
        this.customReplacements.putAll(customReplacements);
        return this;
    }

    public Slugify withUnderscoreSeparator(final boolean underscoreSeparator) {
        this.underscoreSeparator = underscoreSeparator;
        return this;
    }


    public Slugify withLowerCase(final boolean lowerCase) {
        this.lowerCase = lowerCase;
        return this;
    }

    public String slugify(final String text) {
        String input = text;
        if (isNullOrBlank(input)) {
            return EMPTY;
        }

        input = input.trim();
        input = customReplacements(input);
        input = builtInReplacements(input);

        input = normalize(input);

        if (lowerCase) {
            input = input.toLowerCase();
        }

        return input;
    }

    public Map<String, String> defaultProperties() {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("\u00c4", "Ae");
        replacements.put("\u00e4", "ae");
        replacements.put("\u00d6", "Oe");
        replacements.put("\u00f6", "oe");
        replacements.put("\u00dc", "Ue");
        replacements.put("\u00fc", "ue");
        replacements.put("\u00df", "ss");
        replacements.put("\u00e5", "aa");
        replacements.put("\u00c5", "Aa");
        replacements.put("\u00e6", "ae");
        replacements.put("\u00c6", "Ae");
        replacements.put("\u00f8", "oe");
        replacements.put("\u00d8", "Oe");
        replacements.put("\u011e", "g");
        replacements.put("\u011f", "g");
        replacements.put("\u0130", "i");
        replacements.put("\u0131", "i");
        replacements.put("\u015e", "s");
        replacements.put("\u015f", "s");
        replacements.put("\u0410", "A");
        replacements.put("\u0411", "B");
        replacements.put("\u0412", "V");
        replacements.put("\u0413", "G");
        replacements.put("\u0414", "D");
        replacements.put("\u0415", "E");
        replacements.put("\u0416", "Zh");
        replacements.put("\u0417", "Z");
        replacements.put("\u0418", "I");
        replacements.put("\u0419", "J");
        replacements.put("\u041a", "K");
        replacements.put("\u041b", "L");
        replacements.put("\u041c", "M");
        replacements.put("\u041d", "N");
        replacements.put("\u041e", "O");
        replacements.put("\u041f", "P");
        replacements.put("\u0420", "R");
        replacements.put("\u0421", "S");
        replacements.put("\u0422", "T");
        replacements.put("\u0423", "U");
        replacements.put("\u0424", "F");
        replacements.put("\u0425", "H");
        replacements.put("\u0426", "Ts");
        replacements.put("\u0427", "Ch");
        replacements.put("\u0428", "Sh");
        replacements.put("\u0429", "Shch");
        replacements.put("\u042a", "'");
        replacements.put("\u042b", "Y");
        replacements.put("\u042c", "'");
        replacements.put("\u042d", "E");
        replacements.put("\u042e", "Yu");
        replacements.put("\u042f", "Ya");
        replacements.put("\u0430", "a");
        replacements.put("\u0431", "b");
        replacements.put("\u0432", "v");
        replacements.put("\u0433", "g");
        replacements.put("\u0434", "d");
        replacements.put("\u0435", "e");
        replacements.put("\u0436", "zh");
        replacements.put("\u0437", "z");
        replacements.put("\u0438", "i");
        replacements.put("\u0439", "j");
        replacements.put("\u043a", "k");
        replacements.put("\u043b", "l");
        replacements.put("\u043c", "m");
        replacements.put("\u043d", "n");
        replacements.put("\u043e", "o");
        replacements.put("\u043f", "p");
        replacements.put("\u0440", "r");
        replacements.put("\u0441", "s");
        replacements.put("\u0442", "t");
        replacements.put("\u0443", "u");
        replacements.put("\u0444", "f");
        replacements.put("\u0445", "h");
        replacements.put("\u0446", "ts");
        replacements.put("\u0447", "ch");
        replacements.put("\u0448", "sh");
        replacements.put("\u0449", "shch");
        replacements.put("\u044a", "'");
        replacements.put("\u044b", "y");
        replacements.put("\u044c", "'");
        replacements.put("\u044d", "e");
        replacements.put("\u044e", "yu");
        replacements.put("\u044f", "ya");
        replacements.put("\u0141", "L");
        replacements.put("\u0142", "l");
        replacements.put("\u0391", "A");
        replacements.put("\u0392", "B");
        replacements.put("\u0393", "G");
        replacements.put("\u0394", "D");
        replacements.put("\u0395", "E");
        replacements.put("\u0396", "Z");
        replacements.put("\u0397", "H");
        replacements.put("\u0398", "TH");
        replacements.put("\u0399", "I");
        replacements.put("\u039A", "K");
        replacements.put("\u039B", "L");
        replacements.put("\u039C", "M");
        replacements.put("\u039D", "N");
        replacements.put("\u039E", "KS");
        replacements.put("\u039F", "O");
        replacements.put("\u03A0", "P");
        replacements.put("\u03A1", "R");
        replacements.put("\u03A3", "S");
        replacements.put("\u03A4", "T");
        replacements.put("\u03A5", "Y");
        replacements.put("\u03A6", "F");
        replacements.put("\u03A7", "X");
        replacements.put("\u03A8", "PS");
        replacements.put("\u03A9", "W");
        replacements.put("\u03B1", "a");
        replacements.put("\u03B2", "b");
        replacements.put("\u03B3", "g");
        replacements.put("\u03B4", "d");
        replacements.put("\u03B5", "e");
        replacements.put("\u03B6", "z");
        replacements.put("\u03B7", "h");
        replacements.put("\u03B8", "th");
        replacements.put("\u03B9", "i");
        replacements.put("\u03BA", "k");
        replacements.put("\u03BB", "l");
        replacements.put("\u03BC", "m");
        replacements.put("\u03BD", "n");
        replacements.put("\u03BE", "ks");
        replacements.put("\u03BF", "o");
        replacements.put("\u03C0", "p");
        replacements.put("\u03C1", "r");
        replacements.put("\u03C2", "s");
        replacements.put("\u03C3", "s");
        replacements.put("\u03C4", "t");
        replacements.put("\u03C5", "y");
        replacements.put("\u03C6", "f");
        replacements.put("\u03C7", "x");
        replacements.put("\u03C8", "ps");
        replacements.put("\u03C9", "w");
        replacements.put("\u0386", "A");
        replacements.put("\u0388", "E");
        replacements.put("\u0389", "H");
        replacements.put("\u038A", "I");
        replacements.put("\u038C", "O");
        replacements.put("\u038E", "Y");
        replacements.put("\u038F", "W");
        replacements.put("\u03AC", "a");
        replacements.put("\u03AD", "e");
        replacements.put("\u03AE", "h");
        replacements.put("\u03CC", "o");
        replacements.put("\u03AF", "i");
        replacements.put("\u03CD", "y");
        replacements.put("\u03CE", "w");
        replacements.put("\u03AA", "I");
        replacements.put("\u03AB", "Y");
        replacements.put("\u03CA", "i");
        replacements.put("\u03CB", "u");
        replacements.put("\u03B0", "u");
        replacements.put("\u0390", "i");
        replacements.put("\u0623", "a");
        replacements.put("\u0628", "b");
        replacements.put("\u062A", "t");
        replacements.put("\u062B", "th");
        replacements.put("\u062C", "g");
        replacements.put("\u062D", "h");
        replacements.put("\u062E", "kh");
        replacements.put("\u062F", "d");
        replacements.put("\u0630", "th");
        replacements.put("\u0631", "r");
        replacements.put("\u0632", "z");
        replacements.put("\u0633", "s");
        replacements.put("\u0634", "sh");
        replacements.put("\u0635", "s");
        replacements.put("\u0636", "d");
        replacements.put("\u0637", "t");
        replacements.put("\u0638", "th");
        replacements.put("\u0639", "aa");
        replacements.put("\u063A", "gh");
        replacements.put("\u0641", "f");
        replacements.put("\u0642", "k");
        replacements.put("\u0643", "k");
        replacements.put("\u0644", "l");
        replacements.put("\u0645", "m");
        replacements.put("\u0646", "n");
        replacements.put("\u0647", "h");
        replacements.put("\u0648", "o");
        replacements.put("\u064A", "y");

        return replacements;
    }

    public Map<String, String> getCustomReplacements() {
        return customReplacements;
    }

    private String customReplacements(String input) {
        Map<String, String> customReplacements = getCustomReplacements();
        for (Map.Entry<String, String> entry : customReplacements.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue());
        }

        return input;
    }

    private String builtInReplacements(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char s : input.toCharArray()) {
            if (builtinReplacements.containsKey(s)) {
                stringBuilder.append(builtinReplacements.get(s));
            } else {
                stringBuilder.append(s);
            }
        }
        return stringBuilder.toString();
    }

    private Slugify loadReplacements() {

        if (!REPLACEMENTS.isEmpty()) {
            return this;
        }


        REPLACEMENTS.putAll(defaultProperties());
        return this;

    }

    private void createPatternCache() {
        if (!builtinReplacements.isEmpty()) {
            return;
        }

        REPLACEMENTS.entrySet().forEach(replacement -> addReplacement(replacement));
    }

    private void addReplacement(Map.Entry<Object, Object> e) {
        if (isValidReplacement(e)) {
            throw new IllegalArgumentException("Builtin replacements can only be characters");
        }
        builtinReplacements.put(e.getKey().toString().charAt(0), e.getValue().toString());
    }

    private boolean isValidReplacement(Map.Entry<Object, Object> replacement) {
        return replacement.getKey().toString().length() > 1;
    }

    private static boolean isNullOrBlank(final String string) {
        return string == null || string.trim().isEmpty();
    }

    private String normalize(final String input) {
        String text = Normalizer.normalize(input, Normalizer.Form.NFKD);
        text = matchAndReplace(text);
        return text;
    }

    private String matchAndReplace(final String input) {
        String text = PATTERN_NORMALIZE_NON_ASCII.matcher(input).replaceAll(EMPTY);
        text = underscoreSeparator ? PATTERN_NORMALIZE_UNDERSCORE_SEPARATOR.matcher(text).replaceAll(UNDERSCORE) :
                PATTERN_NORMALIZE_HYPHEN_SEPARATOR.matcher(text).replaceAll(HYPHEN);
        text = PATTERN_NORMALIZE_TRIM_DASH.matcher(text).replaceAll(EMPTY);

        return text;
    }
}