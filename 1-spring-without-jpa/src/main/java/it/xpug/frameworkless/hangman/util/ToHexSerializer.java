package it.xpug.frameworkless.hangman.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ToHexSerializer extends StdSerializer<Long> {
    protected ToHexSerializer(Class<Long> t) {
        super(t);
    }

    public ToHexSerializer(JavaType type) {
        super(type);
    }

    public ToHexSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    public ToHexSerializer() { super(Long.class); }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(Long.toHexString(value));
    }
}
