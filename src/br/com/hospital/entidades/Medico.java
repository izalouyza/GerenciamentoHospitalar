package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import br.com.hospital.exceptions.MedicoException;
import br.com.hospital.utilitarios.Utilitarios;

public class Medico extends Pessoa implements Validavel {
    private String crm;
    private String especialidade;

    public Medico(int id, String nome, String cpf, String telefone, String email, String endereco,String senha, String nivelAcesso,
                  String crm, String especialidade) {
        super(id, nome, cpf, telefone, email, endereco,senha, nivelAcesso);

        //Exceções:
        try {
            // ----- CRM -----
            if (crm == null || crm.isBlank()) {
                throw new MedicoException("CRM não pode ser vazio.");
            }
            if (crm.length() < 4 || crm.length() > 10) {
                throw new MedicoException("CRM deve ter entre 4 e 10 caracteres.");
            }
            if (crm.contains(" ")) {
                throw new MedicoException("CRM não pode conter espaços.");
            }

            // ----- Especialidade -----
            if (especialidade == null || especialidade.isBlank()) {
                throw new MedicoException("Especialidade inválida.");
            }
            if (especialidade.length() < 3) {
                throw new MedicoException("Especialidade deve ter no mínimo 3 caracteres.");
            }

            // ----- Demais -----
            if (getNome() == null || getNome().isBlank()) {
                throw new MedicoException("Nome não pode ser vazio.");
            }
            if (!Utilitarios.cpfValido(getCpf())) {
                throw new MedicoException("CPF inválido.");
            }
            if (!Utilitarios.telefoneValido(getTelefone())) {
                throw new MedicoException("Telefone inválido.");
            }
            if (!Utilitarios.emailValido(getEmail())) {
                throw new MedicoException("Email inválido.");
            }

        this.crm = crm;
        this.especialidade = especialidade;

        } catch (MedicoException e) {
            System.out.println("Erro ao criar médico: " + e.getMessage());
        }
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
