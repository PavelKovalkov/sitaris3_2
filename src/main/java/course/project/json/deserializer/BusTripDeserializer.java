package course.project.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import course.project.entity.Bus;
import course.project.entity.BusTrip;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BusTripDeserializer extends StdDeserializer<BusTrip> {
    public BusTripDeserializer() {
        this(null);
    }

    public BusTripDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BusTrip deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JsonNode node = p.getCodec().readTree(p);

        BusTrip trip = new BusTrip();
        trip.setId(node.get("id").asText());
        Bus bus = new Bus();
        bus.setId(node.get("bus").asText());
        trip.setBus(bus);
        trip.setDepartureTime(node.get("departureTime").asText());
        trip.setTravelTime(node.get("travelTime").asText());
        trip.setDepartureStation(node.get("departureStation").asText());
        trip.setArrivalTime(node.get("arrivalTime").asText());
        trip.setArrivalStation(node.get("arrivalStation").asText());
        trip.setAvailableTicketCount(0);
        trip.setTotalTicketCount(node.get("totalTicketCount").asInt());
        trip.setAvailableTicketCount(node.get("totalTicketCount").asInt());
        trip.setTicketPrice(node.get("ticketPrice").asText());
        try {
            trip.setDepartureDate(new Date(dateFormat.parse(node.get("departureDate").asText()).getTime()));
            trip.setArrivalDate(new Date(dateFormat.parse(node.get("arrivalDate").asText()).getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return trip;
    }
}
