package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import br.com.hospital.utilitarios.Utilitarios;

public abstract class Pessoa implements Validavel {

    private String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private String senha;
    private String nivelAcesso;

    private String mensagemValidacao;

    public Pessoa(String id, String nome, String cpf, String telefone, String email,
                  String endereco, String senha, String nivelAcesso) {

        if (id == null || id.isBlank()) {
            this.id = Utilitarios.gerarIdUnico();
        } else {
            this.id = id;
        }

        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;

        this.mensagemValidacao = "";
    }

    // ------------ GETTERS E SETTERS COM VALIDAÇÃO PRÓPRIA --------------

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean setNome(String nome) {
        if (!Utilitarios.textoNaoVazio(nome)) {
            mensagemValidacao = "Nome não pode ser vazio.";
            return false;
        }
        this.nome = Utilitarios.capitalizarNome(nome);
        return true;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean setCpf(String cpf) {
        if (cpf == null) {
            mensagemValidacao = "CPF não pode ser nulo.";
            return false;
        }
        if (!Utilitarios.cpfValido(cpf)) {
            mensagemValidacao = "CPF inválido.";
            return false;
        }
        this.cpf = cpf;
        return true;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean setTelefone(String telefone) {
        if (telefone == null || !Utilitarios.telefoneValido(telefone)) {
            mensagemValidacao = "Telefone inválido.";
            return false;
        }
        this.telefone = telefone;
        return true;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (email == null || !Utilitarios.emailValido(email)) {
            mensagemValidacao = "Email inválido.";
            return false;
        }
        this.email = email;
        return true;
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

    public boolean setNivelAcesso(String nivelAcesso) {
        if (!Utilitarios.textoNaoVazio(nivelAcesso)) {
            mensagemValidacao = "Nível de acesso inválido.";
            return false;
        }
        this.nivelAcesso = nivelAcesso;
        return true;
    }

    // ------------------ VALIDAVEL ---------------------

    @Override
    public boolean validar() {
        if (!Utilitarios.textoNaoVazio(nome)) {
            mensagemValidacao = "Nome inválido.";
            return false;
        }
        if (!Utilitarios.cpfValido(cpf)) {
            mensagemValidacao = "CPF inválido.";
            return false;
        }
        if (!Utilitarios.telefoneValido(telefone)) {
            mensagemValidacao = "Telefone inválido.";
            return false;
        }
        if (!Utilitarios.emailValido(email)) {
            mensagemValidacao = "Email inválido.";
            return false;
        }
        mensagemValidacao = "";
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao;
    }

    // ------------------ PRINT ---------------------

    public String resumo() {
        return String.format("Código: %s | Nome: %s | CPF: %s", id, nome, cpf);
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
