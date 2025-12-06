package br.com.hospital.sistema;

import br.com.hospital.entidades.Funcionario;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.exceptions.LoginException;

import java.util.List;

public class Login {

    private final List<Pessoa> usuarios; // lista de todos os usuários do sistema
    private Pessoa usuarioLogado; // armazena o usuário que está logado no momento

    public Login(List<Pessoa> usuarios) {
        this.usuarios = usuarios;
    }

    // Autenticação
    // Login permitido somente para funcionários (inclui médicos)
    public boolean autenticar(String cpf, String senha) throws LoginException {

        if (cpf == null || senha == null) { // valida entrada
            throw new LoginException("CPF e senha não podem ser vazios.");
        }

        // remove letras, espaços, pontos e traços do CPF digitado
        String cpfDigitado = cpf.replaceAll("\\D", "");
        String senhaDigitada = senha.trim(); // remove espaços extras

        for (Pessoa p : usuarios) {

            if (p instanceof Funcionario) { // apenas funcionários podem logar

                String cpfPessoa = p.getCpf().replaceAll("\\D", "");

                if (cpfPessoa.equals(cpfDigitado)) { // verifica CPF

                    if (p.getSenha().equals(senhaDigitada)) { // verifica senha
                        usuarioLogado = p; // login bem-sucedido
                        return true;
                    } else {
                        throw new LoginException("Senha incorreta."); // senha inválida
                    }
                }
            }
        }

        // CPF não encontrado ou usuário não é funcionário
        throw new LoginException("CPF não encontrado ou usuário não é funcionário.");
    }

    /* método não utilizado

    public Pessoa getUsuarioLogado() {
        return usuarioLogado; // retorna usuário logado
    }

     */

    /* método não utilizado

    public void logout() {
        usuarioLogado = null; // efetua logout
    }

     */
}
