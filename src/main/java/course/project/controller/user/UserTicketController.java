package course.project.controller.user;

import course.project.entity.Ticket;
import course.project.resource.UserPublicInfo;
import course.project.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@RestController
@RequestMapping(value = "/user/bus-trip")
public class UserTicketController {
    private final TicketService ticketService;

    @Autowired
    public UserTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/buy")
    public ResponseEntity purchaseTicket(@RequestBody String body) {
        ticketService.addNewTicket(body);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get-not-used-tickets")
    public ResponseEntity getNotUsedTickets(HttpSession session) {
        UserPublicInfo publicInfo = (UserPublicInfo) session.getAttribute("userInfo");

        Collection<Ticket> result = ticketService.getNotUsedTickets(publicInfo.getEmail());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
