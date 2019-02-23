package course.project.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_buses")
@Getter
@Setter
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bus_id")
    private Long id;
    private String model;
    private String creationYear;
    private double mileage;

    @OneToMany(mappedBy = "busId", fetch = FetchType.EAGER, targetEntity = BusTrip.class)
    private List<BusTrip> busTrips;
}
