package br.com.alterdata.vendas.config;

public class MessageConstants {

    private MessageConstants() {
        throw new IllegalStateException("Class utilitária!");
    }

    public static final String MSG_AUTH_UNAUTHORIZED="Não autorizado";
    public static final String MSG_USER_NOT_FOUND = "Usuário não encontrado";
    public static final String MSG_AUTH_LOGIN_NOT_FOUND="Login não encontrado";
    public static final String MSG_AUTH_INVALID_PASSWORD="Senha inválida";

    public static final String MSG_VALIDATION_ERROR="Erro de validação";

    public static final String MSG_INVALID_PARAMS="Erro de validação";

    public static final String MSG_PRODUTO_NOT_FOUND="Produto não encontrado";

    public static final String MSG_CATEGORIA_NOT_FOUND="Categoria não encontrada";

}
