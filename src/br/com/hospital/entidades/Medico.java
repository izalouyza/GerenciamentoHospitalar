package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import br.com.hospital.sistema.UsuarioSistema;
import br.com.hospital.sistema.NivelAcesso;

import static br.com.hospital.utilitarios.Utilitarios.*;

public class Medico extends Pessoa implements Validavel {

    private final String crm;
    private String especialidade;

    private UsuarioSistema credenciais;  // LOGIN do médico
    private String mensagemValidacao = "";

    public Medico(
            String id,
            String nome,
            String cpf,
            String telefone,
            String email,
            String endereco,
            String crm,
            String especialidade,
            UsuarioSistema credenciais
    ) {
        super(id, nome, cpf, telefone, email, endereco);

        this.crm = crm;
        this.especialidade = normalizarTexto(especialidade);
        this.credenciais = credenciais;
    }

    // ----------------------------
    // Getters próprios da classe
    // ----------------------------

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public UsuarioSistema getCredenciais() {
        return credenciais;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        Println("CRM: " + crm);
        Println("Especialidade: " + especialidade);
    }

    // ----------------------------
    // Validação
    // ----------------------------

    @Override
    public boolean validar() {

        // Valida dados da classe Pessoa
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

        // Validação especialidade
        if (especialidade == null || especialidade.isBlank()) {
            mensagemValidacao = "A especialidade não pode ser vazia.";
            return false;
        }

        // Validação credenciais
        if (credenciais == null) {
            mensagemValidacao = "Credenciais de acesso não foram atribuídas ao médico.";
            return false;
        }

        if (credenciais.getNivel() != NivelAcesso.MEDICO) {
            mensagemValidacao = "Credenciais incompatíveis: o médico deve ter nível de acesso MÉDICO.";
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