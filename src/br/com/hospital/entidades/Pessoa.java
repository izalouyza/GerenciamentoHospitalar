package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import static br.com.hospital.utilitarios.Utilitarios.*;

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

        // Se o id não for informado, gera um único automaticamente
        if (id == null || id.isBlank()) {
            this.id = gerarIdUnico();
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

        this.mensagemValidacao = ""; // inicia mensagem de validação vazia
    }

    // ------------ GETTERS E SETTERS COM VALIDAÇÃO PRÓPRIA --------------

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean setNome(String nome) {
        if (!textoNaoVazio(nome)) {
            mensagemValidacao = "Nome não pode ser vazio."; // valida se o nome não está vazio
            return false;
        }
        this.nome = capitalizarNome(nome); // normaliza e capitaliza o nome
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
        if (!cpfValido(cpf)) {
            mensagemValidacao = "CPF inválido."; // valida CPF com método utilitário
            return false;
        }
        this.cpf = cpf;
        return true;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean setTelefone(String telefone) {
        if (telefone == null || !telefoneValido(telefone)) {
            mensagemValidacao = "Telefone inválido."; // valida telefone
            return false;
        }
        this.telefone = telefone;
        return true;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (email == null || !emailValido(email)) {
            mensagemValidacao = "Email inválido."; // valida email
            return false;
        }
        this.email = email;
        return true;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco; // sem validação, apenas define
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha; // define senha
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public boolean setNivelAcesso(String nivelAcesso) {
        if (!textoNaoVazio(nivelAcesso)) {
            mensagemValidacao = "Nível de acesso inválido."; // valida nível de acesso
            return false;
        }
        this.nivelAcesso = nivelAcesso;
        return true;
    }

    // Implementação da interface Validavel
    @Override
    public boolean validar() {
        if (!textoNaoVazio(nome)) {
            mensagemValidacao = "Nome inválido.";
            return false;
        }
        if (!cpfValido(cpf)) {
            mensagemValidacao = "CPF inválido.";
            return false;
        }
        if (!telefoneValido(telefone)) {
            mensagemValidacao = "Telefone inválido.";
            return false;
        }
        if (!emailValido(email)) {
            mensagemValidacao = "Email inválido.";
            return false;
        }
        mensagemValidacao = ""; // tudo ok
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao;
    }

    // Retorna um resumo da pessoa
    public String resumo() {
        return String.format("Código: %s | Nome: %s | CPF: %s", id, nome, cpf);
    }

    // Exibe informações completas
    public void exibirInformacoes() {
        Println("--------------------------------------------------");
        Println("ID: " + id);
        Println("Nome: " + nome);
        Println("CPF: " + cpf);
        Println("Telefone: " + telefone);
        Println("Email: " + email);
        Println("Endereço: " + endereco);
        Println("Nível Acesso: " + nivelAcesso);
        Println("--------------------------------------------------");
    }
}
