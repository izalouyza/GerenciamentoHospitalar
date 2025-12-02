package br.com.hospital.entidades;

import br.com.hospital.interfaces.Identificavel;
import br.com.hospital.interfaces.Acessavel;
import br.com.hospital.exceptions.PessoaException;
import br.com.hospital.utilitarios.Utilitarios;

public abstract class Pessoa implements Identificavel, Acessavel {

    private final int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private String senha;
    private String nivelAcesso;

    public Pessoa(int id, String nome, String cpf, String telefone, String email,
                  String endereco, String senha, String nivelAcesso)
            throws PessoaException {

        // -------- Validações básicas --------
        if (id <= 0) {
            throw new PessoaException("ID inválido.");
        }
        if (!Utilitarios.textoNaoVazio(nome)) {
            throw new PessoaException("Nome inválido.");
        }
        if (!Utilitarios.cpfValido(cpf)) {
            throw new PessoaException("CPF inválido.");
        }
        if (!Utilitarios.telefoneValido(telefone)) {
            throw new PessoaException("Telefone inválido.");
        }
        if (!Utilitarios.emailValido(email)) {
            throw new PessoaException("E-mail inválido.");
        }
        if (!Utilitarios.textoNaoVazio(endereco)) {
            throw new PessoaException("Endereço inválido.");
        }
        if (!Utilitarios.textoNaoVazio(senha) || senha.length() < 4) {
            throw new PessoaException("Senha inválida. Mínimo de 4 caracteres.");
        }
        if (!Utilitarios.textoNaoVazio(nivelAcesso)) {
            throw new PessoaException("Nível de acesso inválido.");
        }

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso.toUpperCase(); // padronização
    }

    @Override
    public String getIdentificador() {
        return String.valueOf(id);
    }

    @Override
    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        if (Utilitarios.textoNaoVazio(nivelAcesso)) {
            this.nivelAcesso = nivelAcesso.toUpperCase();
        }
    }

    @Override
    public boolean temPermissao(String acao) {
        // Todas as comparações agora são coerentes
        switch (nivelAcesso) {
            case "MEDICO":
                return acao.equals("VER PACIENTES") ||
                        acao.equals("CRIAR CONSULTA");

            case "RECEPCIONISTA":
                return acao.equals("CRIAR CONSULTA");

            case "ADMIN":
                return true; // se existir admin no seu sistema

            default:
                return false;
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    // Removido setId() propositalmente (ID deve ser imutável)

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void exibirInformacoes() {
        System.out.printf("""
                        Dados pessoais:
                        Id: %d
                        Nome: %s
                        CPF: %s
                        Telefone: %s
                        Email: %s
                        Endereco: %s
                        """,
                id, nome, cpf, telefone, email, endereco);
    }
}
