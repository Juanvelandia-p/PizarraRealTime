package escuelaing.edu.arsw.PizarraRealTime.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PizarraController {
    @MessageMapping("/draw")
    @SendTo("/topic/pizarra")
    public String broadcastDraw(String drawData) {
        // Aquí puedes procesar el mensaje si es necesario
        return drawData;
    }
}
