package course.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import course.project.serializer.BusSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "app_bus_trips")
@Getter
@Setter
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
    @JsonIgnore
    private int totalTicketCount;
    private int availableTicketCount;
    private String ticketPrice;
}
