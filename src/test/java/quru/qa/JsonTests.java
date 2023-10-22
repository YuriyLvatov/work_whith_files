package quru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import quru.qa.pojos.TicketPojo;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonTests {
    private ClassLoader classLoader = FilesZipTest.class.getClassLoader();

    @DisplayName("Проверка содержимого json файла")
    @Test
    void jsonTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("ticket.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            TicketPojo ticketPojo = new ObjectMapper().readValue(isr, TicketPojo.class);
            Assertions.assertEquals(999111, ticketPojo.getTicket().getTicketId());
            Assertions.assertEquals(33322, ticketPojo.getTicket().getCuratorId());
            Assertions.assertEquals("Григорий Григорьевич", ticketPojo.getTicket().getCuratorName());
            Assertions.assertEquals(12345, ticketPojo.getTicket().getTasks().get(0).getTask());
            Assertions.assertEquals("Диагностика", ticketPojo.getTicket().getTasks().get(0).getName());
            Assertions.assertEquals("Асташкин Алексей Валерьевич", ticketPojo.getTicket().getTasks().get(0).getUser());
            Assertions.assertEquals("1ЛТП", ticketPojo.getTicket().getTasks().get(0).getUnit());
            Assertions.assertEquals(12, ticketPojo.getTicket().getTasks().get(0).getSla());
            Assertions.assertEquals(true, ticketPojo.getTicket().getTasks().get(0).isWork());
        }
    }
}
