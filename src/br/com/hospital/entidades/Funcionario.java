package br.com.hospital.entidades;

import static br.com.hospital.utilitarios.Utilitarios.*;

public class Funcionario extends Pessoa {

    private String cargo; // cargo do funcionário
    private String setor; // setor do funcionário

    private String mensagemValidacao = ""; // mensagem para validação

    public Funcionario(String id, String nome, String cpf, String telefone, String email,
                       String endereco, String senha, String nivelAcesso,
                       String cargo, String setor) {

        super(id, nome, cpf, telefone, email, endereco, senha, nivelAcesso);
        this.cargo = cargo;
        this.setor = setor;
    }

    // ------------------- GETTERS E SETTERS ---------------------
    /* métodos não utilizados:


    //getters
    public String getCargo() {
        return cargo;
    }

    public boolean setCargo(String cargo) {
        if (cargo == null || cargo.isBlank()) { // valida se cargo é nulo ou vazio
            mensagemValidacao = "O cargo não pode ser vazio.";
            return false;
        }
        this.cargo = cargo;
        return true;
    }

    public String getSetor() {
        return setor;
    }

    //setters
    public boolean setSetor(String setor) {
        if (setor == null || setor.isBlank()) { // valida se setor é nulo ou vazio
            mensagemValidacao = "O setor não pode ser vazio.";
            return false;
        }
        this.setor = setor;
        return true;
    }
    */

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes(); // chama exibição da classe Pessoa
        Println("Cargo: " + cargo);
        Println("Setor: " + setor);
    }

    // Métodos de validação
    @Override
    public boolean validar() {

        if (!super.validar()) { // valida atributos da classe pai
            mensagemValidacao = super.getMensagemValidacao(); // mantém mensagem de erro da classe pai
            return false;
        }

        if (cargo == null || cargo.isBlank()) { // valida cargo
            mensagemValidacao = "O cargo não pode ser vazio.";
            return false;
        }

        if (setor == null || setor.isBlank()) { // valida setor
            mensagemValidacao = "O setor não pode ser vazio.";
            return false;
        }

        mensagemValidacao = ""; // tudo válido
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao; // retorna mensagem de validação atual
    }
}
