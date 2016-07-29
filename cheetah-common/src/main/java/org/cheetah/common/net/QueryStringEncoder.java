package org.cheetah.common.net;

import org.cheetah.common.Constants;
import org.cheetah.common.logger.Loggers;
import org.cheetah.common.utils.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;

/**
 * borrow from netty.
 *
 * @author Max
 * @version $Id: $Id
 */
public class QueryStringEncoder {

    private final Charset charset;
    private final String uri;
    private final List<Param> params = new ArrayList<Param>();

    /**
     * Creates a new encoder that encodes a URI that starts with the specified
     * path string.  The encoder will encode the URI in UTF-8.
     *
     * @param uri a {@link String} object.
     */
    public QueryStringEncoder(String uri) {
        this(uri, Constants.DEFAULT_CHARSET);
    }

    /**
     * Creates a new encoder that encodes a URI that starts with the specified
     * path string in the specified charset.
     *
     * @param uri a {@link String} object.
     * @param charset a {@link Charset} object.
     */
    public QueryStringEncoder(String uri, Charset charset) {
        Assert.hasText(uri, "uri must not be null or empty.");
        Assert.notNull(charset, "charset must not be null.");
        this.uri = uri;
        this.charset = charset;
    }

    /**
     * Adds a parameter with the specified name and value to this encoder.
     *
     * @param name a {@link String} object.
     * @param value a {@link String} object.
     */
    public void addParam(String name, String value) {
        Assert.hasText(name, "name must not be null or empty.");
        Assert.notNull(value, "value must not be null.");
        params.add(new Param(name, value));
    }

    /**
     * Returns the URL-encoded URI object which was created from the path string
     * specified in the constructor and the parameters added by
     * {@link #addParam(String, String)} getMethod.
     *
     * @return a {@link URI} object.
     */
    public URI toUri() {
        try {
            return new URI(toString());
        } catch (URISyntaxException e) {
            throw new QueryStringEncoderException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * Returns the URL-encoded URI which was created from the path string
     * specified in the constructor and the parameters added by
     * {@link #addParam(String, String)} getMethod.
     */
    @Override
    public String toString() {
        if (params.isEmpty()) {
            return uri;
        }

        StringBuilder sb = new StringBuilder(uri).append('?');
        for (int i = 0; i < params.size(); i++) {
            Param param = params.get(i);
            sb.append(encodeComponent(param.name, charset));
            sb.append('=');
            sb.append(encodeComponent(param.value, charset));
            if (i != params.size() - 1) {
                sb.append('&');
            }
        }
        return sb.toString();
    }

    private static String encodeComponent(String s, Charset charset) {
        // TODO: Optimize logger.
        try {
            return URLEncoder.encode(s, charset.name()).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            Loggers.logger().warn(QueryStringEncoder.class, "encode component occurs error.", e);
            throw new UnsupportedCharsetException(charset.name());
        }
    }

    private static final class Param {

        final String name;
        final String value;

        Param(String name, String value) {
            this.value = value;
            this.name = name;
        }
    }
}
