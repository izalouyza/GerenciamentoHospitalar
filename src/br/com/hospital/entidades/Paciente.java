package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import static br.com.hospital.utilitarios.Utilitarios.*;

public class Paciente extends Pessoa implements Validavel {

    private int idade;
    private String principalQueixa;

    private String mensagemValidacao = "";

    public Paciente(
            String id,
            String nome,
            String cpf,
            String telefone,
            String email,
            String endereco,
            int idade,
            String principalQueixa
    ) {
        // Pessoa já não recebe mais senha ou nível de acesso
        super(id, nome, cpf, telefone, email, endereco);

        this.idade = idade;
        this.principalQueixa = normalizarTexto(principalQueixa);
    }

    // -------------------
    // GETTERS
    // -------------------

    public int getIdade() {
        return idade;
    }

    public String getPrincipalQueixa() {
        return principalQueixa;
    }

    // -------------------
    // Exibição
    // -------------------

    @Override
    public void exibirInformacoes() {
        super.exibirDadosBasicos(); // Mudou aqui
        Println("Idade: " + idade);
        Println("Principal queixa: " + principalQueixa);
        Println("--------------------------------------------------");
    }

    // -------------------
    // Validação
    // -------------------

    @Override
    public boolean validar() {

        // Valida dados da classe Pessoa
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