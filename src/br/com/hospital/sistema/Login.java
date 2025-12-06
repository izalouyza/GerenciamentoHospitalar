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

    // Login permitido SOMENTE para funcionários (inclui médicos)
    public boolean autenticar(String cpf, String senha) throws LoginException {

        if (cpf == null || senha == null) {
            throw new LoginException("CPF e senha não podem ser vazios.");
        }

        // remove letras, espaços, pontos e traços do CPF digitado
        String cpfDigitado = cpf.replaceAll("\\D", "");
        String senhaDigitada = senha.trim();

        for (Pessoa p : usuarios) {

            if (p instanceof Funcionario) {

                String cpfPessoa = p.getCpf().replaceAll("\\D", "");

                if (cpfPessoa.equals(cpfDigitado)) {

                    if (p.getSenha().equals(senhaDigitada)) {
                        usuarioLogado = p;
                        return true;
                    } else {
                        throw new LoginException("Senha incorreta.");
                    }
                }
            }
        }

        throw new LoginException("CPF não encontrado ou usuário não é funcionário.");
    }

    public Pessoa getUsuarioLogado() {
        return usuarioLogado;
    }

    public void logout() {
        usuarioLogado = null;
    }
}
