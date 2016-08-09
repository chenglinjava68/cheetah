package org.cheetah.rest.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.cheetah.common.utils.ObjectMappers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Max
 */
@Provider
@Consumes(MediaType.WILDCARD) // NOTE: required to support "non-standard" JSON variants
@Produces(MediaType.WILDCARD)
public class JarrsApiJsonProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

    static ObjectMapper OBJECT_MAPPER = ObjectMappers.specialStyleObjectMapper();

    final JacksonJsonProvider jsonProvider;

    public JarrsApiJsonProvider() {
        jsonProvider = new JacksonJsonProvider(OBJECT_MAPPER);
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return jsonProvider.isReadable(type, genericType, annotations, mediaType);
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        return jsonProvider.readFrom(type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return jsonProvider.isWriteable(type, genericType, annotations, mediaType);
    }

    @Override
    public long getSize(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return jsonProvider.getSize(o, type, genericType, annotations, mediaType);
    }

    @Override
    public void writeTo(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        jsonProvider.writeTo(o, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }
}
