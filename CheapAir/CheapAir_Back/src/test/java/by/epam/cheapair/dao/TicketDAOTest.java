package by.epam.cheapair.dao;

import by.epam.cheapair.dao.implementation.FlightDAOImpl;
import by.epam.cheapair.dao.implementation.TicketDAOImpl;
import by.epam.cheapair.dao.implementation.UserDAOImpl;
import by.epam.cheapair.dao.interfaces.FlightDAO;
import by.epam.cheapair.dao.interfaces.TicketDAO;
import by.epam.cheapair.dao.interfaces.UserDAO;
import by.epam.cheapair.persistence.domain.BaseEntity;
import by.epam.cheapair.persistence.domain.Ticket;
import by.epam.cheapair.persistence.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TicketDAOTest {
    private UserDAO userDAO;
    private FlightDAO flightDAO;
    private TicketDAO ticketDAO;
    private static final Logger logger = LogManager.getLogger();

    public TicketDAOTest(){
        userDAO = new UserDAOImpl();
        flightDAO = new FlightDAOImpl();
        ticketDAO = new TicketDAOImpl();
    }

    @Test
    public void getTicketTest() throws SQLException {
        logger.info(ticketDAO.findById(2).orElse(null));
    }

    @Test
    public void getAllTicketsTest() throws SQLException{
        logger.info(ticketDAO.getAll());
    }

    @Test
    public void getTicketsByUser() throws SQLException{
        User user = userDAO.get(1).orElse(null);
        logger.info("User " + user + "has tickets below: ");
        logger.info(ticketDAO.findUserTickets(user));
    }

    @Test
    public void createTicketTest() throws SQLException {

        int was =  ticketDAO.getAll().size();

        Ticket ticket = new Ticket(2, 3, true, false);
        ticketDAO.createTicket(ticket);

        ArrayList<Ticket> tickets = ticketDAO.getAll()
                .stream()
                .sorted(Comparator.comparingInt(BaseEntity::getId))
                .collect(Collectors.toCollection(ArrayList::new));
        int become = tickets.size();

        Assertions.assertEquals(was + 1, become);
    }

    @Test
    public void upgradeTicketTest() throws SQLException{
        Ticket ticket_was = ticketDAO.findById(3).orElse(null);
        logger.info(ticket_was);
        ticket_was.setPrice(1000);
        ticketDAO.updateTicket(ticket_was);
        Ticket ticket_become = ticketDAO.findById(3).orElse(null);
        logger.info(ticket_become);
    }

    @Test
    public void deleteTicketTest() throws SQLException{
        int expected = ticketDAO.getAll().size() - 1;

        Ticket ticket = ticketDAO.getAll().get(expected);
        ticketDAO.deleteTicket(ticket);

        int actual = ticketDAO.getAll().size();

        Assertions.assertEquals(expected, actual);
    }
}
