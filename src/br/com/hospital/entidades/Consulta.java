package br.com.hospital.entidades;

public class Consulta {
    private final String id;
    private Paciente paciente;
    private Medico medico;
    private String dataHora; // formato dd/MM/yyyy HH:mm
    private String descricao;

    public Consulta(String id, Paciente paciente, Medico medico, String dataHora, String descricao) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.descricao = descricao;
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

    public void exibirResumo() {
        System.out.printf("Consulta %s | Paciente: %s | Médico: %s | %s | %s%n",
                id, paciente.getNome(), medico.getNome(), dataHora, descricao);
    }
}
