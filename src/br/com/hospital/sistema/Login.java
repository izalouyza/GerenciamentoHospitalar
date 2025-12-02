package br.com.hospital.sistema;

import br.com.hospital.entidades.Funcionario;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.exceptions.LoginException;

import java.util.List;

public class Login {
    private final List<Pessoa> usuarios;
    private Pessoa usuarioLogado;

    public Login(List<Pessoa> usuarios) {
        this.usuarios = usuarios;
    }

    // autentica apenas funcionários
    public boolean autenticar(String cpf, String senha) throws LoginException {
        for (Pessoa p : usuarios) {
            if (p instanceof Funcionario &&
                    p.getCpf().equals(cpf) &&
                    p.getSenha().equals(senha)) {
                usuarioLogado = p;
                return true;
            }
        }
        throw new LoginException("CPF ou senha incorretos (somente funcionário pode logar).");
    }

    public Pessoa getUsuarioLogado() {
        return usuarioLogado;
    }
    public void logout() {
        usuarioLogado = null;
    }
}


