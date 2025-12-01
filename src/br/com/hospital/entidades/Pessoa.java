package br.com.hospital.entidades;

import br.com.hospital.interfaces.Identificavel;
import br.com.hospital.interfaces.Acessavel;

public abstract class Pessoa implements Identificavel, Acessavel {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private String senha;
    private String nivelAcesso;

    public Pessoa(int id, String nome, String cpf, String telefone, String email, String endereco, String senha, String nivelAcesso) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }

    @Override
    public String getIdentificador() {
        return String.valueOf(id);
    }

    @Override
    public String getNivelAcesso() {
        return nivelAcesso;
    }

    @Override
    public boolean temPermissao(String acao) {
        if ("MEDICO".equals(nivelAcesso)) {
            return acao.equals("Ver Pacientes") || acao.equals("Criar Consulta");
        }

        if ("Recepcionista".equals(nivelAcesso)) {
            return acao.equals("Criar Consulta");
        }
        return false;
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

    public void setId(int id) {
        this.id = id;
    }

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
