package br.com.alterdata.vendas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    private boolean admin;
}
