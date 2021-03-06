package idu.cs.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import idu.cs.domain.User;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;

@Controller
public class HomeController {
	@Autowired UserRepository userRepo; // Dependency Injection
	
	@GetMapping("/test")
	public String home(Model model) {
		model.addAttribute("test", "인덕 컴소");
		model.addAttribute("egy", "유응구");
		return "index";
	}
	@GetMapping("/")
	public String loadWelcome(Model model) {
		return "welcome";
	}
	
	@GetMapping("/users")
	public String getAllUser(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}
	
	@GetMapping("/users/byname") //byname?name=***, ***값이 name변수에 저장됨
	public String getUsersByName(@Param(value = "name") String name, Model model) {
		List<User> users = userRepo.findByName(name);
		model.addAttribute("users", users);
		return "userlist";
	}
	
	
	@GetMapping("/users/nameasc") 
	public String getUsersByNameAsc(@Param(value = "name") String name, Model model) {
		List<User> users = userRepo.findByNameOrderByIdAsc(name);
		model.addAttribute("users", users);
		return "userlist";
	}
	
	
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId,  
	Model model) throws ResourceNotFoundException {
		//User user = userRepo.findById(userId).get();
		User user = userRepo.findById(userId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("not found " + userId ));
		model.addAttribute("id", "" + userId);
		model.addAttribute("name", user.getName());
		model.addAttribute("company", user.getCompany());
		model.addAttribute("user", user);
		return "user";
	}
	
	
	
	@GetMapping("/regform")
	public String loadRegForm(Model model) {		
		return "regform";
	}	
	@PostMapping("/users")
	public String createUser(@Valid User user, Model model) {
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}
	
	@PutMapping("/users/{id}")  //@RequestMapping(value = "/users/{id}" method = "RequestMethod.DELETE
	public String updateUserById(@PathVariable(value = "id") Long userId,  
			@Valid User userDetails, Model model) throws ResourceNotFoundException {
				//userDetails 폼을 통해 전송된 객체, user는 id로 jpa를 통해서 가져온 객체
		User user = userRepo.findById(userId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("not found " + userId ));
		user.setName(userDetails.getName()); // 삭제하는 부분, 객체 삭제-> jpa : record 삭제로 적용
		user.setCompany(userDetails.getCompany());
		User userUpdate = userRepo.save(user);
		//model.addAttribute("user", userUpdate);
		return "redirect:/users"; //엡데이트가 성공하면 users get방식으로 model에 user어트리뷰트를  전달한다.
	}
	
	
	@DeleteMapping("/users/{id}")  //@RequestMapping(value = "/users/{id}" method = "RequestMethod.DELETE
	public String deleteUserById(@PathVariable(value = "id") Long userId,  
	Model model) throws ResourceNotFoundException {
				User user = userRepo.findById(userId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("not found " + userId ));
		userRepo.delete(user); // 삭제하는 부분, 객체 삭제-> jpa : record 삭제로 적용
		model.addAttribute("name", user.getName());
		return "disjoin";
	}
	
	@GetMapping("/disjoin")
	public String disjoinForm(Model model) {
		return "disjoin";
	
	}

	
}
