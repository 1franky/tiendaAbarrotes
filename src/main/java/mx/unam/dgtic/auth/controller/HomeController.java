package mx.unam.dgtic.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.auth.dto.UserInfoDTO;
import mx.unam.dgtic.auth.service.UserInfoService;
import mx.unam.dgtic.security.jwt.JWTTokenProvider;
import mx.unam.dgtic.security.request.JwtRequest;
import mx.unam.dgtic.security.request.LoginUserRequest;
import mx.unam.dgtic.security.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Log4j2
@Controller
public class HomeController {
	private final UserInfoService userInfoService;
	private final AuthenticationManager authenticationManager;
	private final JWTTokenProvider jwtTokenProvider;
	private final UserDetailsServiceImpl userDetailsService;

    public HomeController(UserInfoService userInfoService, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.userInfoService = userInfoService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

	@GetMapping({ "/", "/home", "/inicio", "/index"})
    public String home() {
        return "home";
    }

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login_success_handler")
	public String loginSuccessHandler() {
		log.info("Logging user login success...");
		return "index";
	}

	@PostMapping("/login_failure_handler")
	public String loginFailureHandler() {
		log.info("Login failure handler....");
		return "login";
	}

//	@GetMapping("/register")
//	public String showRegistrationForm(Model model) {
//		model.addAttribute("user", new UserInfoDTO());
//		return "signup_form";
//	}
//
//	@PostMapping("/process_register")
//	public String processRegister(UserInfoDTO user) throws UserInfoNotFoundException {
//		user.setUseIdStatus(1);
//		Set<UserInfoRoleDTO> roles = new HashSet<>();
//		roles.add(UserInfoRoleDTO.builder().usrId(1L).build());
//		user.setUseInfoRoles(roles);
//		user.setUseCreatedBy(1L);
//		user.setUseModifiedBy(1L);
//		userInfoService.save(user);
//		return "register_success";
//	}

	@PostMapping("/token")
	public String createAuthenticationToken(Model model, HttpSession session,
											@ModelAttribute LoginUserRequest loginUserRequest, HttpServletResponse res) throws Exception {
		log.info("LoginUserRequest {}", loginUserRequest);
		try {
			Optional<UserInfoDTO> userOpt = userInfoService.findByUseEmail(loginUserRequest.getUsername());
			if (userOpt.isEmpty()){
				session.setAttribute("msg","User not Exist");
				return "redirect:/login";
			}
			UserInfoDTO user = userOpt.get();
			if (user.getUseIdStatus() == 1) {
				Authentication authentication = authenticate(loginUserRequest.getUsername(),
						loginUserRequest.getPassword());
				log.info("authentication {}", authentication);
				String jwtToken = jwtTokenProvider.generateJwtToken(authentication, user);
				log.info("jwtToken {}", jwtToken);
				JwtRequest jwtRequest = new JwtRequest(jwtToken, user.getUseId(), user.getUseEmail(),
						jwtTokenProvider.getExpiryDuration(), authentication.getAuthorities());
				log.info("jwtRequest {}", jwtRequest);
				Cookie cookie = new Cookie("token",jwtToken);
				cookie.setMaxAge(Integer.MAX_VALUE);
				res.addCookie(cookie);
				session.setAttribute("msg","Login OK!");
			}
		} catch (UsernameNotFoundException | BadCredentialsException e) {
			session.setAttribute("msg","Bad Credentials");
			return "redirect:/login";
		}
		return "redirect:/index";
	}

	private Authentication authenticate(String username, String password) throws Exception {
		try {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
