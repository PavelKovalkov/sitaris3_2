package course.project.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import course.project.entity.Bus;

import java.io.IOException;

public class BusSerializer extends JsonSerializer<Bus> {
    @Override
    public void serialize(Bus value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getId());
    }
}
