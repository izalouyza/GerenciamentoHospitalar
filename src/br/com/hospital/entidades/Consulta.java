package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;

public class Consulta implements Validavel {
    private final String id;
    private Paciente paciente;
    private Medico medico;
    private String dataHora; // formato dd/MM/yyyy HH:mm

    private String mensagemValidacao = "";

    public Consulta(String id, Paciente paciente, Medico medico, String dataHora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
    }

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

    // -----------------------------------------------------
    //            IMPLEMENTAÇÃO DA INTERFACE VALIDAVEL
    // -----------------------------------------------------
    @Override
    public boolean validar() {
        if (id == null || id.isBlank()) {
            mensagemValidacao = "ID da consulta é inválido.";
            return false;
        }

        if (paciente == null) {
            mensagemValidacao = "Paciente não informado.";
            return false;
        }

        if (medico == null) {
            mensagemValidacao = "Médico não informado.";
            return false;
        }

        if (dataHora == null || dataHora.isBlank()) {
            mensagemValidacao = "Data da consulta não informada.";
            return false;
        }

        // Validar formato: dd/MM/yyyy HH:mm
        if (!dataHora.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
            mensagemValidacao = "Formato de data/hora inválido. Use dd/MM/yyyy HH:mm";
            return false;
        }

        mensagemValidacao = ""; // tudo certo
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao;
    }

    public void exibirResumo() {
        System.out.printf("""
                             Dados da consulta:
                             Protocolo: %s
                             Paciente: %s
                             Médico: %s
                             Data: %s
                             Dara: %s
                             Queixa do paciente: %s""",
                id, paciente.getNome(), medico.getNome(), dataHora, paciente.getPrincipalQueixa());
    }
}
