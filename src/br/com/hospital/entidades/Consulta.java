package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import static br.com.hospital.utilitarios.Utilitarios.*;

public class Consulta implements Validavel {
    private final String id; // ID único da consulta
    private Paciente paciente; // paciente associado à consulta
    private Medico medico; // médico responsável
    private String dataHora; // formato dd/MM/yyyy HH:mm

    private String mensagemValidacao = ""; // armazena mensagens de erro na validação

    public Consulta(String id, Paciente paciente, Medico medico, String dataHora) {
        this.id = id; // ID é final, não muda depois
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
    }

    // ------------------- GETTERS E SETTERS ---------------------
    //getters
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

    // setters
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    //Implementação da interface Validavel
    @Override
    public boolean validar() {
        // Verifica se o ID é válido
        if (id == null || id.isBlank()) {
            mensagemValidacao = "ID da consulta é inválido.";
            return false;
        }

        // Confere se o paciente foi informado
        if (paciente == null) {
            mensagemValidacao = "Paciente não informado.";
            return false;
        }

        // Confere se o médico foi informado
        if (medico == null) {
            mensagemValidacao = "Médico não informado.";
            return false;
        }

        // Verifica se a data/hora foi informada
        if (dataHora == null || dataHora.isBlank()) {
            mensagemValidacao = "Data da consulta não informada.";
            return false;
        }

        // Valida o formato da data/hora: dd/MM/yyyy HH:mm
        if (!dataHora.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
            mensagemValidacao = "Formato de data/hora inválido. Use dd/MM/yyyy HH:mm";
            return false;
        }

        mensagemValidacao = ""; // tudo certo
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao; // retorna a última mensagem de validação
    }

    public void exibirResumo() {

        // exibe informações resumidas da consulta
        Printf("""
                             Dados da consulta:
                             Protocolo: %s
                             Paciente: %s
                             Médico: %s
                             Data: %s
                             Queixa do paciente: %s""",
                id, paciente.getNome(), medico.getNome(), dataHora, paciente.getPrincipalQueixa());
    }
}

