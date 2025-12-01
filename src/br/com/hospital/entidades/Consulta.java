package br.com.hospital.entidades;

import br.com.hospital.exceptions.ConsultaException;
import br.com.hospital.interfaces.Agendavel;
import br.com.hospital.interfaces.Identificavel;
import br.com.hospital.interfaces.Validavel;
import br.com.hospital.exceptions.FuncionarioException;
import br.com.hospital.utilitarios.Utilitarios;

public class Consulta implements Identificavel, Agendavel, Validavel {

    private String id;
    private Paciente paciente;
    private Medico medico;
    private String dataHora;
    private String descricao;

    public Consulta(String id, Paciente paciente, Medico medico, String dataHora, String descricao)
        throws ConsultaException {

        //Exceções:
        if (!Utilitarios.textoNaoVazio(id)) {
            throw new ConsultaException("ID da consulta inválido.");
        }
        if (paciente == null) {
            throw new ConsultaException("Paciente não pode ser nulo.");
        }
        if (medico == null) {
            throw new ConsultaException("Médico não pode ser nulo.");
        }
        if (!Utilitarios.textoNaoVazio(dataHora)) {
            throw new ConsultaException("Data e hora da consulta inválidas.");
        }
        if (!Utilitarios.dataHoraValida(dataHora)) {
            throw new ConsultaException("Formato de data e hora inválido. Utilize: dd/MM/yyyy HH:mm");
        }
        if (!Utilitarios.textoNaoVazio(descricao)) {
            throw new ConsultaException("Descrição da consulta inválida.");
        }

        //  -------- Validações específicas de Consulta --------
        if (!paciente.validar()) {
            throw new ConsultaException("Paciente inválido: " + paciente.getMensagemValidacao());
        }
        if (!medico.validar()) {
            throw new ConsultaException("Médico inválido: " + medico.getMensagemValidacao());
        }

        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.descricao = descricao;
    }

    @Override
    public String getIdentificador() {
        return id;
    }

    @Override
    public void agendar(String dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public void cancelarAgendamento() {
        this.dataHora = null;
    }

    @Override
    public String getDataHora() {
        return dataHora;
    }

    @Override
    public String getResumoAgendamento() {
        return "Consulta " + id + " - " + medico.getNome() + " com " + paciente.getNome() + " em " + dataHora;
    }

    @Override
    public boolean validar() {

        if (paciente == null || !paciente.validar()) return false;
        if (medico == null || !medico.validar()) return false;

        if (dataHora == null || dataHora.isBlank()) return false;

        return true;
    }

    @Override
    public String getMensagemValidacao() {
        if (paciente == null) return "Paciente inválido.";
        if (!paciente.validar()) return paciente.getMensagemValidacao();

        if (medico == null) return "Médico inválido.";
        if (!medico.validar()) return medico.getMensagemValidacao();

        if (dataHora == null || dataHora.isBlank()) return "Data e hora inválidas.";

        return "Consulta válida.";
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
