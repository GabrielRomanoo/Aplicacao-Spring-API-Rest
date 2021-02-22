package br.com.empresa.mvc.mudi.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.env.Environment;

import br.com.empresa.mvc.mudi.dto.PasswordDto;
import br.com.empresa.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.empresa.mvc.mudi.model.PasswordResetToken;
import br.com.empresa.mvc.mudi.model.Pedido;
import br.com.empresa.mvc.mudi.model.StatusPedido;
import br.com.empresa.mvc.mudi.model.User;
import br.com.empresa.mvc.mudi.repository.PasswordTokenRepository;
import br.com.empresa.mvc.mudi.repository.PedidoRepository;
import br.com.empresa.mvc.mudi.repository.UserRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MessageSource messages;

	@Autowired
	private Environment env;

	@PostMapping("salvarSenha")
	public String salvarSenha(PasswordDto passwordDto) {
		
		String result = validatePasswordResetToken(passwordDto.getToken());
		
		System.out.println("nova senha: " + passwordDto.getNovasenha());
		

		if (result != null) {
			System.out.println("DEU MUITO RUIM");
			return "redirect:/usuario/home";
		}

		String name = passwordTokenRepository.findUserByToken(passwordDto.getToken());
		User user = userRepository.findByUsername(name);
		if (user != null) {
			changeUserPassword(user, passwordDto.getNovasenha());
			return "salvo";
		} else {
			return "redirect:/usuario/home";
		}
	}

	@GetMapping("updatePassword")
	public String updatePassword() {
		System.out.println("ENTROU AQUI");
		return "updatePassword";
	}

	@GetMapping("mudarSenha")
	public String mudarSenha(Locale locale, Model model, @RequestParam("token") String token) {
		System.out.println("ENTROUUUUUUU");
		String result = validatePasswordResetToken(token);
		if (result != null) {
			System.out.println("DEU NULO");
			return "redirect:/usuario/home";
		} else {
			System.out.println("DEU BOM");
			model.addAttribute("token", token);
			return "updatePassword";
		}
	}

	@PostMapping("resetarSenha")
	public String resetarSenha(@RequestParam("email") String email, HttpServletRequest request) throws Exception {
		System.out.println(email);

		User user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("NÃO ACHOU O USUARIO");
			return "redirect:/usuario/home";
		}

		String token = UUID.randomUUID().toString();
		createPasswordResetTokenForUser(user, token);
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		mailSender.send(constructResetTokenEmail(appUrl, request.getLocale(), token, user));
		// erro que estava dando ao enviar o email:
		// https://stackoverflow.com/questions/43406528/javamail-api-username-and-password-not-accepted-gmail

		return "email";
	}

	@GetMapping("esqueciSenha")
	public String esqueciSenha() {
		return "senha";
	}

	@GetMapping("pedido")
	public String home(Model model, Principal principal) { // objeto principal pegamos dados do usuario
		List<Pedido> pedidos = pedidoRepository.findAllByUser(principal.getName());
		model.addAttribute("pedidos", pedidos);
		return "usuario/home";
	}

	@GetMapping("pedido/{satus}")
	public String porStatus(@PathVariable("satus") String status, Model model) { // o spring vai injetar no parametro
																					// status uma variavel que vem do
																					// path chamada status
		List<Pedido> pedidos = pedidoRepository.findByStatusAndUser(StatusPedido.valueOf(status.toUpperCase()),
				SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", status);
		return "usuario/home";
	}

//	 se ouver algum erro, entra no metodo abaixo
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/usuario/home";
	}

	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user,
				java.sql.Date.valueOf(LocalDate.now().plusDays(1)));
		passwordTokenRepository.save(myToken);
	}

	private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
		String url = contextPath + "/usuario/mudarSenha?token=" + token;
		String message = "Link para mudança de senha";
		return constructEmail("Mudar Senha", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, User user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

	private String validatePasswordResetToken(String token) {
		PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
		System.out.println("token: " + token);

		return !isTokenFound(passToken) ? "invalidToken" : isTokenExpired(passToken) ? "expired" : null;
	}

	private boolean isTokenFound(PasswordResetToken passToken) {
		return passToken != null;
	}

	private boolean isTokenExpired(PasswordResetToken passToken) {
		final Calendar cal = Calendar.getInstance();
		return passToken.getExpiryDate().before(cal.getTime());
	}
	
	private void changeUserPassword(User user, String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode(password);
	    user.setPassword(encode);
	    userRepository.save(user);
	}


}
