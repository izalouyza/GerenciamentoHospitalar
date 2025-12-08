package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import static br.com.hospital.utilitarios.Utilitarios.*;

public class Consulta implements Validavel {

    private final String id;       // ID único da consulta
    private Paciente paciente;     // paciente associado
    private Medico medico;         // médico responsável
    private String dataHora;       // formato dd/MM/yyyy HH:mm

    private String mensagemValidacao = "";

    public Consulta(String id, Paciente paciente, Medico medico, String dataHora) {

        // Se ID não for informado, gerar automaticamente
        this.id = (id == null || id.isBlank()) ? gerarIdUnico() : id;

        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
    }

    // -------------------
    // GETTERS E SETTERS
    // -------------------

    public String getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getDataHora() {
        return dataHora;
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

    // -------------------
    // Validação
    // -------------------

    @Override
    public boolean validar() {

        if (paciente == null) {
            mensagemValidacao = "Paciente não informado.";
            return false;
        }

        if (medico == null) {
            mensagemValidacao = "Médico não informado.";
            return false;
        }

        if (dataHora == null || dataHora.isBlank()) {
            mensagemValidacao = "Data/hora da consulta não informada.";
            return false;
        }

        // Formato dd/MM/yyyy HH:mm
        if (!dataHora.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
            mensagemValidacao = "Formato inválido! Use: dd/MM/yyyy HH:mm";
            return false;
        }

        mensagemValidacao = "";
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao;
    }

    // -------------------
    // Exibição
    // -------------------

    public void exibirResumo() {

        Printf("""
                ========================
                Dados da consulta:
                ------------------------
                Protocolo: %s
                Data e Hora: %s
                ------------------------
                Paciente: %s
                Idade: %d
                Queixa: %s
                ------------------------
                Médico: %s
                Especialidade: %s
                ========================
                """,
                id,
                dataHora,

                // paciente
                paciente.getNome(),
                paciente.getIdade(),
                paciente.getPrincipalQueixa(),

                // médico
                medico.getNome(),
                medico.getEspecialidade()
        );
    }
}