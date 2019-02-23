package course.project.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "app_bus_trips")
@Getter
@Setter
public class BusTrip {
    @Id
    private String id;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Bus.class)
    @JoinColumn(name = "bus_id")
    private Long busId;
    private String departureDate;
    private String departureTime;
    private String travelTime;
    private String departureStation;
    private String arrivalDate;
    private String arrivalTime;
    private String arrivalStation;
    private int totalTicketCount;
    private int availableTicketCount;
    private String ticketPrice;
}
