package course.project.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import course.project.json.deserializer.BusTripDeserializer;
import course.project.json.serializer.BusSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "app_bus_trips")
@Getter
@Setter
@JsonDeserialize(using = BusTripDeserializer.class)
public class BusTrip {
    @Id
    private String id;
    @JsonSerialize(using = BusSerializer.class)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_id")
    private Bus bus;
    private Date departureDate;
    private String departureTime;
    private String travelTime;
    private String departureStation;
    private Date arrivalDate;
    private String arrivalTime;
    private String arrivalStation;
    private int totalTicketCount;
    private int availableTicketCount;
    private String ticketPrice;
}
