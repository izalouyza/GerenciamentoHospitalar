package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import static br.com.hospital.utilitarios.Utilitarios.*;

public class Medico extends Pessoa implements Validavel {

    private final String crm;
    private String especialidade;

    private String mensagemValidacao = "";

    public Medico(String id, String nome, String cpf, String telefone, String email,
                  String endereco, String senha, String crm, String especialidade) {

        super(id, nome, cpf, telefone, email, endereco, senha, "MEDICO");

        this.crm = crm;
        this.especialidade = normalizarTexto(especialidade);
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = normalizarTexto(especialidade);
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        Println("CRM: " + crm);
        Println("Especialidade: " + especialidade);
    }

    // Implementação Validável
    @Override
    public boolean validar() {

        // Valida campos herdados de Pessoa
        if (!super.validar()) {
            mensagemValidacao = super.getMensagemValidacao();
            return false;
        }

        // Validação CRM
        if (crm == null || crm.isBlank()) {
            mensagemValidacao = "O CRM não pode ser vazio.";
            return false;
        }

        if (!crmValido(crm)) {
            mensagemValidacao = "CRM inválido. Use apenas números com 4 a 10 dígitos.";
            return false;
        }

        // Validação Especialidade
        if (especialidade == null || especialidade.isBlank()) {
            mensagemValidacao = "A especialidade não pode ser vazia.";
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
