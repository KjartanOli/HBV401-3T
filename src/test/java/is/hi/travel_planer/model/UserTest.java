import org.junit.*;
import static org.junit.Assert.*;

import is.hi.travel_planer.model.User;

public class UserTest {
	User user;
	@Before
	public void setUp() {
		user = new User("John Doe", "john@example.org", "1234567890", "1234567");
	}

	@After
	public void tearDown() {
		user = null;
	}

	@Test
	public void testName() {
		assertEquals(user.getName(), "John Doe");
	}

	@Test
	public void testEmail() {
		assertEquals(user.getEmail(), "john@example.org");
	}

	@Test
	public void testSSN() {
		assertEquals(user.getSSN(), "1234567890");
	}

	@Test
	public void testPhone() {
		assertEquals(user.getPhone(), "1234567");
	}
}
