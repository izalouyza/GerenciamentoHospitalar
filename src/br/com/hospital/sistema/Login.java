package br.com.hospital.sistema;

import br.com.hospital.exceptions.LoginException;
import java.util.List;

public class Login {

    private final List<UsuarioSistema> usuarios;
    private UsuarioSistema usuarioLogado;

    public Login(List<UsuarioSistema> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuarioSistema autenticar(String login, String senha) throws LoginException {

        if (login == null || senha == null) {
            throw new LoginException("Usuário e senha não podem ser vazios.");
        }

        String loginDigitado = login.trim();
        String senhaDigitada = senha.trim();

        for (UsuarioSistema u : usuarios) {

            if (u.getUsuario().equals(loginDigitado)) {

                if (u.getSenha().equals(senhaDigitada)) {
                    usuarioLogado = u;
                    return u; // retorna o objeto com nível de acesso
                } else {
                    throw new LoginException("Senha incorreta.");
                }
            }
        }

        throw new LoginException("Usuário não encontrado.");
    }

    public UsuarioSistema getUsuarioLogado() {
        return usuarioLogado;
    }

    public void logout() {
        usuarioLogado = null;
    }
}