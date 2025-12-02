package br.com.hospital.entidades;

import br.com.hospital.interfaces.Agendavel;
import br.com.hospital.interfaces.Identificavel;
import br.com.hospital.interfaces.Validavel;

public class Consulta implements Identificavel, Agendavel, Validavel {

    private String id;
    private Paciente paciente;
    private Medico medico;
    private String dataHora;
    private String descricao;
    private boolean cancelada = false; // Evita quebrar validação apagando a data

    public Consulta(String id, Paciente paciente, Medico medico, String dataHora, String descricao) {
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
        this.cancelada = false; // Reagendar remove o estado de cancelado
    }

    @Override
    public void cancelarAgendamento() {
        this.cancelada = true; // Marca como cancelada sem apagar data
    }

    @Override
    public String getDataHora() {
        return dataHora;
    }

    @Override
    public String getResumoAgendamento() {
        if (cancelada) {
            return "Consulta " + id + " foi cancelada.";
        }

        return "Consulta " + id + " - " + medico.getNome()
                + " com " + paciente.getNome() + " em " + dataHora;
    }

    @Override
    public boolean validar() {

        if (paciente == null || !paciente.validar()) return false;
        if (medico == null || !medico.validar()) return false;

        if (!cancelada) { // Só valida data se não estiver cancelada
            if (dataHora == null || dataHora.isBlank()) return false;
        }

        // Descrição agora é OPCIONAL (não há validação aqui)

        return true;
    }

    @Override
    public String getMensagemValidacao() {
        if (paciente == null) return "Paciente inválido.";
        if (!paciente.validar()) return paciente.getMensagemValidacao();

        if (medico == null) return "Médico inválido.";
        if (!medico.validar()) return medico.getMensagemValidacao();

        if (!cancelada && (dataHora == null || dataHora.isBlank()))
            return "Data e hora inválidas.";

        // Descrição opcional → nenhuma validação aqui

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

    public boolean isCancelada() {
        return cancelada;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
        this.cancelada = false; // Alterou data → deixa de ser cancelada
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
