package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import br.com.hospital.utilitarios.Utilitarios;

public class Paciente extends Pessoa implements Validavel {

    private int idade;
    private String principalQueixa;

    private String mensagemValidacao = ""; // Armazena mensagem de erro na validação

    public Paciente(String id, String nome, String cpf, String telefone, String email,
                    String endereco, String senha, int idade, String principalQueixa) {

        super(id, nome, cpf, telefone, email, endereco, senha, "PACIENTE"); // Define nível de acesso como PACIENTE
        this.idade = idade;
        this.principalQueixa = Utilitarios.normalizarTexto(principalQueixa); // Remove acentos e caracteres indesejados
    }

    // ------------------- GETTERS E SETTERS ---------------------

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
        this.principalQueixa = Utilitarios.normalizarTexto(principalQueixa); // Sempre normaliza o texto
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes(); // Exibe informações comuns de Pessoa
        Utilitarios.println("Idade: " + idade);
        Utilitarios.println("Principal queixa: " + principalQueixa);
    }

    // Implementação da interface Validavel
    @Override
    public boolean validar() {

        if (!super.validar()) { // Valida atributos da classe Pessoa
            mensagemValidacao = super.getMensagemValidacao();
            return false;
        }

        if (idade <= 0) { // Idade deve ser positiva
            mensagemValidacao = "A idade deve ser maior que zero.";
            return false;
        }

        if (principalQueixa == null || principalQueixa.isBlank()) { // Queixa não pode estar vazia
            mensagemValidacao = "A principal queixa não pode ser vazia.";
            return false;
        }

        mensagemValidacao = ""; // Tudo ok
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao;
    }
}
