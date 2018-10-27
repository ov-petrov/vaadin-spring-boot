package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
public class MainController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}

	public void saveCustomer(@RequestParam String firstName, @RequestParam String lastName,
										   @RequestParam LocalDate birthDate, @RequestParam CustomerStatus status) {
		Customer c = new Customer();
		c.setFirstName(firstName);
		c.setLastName(lastName);
		c.setBirthDate(birthDate);
		c.setStatus(status);

		customerRepository.save(c);
	}

	public void saveCustomer(@RequestParam Customer customer) {
		customerRepository.save(customer);
	}
	
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
}
