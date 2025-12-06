package br.com.hospital.interfaces;

import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Paciente;

public interface Agendavel {

    void agendar();

    void cancelarAgendamento();

    void listarConsultas();

    void buscarConsulta();
}
