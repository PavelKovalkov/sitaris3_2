package course.project.controller.user;

import course.project.entity.Bus;
import course.project.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user/bus-trip")
public class UserBusController {
    private final BusService busService;

    @Autowired
    public UserBusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/bus-info/{busId}")
    public String getBusInfo(@PathVariable String busId,
                             Model model) {
        Bus bus = busService.getBusInfo(busId);
        model.addAttribute("bus", bus);

        return "user/businfo";
    }
}
