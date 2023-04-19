package controllers;
import dao.EmployeeDao;
import dao.InvalidAdminException;
import dao.InvalidPasswordException;
import models.Employee;
import ninja.Context;
import ninja.NinjaTest;
import ninja.Result;
import ninja.session.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest extends NinjaTest {
    @Mock
    private EmployeeDao employeeDao;
    @Mock
    private Session session;
    private EmployeeController employeeController;
    // this will be executed only once before the execution of all the test cases we can use @Before and @BeforeAll
    @Before
    public void setupTest(){
        employeeController = new EmployeeController();
        employeeController.employeeDao = employeeDao;
    }
    @Test
    public void test(){
        assertTrue(true);
    }
    @Test
    public void createUser() throws Exception{
        Employee employee = new Employee();
        employee.setEmail("niki@gmail.com");
        employee.setPassword("HiHello");
        employee.setAdmin(false);

        Employee employee1 = new Employee();
        employee1.setEmail("niki@gmail.com");
        employee1.setPassword("HiHello");
        employee1.setAdmin(false);
        when(employeeDao.signup(employee)).thenReturn(employee1);
        Result result = employeeController.signup(employee);
        assertEquals(200,result.getStatusCode());
    }

    @Test
    public void validate() throws InvalidPasswordException, InvalidAdminException {
        String email = "nishtha@agrawal.com";
        String password = "nishtha@123";
        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setPassword(password);
        when(employeeDao.validateUser(email,password)).thenReturn(true);
        Result result = employeeController.login(employee,session);
        assertEquals(200,result.getStatusCode());
    }

}