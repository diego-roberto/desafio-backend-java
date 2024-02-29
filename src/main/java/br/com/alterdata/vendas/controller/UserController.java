package br.com.alterdata.vendas.controller;

import br.com.alterdata.vendas.dto.LoginDTO;
import br.com.alterdata.vendas.dto.TokenDTO;
import br.com.alterdata.vendas.dto.UserDTO;
import br.com.alterdata.vendas.exception.InvalidPasswordException;
import br.com.alterdata.vendas.model.User;
import br.com.alterdata.vendas.security.jwt.JwtService;
import br.com.alterdata.vendas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@RequestBody @Valid UserDTO user){
        String encrypted = encoder.encode(user.getPassword());
        user.setPassword(encrypted);
        return userService.save(user);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public TokenDTO authenticate(@RequestBody @Valid LoginDTO dto){
        try {
            UserDetails authUser = userService.authenticate(dto);

            User user = User.builder()
                    .login(authUser.getUsername())
                    .password(authUser.getPassword())
                    .build();

            String token = jwtService.generateToken(user);
            return new TokenDTO(user.getLogin(), token);

        }catch (UsernameNotFoundException | InvalidPasswordException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

}
