package br.com.hospital.entidades;

import br.com.hospital.utilitarios.Utilitarios;

public abstract class Pessoa {
    private final String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private String senha;
    private String nivelAcesso;

    public Pessoa(String id, String nome, String cpf, String telefone, String email,
                  String endereco, String senha, String nivelAcesso) {
        this.id = (id == null || id.isBlank()) ? Utilitarios.gerarIdUnico() : id;
        this.nome = Utilitarios.capitalizarNome(nome);
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (!Utilitarios.textoNaoVazio(nome)) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = Utilitarios.capitalizarNome(nome);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
         if (cpf == null || !Utilitarios.cpfValido(cpf)) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || !Utilitarios.telefoneValido(telefone)) {
            throw new IllegalArgumentException("Telefone inválido.");
        }
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !Utilitarios.emailValido(email)) {
            throw new IllegalArgumentException("Email inválido.");
        }
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        if (!Utilitarios.textoNaoVazio(nivelAcesso)) {
            throw new IllegalArgumentException("Nível de acesso inválido.");
        }
        this.nivelAcesso = nivelAcesso;
    }

    public String resumo() {
        return String.format("Código: %s | Nome:  %s | CPF: %s", id, nome, cpf);
    }

    public void exibirInformacoes() {
        Utilitarios.println("--------------------------------------------------");
        Utilitarios.println("ID: " + id);
        Utilitarios.println("Nome: " + nome);
        Utilitarios.println("CPF: " + cpf);
        Utilitarios.println("Telefone: " + telefone);
        Utilitarios.println("Email: " + email);
        Utilitarios.println("Endereço: " + endereco);
        Utilitarios.println("Nível Acesso: " + nivelAcesso);
        Utilitarios.println("--------------------------------------------------");
    }
}
