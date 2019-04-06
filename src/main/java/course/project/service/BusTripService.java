package course.project.service;

import course.project.entity.BusTrip;

public interface BusTripService {

    BusTrip getBusTripInfo(String busTripId);

    void saveBusTrip(BusTrip busTrip);

    void updateBusTrip(BusTrip busTrip);

    void deleteBusTrip(String id);
}
