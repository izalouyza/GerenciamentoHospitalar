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

    private String mensagemValidacao;

    public Pessoa(String id, String nome, String cpf,
                  String telefone, String email, String endereco) {

        // Se não enviar ID, gera automaticamente
        this.id = (id == null || id.isBlank()) ? gerarIdUnico() : id;

        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;

        this.mensagemValidacao = "";
    }

    // --------------------------
    // GETTERS
    // --------------------------

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    // --------------------------
    // Validação
    // --------------------------

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

        mensagemValidacao = "";
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao;
    }

    // --------------------------
    // Exibição
    // --------------------------

    public void exibirInformacoes() {
        Println("--------------------------------------------------");
        Println("ID: " + id);
        Println("Nome: " + nome);
        Println("CPF: " + cpf);
        Println("Telefone: " + telefone);
        Println("Email: " + email);
        Println("Endereço: " + endereco);
        Println("--------------------------------------------------");
    }
}