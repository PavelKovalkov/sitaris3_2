package course.project.service;

import course.project.entity.Bus;

public interface BusService {

    Bus getBusInfo(String busId);

    void saveBus(Bus bus);

    void updateBus(Bus bus);

    void deleteBus(String id);
}
