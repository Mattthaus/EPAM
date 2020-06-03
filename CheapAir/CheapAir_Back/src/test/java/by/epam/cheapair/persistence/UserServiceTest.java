package by.epam.cheapair.persistence;

import by.epam.cheapair.dao.implementation.UserDAOImpl;
import by.epam.cheapair.dao.interfaces.UserDAO;
import by.epam.cheapair.persistence.domain.User;
import by.epam.cheapair.persistence.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class UserServiceTest {
    private static UserDAO userDAO;
    private UserService userService;
    public UserServiceTest(){
        userDAO = new UserDAOImpl();
        userService = new UserService();
    }

    @Test
    public void isAdminTest() throws SQLException {
        User user = new User("test_admin", "test_password", true);
        userDAO.createUser(user);

        boolean isAdmin = userService.isAdmin("test_admin");
        Assertions.assertTrue(isAdmin);
    }

    @AfterAll
    static void deleteUserFixture(){
        userDAO.deleteUserFixture();
    }
}
