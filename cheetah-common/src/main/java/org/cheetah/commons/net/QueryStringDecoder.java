package org.cheetah.commons.net;

import com.google.common.base.Charsets;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

/**
 * borrow from netty.
 *
 * @author Max
 * @version $Id: $Id
 */
public class QueryStringDecoder {
    private static final int DEFAULT_MAX_PARAMS = 1024;

    private final Charset charset;
    private final String uri;
    private final boolean hasPath;
    private final int maxParams;
    private String path;
    private Map<String, List<String>> params;
    private int nParams;

    /**
     * <p>Constructor for QueryStringDecoder.</p>
     *
     * @param uri a {@link String} object.
     */
    public QueryStringDecoder(String uri) {
        this(URI.create(uri));
    }

    /**
     * Creates a new decoder that decodes the specified URI. The decoder will
     * assume that the query string is encoded in UTF-8.
     *
     * @param uri a {@link URI} object.
     */
    public QueryStringDecoder(URI uri) {
        this(uri, Charsets.UTF_8);
    }

    /**
     * Creates a new decoder that decodes the specified URI encoded in the
     * specified charset.
     *
     * @param uri a {@link URI} object.
     * @param charset a {@link Charset} object.
     */
    public QueryStringDecoder(URI uri, Charset charset) {
        this(uri, charset, DEFAULT_MAX_PARAMS);
    }

    /**
     * Creates a new decoder that decodes the specified URI encoded in the
     * specified charset.
     *
     * @param uri a {@link URI} object.
     * @param charset a {@link Charset} object.
     * @param maxParams a int.
     */
    public QueryStringDecoder(URI uri, Charset charset, int maxParams) {
        if (uri == null) {
            throw new NullPointerException("getUri");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        if (maxParams <= 0) {
            throw new IllegalArgumentException(
                    "maxParams: " + maxParams + " (expected: a positive integer)");
        }

        String rawPath = uri.getRawPath();
        if (rawPath != null) {
            hasPath = true;
        } else {
            rawPath = "";
            hasPath = false;
        }
        // Also take care of cut of things like "http://localhost"
        this.uri = rawPath + '?' + uri.getRawQuery();

        this.charset = charset;
        this.maxParams = maxParams;
    }

    /**
     * Returns the decoded path string of the URI.
     *
     * @return a {@link String} object.
     */
    public String path() {
        if (path == null) {
            if (!hasPath) {
                return path = "";
            }

            int pathEndPos = uri.indexOf('?');
            if (pathEndPos < 0) {
                path = uri;
            } else {
                return path = uri.substring(0, pathEndPos);
            }
        }
        return path;
    }

    /**
     * Returns the decoded key-value parameter pairs of the URI.
     *
     * @return a {@link Map} object.
     */
    public Map<String, List<String>> parameters() {
        if (params == null) {
            if (hasPath) {
                int pathLength = path().length();
                if (uri.length() == pathLength) {
                    return Collections.emptyMap();
                }
                decodeParams(uri.substring(pathLength + 1));
            } else {
                if (uri.isEmpty()) {
                    return Collections.emptyMap();
                }
                decodeParams(uri);
            }
        }
        return params;
    }

    private void decodeParams(String s) {
        Map<String, List<String>> localParams = this.params = new LinkedHashMap<>();
        nParams = 0;
        String name = null;
        int pos = 0; // Beginning of the unprocessed region
        int i;       // End of the unprocessed region
        char c;  // Current character
        for (i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c == '=' && name == null) {
                if (pos != i) {
                    name = decodeComponent(s.substring(pos, i), charset);
                }
                pos = i + 1;
                // http://www.w3.org/TR/html401/appendix/notes.html#h-B.2.2
            } else if (c == '&' || c == ';') {
                if (name == null && pos != i) {
                    // We haven't seen an `=' so far but moved forward.
                    // Must be a param of the form '&a&' so add it with
                    // an empty value.
                    if (!addParam(localParams, decodeComponent(s.substring(pos, i), charset), "")) {
                        return;
                    }
                } else if (name != null) {
                    if (!addParam(localParams, name, decodeComponent(s.substring(pos, i), charset))) {
                        return;
                    }
                    name = null;
                }
                pos = i + 1;
            }
        }

        if (pos != i) {  // Are there characters we haven't dealt with?
            if (name == null) {     // Yes and we haven't seen any `='.
                addParam(localParams, decodeComponent(s.substring(pos, i), charset), "");
            } else {                // Yes and this must be the last value.
                addParam(localParams, name, decodeComponent(s.substring(pos, i), charset));
            }
        } else if (name != null) {  // Have we seen a name without value?
            addParam(localParams, name, "");
        }
    }

    private boolean addParam(Map<String, List<String>> params, String name, String value) {
        if (nParams >= maxParams) {
            return false;
        }

        List<String> values = params.get(name);
        if (values == null) {
            values = new ArrayList<String>(1);  // Often there's only 1 value.
            params.put(name, values);
        }
        values.add(value);
        nParams++;
        return true;
    }

    /**
     * <p>decodeComponent.</p>
     *
     * @param s a {@link String} object.
     * @return a {@link String} object.
     */
    public static String decodeComponent(final String s) {
        return decodeComponent(s, Charsets.UTF_8);
    }

    /**
     * <p>decodeComponent.</p>
     *
     * @param s a {@link String} object.
     * @param charset a {@link Charset} object.
     * @return a {@link String} object.
     */
    @SuppressWarnings("fallthrough")
    public static String decodeComponent(final String s,
                                         final Charset charset) {
        if (s == null) {
            return "";
        }
        final int size = s.length();
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            final char c = s.charAt(i);
            switch (c) {
                case '%':
                    i++;  // We can skip at least one char, e.g. `%%'.
                    // Fall through.
                case '+':
                    modified = true;
                    break;
            }
        }
        if (!modified) {
            return s;
        }
        final byte[] buf = new byte[size];
        int pos = 0;  // position in `buf'.
        for (int i = 0; i < size; i++) {
            char c = s.charAt(i);
            switch (c) {
                case '+':
                    buf[pos++] = ' ';  // "+" -> " "
                    break;
                case '%':
                    if (i == size - 1) {
                        throw new IllegalArgumentException("unterminated escape"
                                + " sequence at end of string: " + s);
                    }
                    c = s.charAt(++i);
                    if (c == '%') {
                        buf[pos++] = '%';  // "%%" -> "%"
                        break;
                    }
                    if (i == size - 1) {
                        throw new IllegalArgumentException("partial escape"
                                + " sequence at end of string: " + s);
                    }
                    c = decodeHexNibble(c);
                    final char c2 = decodeHexNibble(s.charAt(++i));
                    if (c == Character.MAX_VALUE || c2 == Character.MAX_VALUE) {
                        throw new IllegalArgumentException(
                                "invalid escape sequence `%" + s.charAt(i - 1)
                                        + s.charAt(i) + "' at index " + (i - 2)
                                        + " of: " + s);
                    }
                    c = (char) (c * 16 + c2);
                    // Fall through.
                default:
                    buf[pos++] = (byte) c;
                    break;
            }
        }
        return new String(buf, 0, pos, charset);
    }

    /**
     * Helper to decode half of a hexadecimal number from a string.
     *
     * @param c The ASCII character of the hexadecimal number to decode.
     *          Must be in the range {@code [0-9a-fA-F]}.
     * @return The hexadecimal value represented in the ASCII character
     * given, or {@link Character#MAX_VALUE} if the character is invalid.
     */
    private static char decodeHexNibble(final char c) {
        if ('0' <= c && c <= '9') {
            return (char) (c - '0');
        } else if ('a' <= c && c <= 'f') {
            return (char) (c - 'a' + 10);
        } else if ('A' <= c && c <= 'F') {
            return (char) (c - 'A' + 10);
        } else {
            return Character.MAX_VALUE;
        }
    }
}
