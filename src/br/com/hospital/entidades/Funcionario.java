package br.com.hospital.entidades;

import br.com.hospital.utilitarios.Utilitarios;

public class Funcionario extends Pessoa {

    private String cargo;
    private String setor;

    private String mensagemValidacao = "";

    public Funcionario(String id, String nome, String cpf, String telefone, String email,
                       String endereco, String senha, String nivelAcesso,
                       String cargo, String setor) {

        super(id, nome, cpf, telefone, email, endereco, senha, nivelAcesso);
        this.cargo = cargo;
        this.setor = setor;
    }

    // ------------------- GETTERS E SETTERS ---------------------

    public String getCargo() {
        return cargo;
    }

    public boolean setCargo(String cargo) {
        if (cargo == null || cargo.isBlank()) {
            mensagemValidacao = "O cargo não pode ser vazio.";
            return false;
        }
        this.cargo = cargo;
        return true;
    }

    public String getSetor() {
        return setor;
    }

    public boolean setSetor(String setor) {
        if (setor == null || setor.isBlank()) {
            mensagemValidacao = "O setor não pode ser vazio.";
            return false;
        }
        this.setor = setor;
        return true;
    }

    // ------------------ PRINT ---------------------

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        Utilitarios.println("Cargo: " + cargo);
        Utilitarios.println("Setor: " + setor);
    }

    // -----------------------------------------------------
    //               MÉTODOS DE VALIDAÇÃO
    // -----------------------------------------------------
    @Override
    public boolean validar() {

        if (!super.validar()) {
            mensagemValidacao = super.getMensagemValidacao();
            return false;
        }

        if (cargo == null || cargo.isBlank()) {
            mensagemValidacao = "O cargo não pode ser vazio.";
            return false;
        }

        if (setor == null || setor.isBlank()) {
            mensagemValidacao = "O setor não pode ser vazio.";
            return false;
        }

        mensagemValidacao = "";
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao;
    }
}
