package course.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import course.project.json.serializer.BusTripForTicketSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "app_tickets")
@Getter
@Setter
public class Ticket {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_trip_id")
    @JsonSerialize(using = BusTripForTicketSerializer.class)
    private BusTrip busTrip;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    private String userName;
    private String userSurname;
    private String userPatronymic;
    private String passportId;
}
