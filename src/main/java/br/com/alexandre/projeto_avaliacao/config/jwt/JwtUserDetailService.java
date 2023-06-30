package br.com.alexandre.projeto_avaliacao.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.alexandre.projeto_avaliacao.domain.Teacher;
import br.com.alexandre.projeto_avaliacao.repositories.TeacherRepository;

@Component
public class JwtUserDetailService implements UserDetailsService {

	@Autowired
	private TeacherRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Teacher teacher = repository.findByEmail(email).orElseThrow(null);
		return User.builder().username(teacher.getName()).password(encoder.encode(teacher.getPassword()))
				.roles(teacher.getRole().split(",")).build();
	}
}
