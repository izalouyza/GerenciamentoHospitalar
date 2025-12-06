package br.com.hospital.entidades;

import br.com.hospital.utilitarios.Utilitarios;

//Trocar essa classe para Admin, a qual sera herdada pela classe Secretaria

public class Funcionario extends Pessoa {
    private String cargo;
    private String setor;

    public Funcionario(String id, String nome, String cpf, String telefone, String email,
                       String endereco, String senha, String nivelAcesso, String cargo, String setor) {
        super(id, nome, cpf, telefone, email, endereco, senha, nivelAcesso);
        this.cargo = cargo;
        this.setor = setor;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSetor() {
        return setor;
    }
    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        Utilitarios.println("Cargo: " + cargo);
        Utilitarios.println("Setor: " + setor);
        
    }
}
