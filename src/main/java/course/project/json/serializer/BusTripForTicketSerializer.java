package course.project.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import course.project.entity.BusTrip;

import java.io.IOException;

public class BusTripForTicketSerializer extends StdSerializer<BusTrip> {
    public BusTripForTicketSerializer() {
        this(null);
    }

    public BusTripForTicketSerializer(Class<BusTrip> t) {
        super(t);
    }

    @Override
    public void serialize(BusTrip value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId());
        gen.writeStringField("departureStation", value.getDepartureStation());
        gen.writeStringField("arrivalStation", value.getArrivalStation());
        gen.writeObjectField("departureDate", value.getDepartureDate());
        gen.writeStringField("departureTime", value.getDepartureTime());
        gen.writeObjectField("arrivalDate", value.getArrivalDate());
        gen.writeObjectField("travelTime", value.getTravelTime());
        gen.writeStringField("arrivalTime", value.getArrivalTime());
        gen.writeStringField("bus",value.getBus().getId());
        gen.writeStringField("ticketPrice",value.getTicketPrice());
        gen.writeEndObject();
    }
}
