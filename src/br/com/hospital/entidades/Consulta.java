package br.com.hospital.entidades;

import br.com.hospital.utilitarios.Utilitarios;

public class Consulta {
    private final String id;
    private Paciente paciente;
    private Medico medico;
    private String dataHora; // formato dd/MM/yyyy HH:mm

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


    public void exibirResumo() {
        System.out.printf("""
                             Dados da consulta:
                             Protocolo: %s
                             Paciente: %s
                             Médico: %s
                             Data: %s
                             Dara: %s
                             Queixa do paciente: %s""",
                id, paciente.getNome(), medico.getNome(), dataHora, paciente.getprincipalQueixa());
    }
}
