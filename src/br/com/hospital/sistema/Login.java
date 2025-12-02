package br.com.hospital.sistema;

import br.com.hospital.entidades.Pessoa;
import br.com.hospital.exceptions.LoginException;

import java.util.List;

public class Login {

    private List<Pessoa> usuarios;
    private Pessoa usuarioLogado;

    public Login(List<Pessoa> usuarios) {
        this.usuarios = usuarios;
    }

    public boolean autenticar(String cpf, String senha) throws LoginException {
        if (cpf == null || senha == null) {
            throw new LoginException("CPF ou senha não podem ser nulos.");
        }

        for (Pessoa p : usuarios) {
            if (p.getCpf().equals(cpf) && p.getSenha().equals(senha)) {
                usuarioLogado = p;
                return true;
            }
        }

        throw new LoginException("CPF ou senha incorretos!");
    }

    public Pessoa getUsuarioLogado() {
        return usuarioLogado;
    }

    public String getNivelAcesso() {
        if (usuarioLogado != null) {
            return usuarioLogado.getNivelAcesso();
        }

        return null;
    }

    public boolean temPermissao(String acao) {
        if (usuarioLogado != null) {
            return usuarioLogado.temPermissao(acao);
        }

        return false;
    }

    public void logout() {
        usuarioLogado = null;
    }
}
