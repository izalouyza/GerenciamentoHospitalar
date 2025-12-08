package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import static br.com.hospital.utilitarios.Utilitarios.*;

// Agora a classe define um contrato abstrato para exibição
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
        this.id = (id == null || id.isBlank()) ? gerarIdUnico() : id;
        this.nome = nome;
        this.cpf = cpf;
        setTelefone(telefone);
        setEmail(email);
        this.endereco = endereco;
        this.mensagemValidacao = "";
    }

    // ------------------------------------------
    //  SETTERS COM VALIDAÇÃO
    // ------------------------------------------

    public void setTelefone(String novoTelefone) {
        if (telefoneValido(novoTelefone)) {
            this.telefone = novoTelefone;
        } else {
            Println("Erro: O formato do telefone é inválido e não foi atualizado.");
        }
    }

    public void setEmail(String novoEmail) {
        if (emailValido(novoEmail)) {
            this.email = novoEmail;
        } else {
            Println("Erro: O formato do email é inválido e não foi atualizado.");
        }
    }

    // --------------------------
    // GETTERS
    // --------------------------
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }

    // --------------------------
    // MÉTODOS DE EXIBIÇÃO
    // --------------------------

    // Obriga as filhas (Medico, Paciente) a implementarem sua própria versão.
    public abstract void exibirInformacoes();

    // Método auxiliar para não duplicar código nas filhas
    protected void exibirDadosBasicos() {
        Println("--------------------------------------------------");
        Println("ID: " + id);
        Println("Nome: " + nome);
        Println("CPF: " + cpf);
        Println("Telefone: " + telefone);
        Println("Email: " + email);
        Println("Endereço: " + endereco);
    }

    // --------------------------
    // Validação
    // --------------------------
    @Override
    public boolean validar() {
        if (!textoNaoVazio(nome)) { mensagemValidacao = "Nome inválido."; return false; }
        if (!cpfValido(cpf)) { mensagemValidacao = "CPF inválido."; return false; }
        if (!telefoneValido(telefone)) { mensagemValidacao = "Telefone inválido."; return false; }
        if (!emailValido(email)) { mensagemValidacao = "Email inválido."; return false; }
        mensagemValidacao = "";
        return true;
    }

    @Override
    public String getMensagemValidacao() { return mensagemValidacao; }
}