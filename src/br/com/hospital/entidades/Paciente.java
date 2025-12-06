package br.com.hospital.entidades;

import br.com.hospital.utilitarios.Utilitarios;

public class Paciente extends Pessoa {
    private int idade;
    private String principalQueixa;

    public Paciente(String id, String nome, String cpf, String telefone, String email,
                    String endereco, String senha, int idade, String principalQueixao) {
        super(id, nome, cpf, telefone, email, endereco, senha, "PACIENTE");
        this.idade = idade;
        this.principalQueixa = principalQueixa;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getprincipalQueixa() {
        return principalQueixa;
    }

    public void setprincipalQueixa(String principalQueixa) {
        this.principalQueixa = principalQueixa;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        Utilitarios.println("Idade: " + idade);
        Utilitarios.println("Principal queixa: " + principalQueixa);
    }
}
