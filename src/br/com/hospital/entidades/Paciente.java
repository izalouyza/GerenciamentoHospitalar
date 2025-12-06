package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import br.com.hospital.utilitarios.Utilitarios;

public class Paciente extends Pessoa implements Validavel {

    private int idade;
    private String principalQueixa;

    private String mensagemValidacao = "";

    public Paciente(String id, String nome, String cpf, String telefone, String email,
                    String endereco, String senha, int idade, String principalQueixa) {

        super(id, nome, cpf, telefone, email, endereco, senha, "PACIENTE");
        this.idade = idade;
        this.principalQueixa = Utilitarios.normalizarTexto(principalQueixa);
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPrincipalQueixa() {
        return principalQueixa;
    }

    public void setPrincipalQueixa(String principalQueixa) {
        this.principalQueixa = Utilitarios.normalizarTexto(principalQueixa);
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        Utilitarios.println("Idade: " + idade);
        Utilitarios.println("Principal queixa: " + principalQueixa);
    }

    // Implementação do Validavel
    @Override
    public boolean validar() {

        if (!super.validar()) {
            mensagemValidacao = super.getMensagemValidacao();
            return false;
        }

        if (idade <= 0) {
            mensagemValidacao = "A idade deve ser maior que zero.";
            return false;
        }

        if (principalQueixa == null || principalQueixa.isBlank()) {
            mensagemValidacao = "A principal queixa não pode ser vazia.";
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
