package br.com.hospital.entidades;

import br.com.hospital.exceptions.PessoaException;
import br.com.hospital.interfaces.Validavel;
import br.com.hospital.exceptions.MedicoException;
import br.com.hospital.utilitarios.Utilitarios;
import br.com.hospital.exceptions.PessoaException;

public class Medico extends Pessoa implements Validavel {
    private String crm;
    private String especialidade;

    public Medico(int id, String nome, String cpf, String telefone, String email, String endereco,String senha, String nivelAcesso, String crm, String especialidade)
        throws MedicoException, PessoaException {

        //Exceções:
        if (!Utilitarios.textoNaoVazio(nome)) {
            throw new MedicoException("Nome inválido.");
        }

        if (!Utilitarios.cpfValido(cpf)) {
            throw new MedicoException("CPF inválido.");
        }

        if (!Utilitarios.telefoneValido(telefone)) {
            throw new MedicoException("Telefone inválido.");
        }

        if (!Utilitarios.emailValido(email)) {
            throw new MedicoException("E-mail inválido.");
        }

        if (!Utilitarios.textoNaoVazio(endereco)) {
            throw new MedicoException("Endereço inválido.");
        }

        if (!Utilitarios.textoNaoVazio(senha) || senha.length() < 4) {
            throw new MedicoException("Senha inválida. Mínimo de 4 caracteres.");
        }

        if (!Utilitarios.textoNaoVazio(nivelAcesso)) {
            throw new MedicoException("Nível de acesso inválido.");
        }

        // -------- Validações específicas do Médico --------
        if (!Utilitarios.textoNaoVazio(crm)) {
            throw new MedicoException("CRM não pode ser vazio.");
        }

        if (crm.contains(" ")) {
            throw new MedicoException("CRM não pode conter espaços.");
        }

        if (!Utilitarios.crmValido(crm)) {
            throw new MedicoException("CRM inválido. Deve ter entre 4 e 10 caracteres.");
        }

        if (!Utilitarios.textoNaoVazio(especialidade)) {
            throw new MedicoException("Especialidade inválida.");
        }

        if (especialidade.length() < 3) {
            throw new MedicoException("Especialidade deve ter ao menos 3 caracteres.");
        }

        super(id, nome, cpf, telefone, email, endereco,senha, nivelAcesso);

        this.crm = crm;
        this.especialidade = especialidade;

    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.printf("""
                
                Dados profissionais:
                CRM: %s
                Especialidade: %s
                """, getCrm(), getEspecialidade());
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean validar() {
        if (getNome() == null || getNome().isBlank()) {
            return false;
        }

        if (getCpf() == null || getCpf().length() != 11) {
            return false;
        }

        if (crm == null || crm.isBlank()) {
            return false;
        }

        if (crm.length() < 4 || crm.length() > 10) {
            return false;
        }

        if (especialidade == null || especialidade.isBlank()) {
            return false;
        }
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        if (getNome() == null || getNome().isBlank()) {
            return "Nome inválido.";
        }

        if (getCpf() == null || getCpf().length() != 11) {
            return "CPF inválido. Deve conter 11 dígitos.";
        }

        if (crm == null || crm.isBlank()) {
            return "CRM não pode ser vazio.";
        }

        if (crm.length() < 4 || crm.length() > 10) {
            return "CRM inválido. Deve ter entre 4 e 10 caracteres.";
        }

        if (especialidade == null || especialidade.isBlank()) {
            return "Especialidade não pode ser vazia.";
        }
        return "Médico válido.";
    }
}
