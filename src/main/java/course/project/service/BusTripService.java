package course.project.service;

import course.project.entity.BusTrip;

import java.util.Collection;

public interface BusTripService {

    BusTrip getBusTripInfo(String busTripId);

    void saveBusTrip(BusTrip busTrip);

    void updateBusTrip(BusTrip busTrip);

    void deleteBusTrip(String id);

    Collection<BusTrip> getBusTripsByDepartureDateAndDepartureStationAndArrivalStation(String date,
                                                                                       String departureStation,
                                                                                       String arrivalStation);
}
