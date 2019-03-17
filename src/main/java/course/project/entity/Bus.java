package course.project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "app_buses")
@Getter
@Setter
public class Bus {
    @Id
    private String id;
    private String model;
    private String creationYear;
    private double mileage;

    @OneToMany(mappedBy = "bus", fetch = FetchType.EAGER)
    private Collection<BusTrip> busTrips;
}
