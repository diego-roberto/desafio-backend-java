package br.com.alterdata.vendas.service;

import br.com.alterdata.vendas.config.MessageConstants;
import br.com.alterdata.vendas.config.RoleConstants;
import br.com.alterdata.vendas.dto.LoginDTO;
import br.com.alterdata.vendas.dto.UserDTO;
import br.com.alterdata.vendas.exception.BusinessException;
import br.com.alterdata.vendas.exception.InvalidPasswordException;
import br.com.alterdata.vendas.model.User;
import br.com.alterdata.vendas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder encoder;

    @Transactional
    public UserDTO save(UserDTO dto) {
        User user;
        user = User.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .admin(dto.isAdmin())
                .build();

        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(MessageConstants.MSG_AUTH_LOGIN_NOT_FOUND));

        String[] roles = user.isAdmin() ?
                new String[]{RoleConstants.ADMIN, RoleConstants.USER} : new String[]{RoleConstants.USER};

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    public UserDetails authenticate(LoginDTO dto) throws InvalidPasswordException {
        UserDetails loadedUser = loadUserByUsername(dto.getLogin());
        boolean match = encoder.matches(dto.getPassword(), loadedUser.getPassword());
        if(match){
            return loadedUser;
        }
        throw new InvalidPasswordException(MessageConstants.MSG_AUTH_INVALID_PASSWORD);
    }

    public List<UserDTO> findAll(){
        List<User> users = userRepository.findAll();

        if (!users.isEmpty()) {
            return users.stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new BusinessException(MessageConstants.MSG_USER_NOT_FOUND);
        }
    }
}
