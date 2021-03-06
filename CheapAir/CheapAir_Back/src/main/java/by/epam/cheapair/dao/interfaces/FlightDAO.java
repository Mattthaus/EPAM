package by.epam.cheapair.dao.interfaces;

import by.epam.cheapair.persistence.domain.Flight;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface FlightDAO {
    Optional<Flight> get(Integer id);
    ArrayList<Flight> getAll();
    void createFlight(Flight flight);
    void updateFlight(Flight flight);
    void deleteFlight(Flight flight);
}
