package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Consulta;
import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Paciente;
import br.com.hospital.interfaces.Agendavel;
import br.com.hospital.sistema.Hospital;
import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorConsulta implements Agendavel {

    private final Hospital hospital;
    private final Scanner sc;

    public GerenciadorConsulta(Scanner sc, Hospital hospital) {
        this.sc = sc;
        this.hospital = hospital;
    }

    private List<Paciente> getPacientes() {
        return hospital.getPessoas().stream()
                .filter(p -> p instanceof Paciente)
                .map(p -> (Paciente) p)
                .toList();
    }

    private List<Medico> getMedicos() {
        return hospital.getPessoas().stream()
                .filter(p -> p instanceof Medico)
                .map(p -> (Medico) p)
                .toList();
    }

    @Override
    public void agendar() {
        List<Paciente> pacientes = getPacientes();
        if (pacientes.isEmpty()) {
            Println("Nenhum paciente cadastrado. Cadastre pacientes antes de agendar.");
            return;
        }

        Paciente pacienteSelecionado = null;
        while (pacienteSelecionado == null) {
            Println("\n--- PACIENTES DISPONÍVEIS ---");
            for (int i = 0; i < pacientes.size(); i++) {
                System.out.printf("%d - %s | CPF: %s\n", i + 1, pacientes.get(i).getNome(), pacientes.get(i).getCpf());
            }

            Print("Escolha o paciente pelo número da lista: ");
            String input = sc.nextLine();

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < pacientes.size()) {
                    pacienteSelecionado = pacientes.get(idx);
                } else {
                    Println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                Println("Digite um número válido.");
            }
        }

        List<Medico> medicos = getMedicos();
        if (medicos.isEmpty()) {
            Println("Nenhum médico cadastrado. Cadastre médicos antes de agendar.");
            return;
        }

        Medico medicoSelecionado = null;
        while (medicoSelecionado == null) {
            Println("\n--- MÉDICOS DISPONÍVEIS ---");
            for (int i = 0; i < medicos.size(); i++) {
                System.out.printf("%d - %s | CRM: %s | Especialidade: %s\n",
                        i + 1,
                        medicos.get(i).getNome(),
                        medicos.get(i).getCrm(),
                        medicos.get(i).getEspecialidade()
                );
            }

            Print("Escolha o médico pelo número da lista: ");
            String input = sc.nextLine();

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < medicos.size()) {
                    medicoSelecionado = medicos.get(idx);
                } else {
                    Println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                Println("Digite um número válido.");
            }
        }

        String dataHoraValida = null;
        while (dataHoraValida == null) {
            Print("Informe a data e hora da consulta (dd/MM/yyyy HH:mm): ");
            String dh = sc.nextLine();

            boolean dataValida = dataHoraValida(dh);
            boolean dataFutura = dataNoFuturo(dh);

            if (!dataValida || !dataFutura) {
                Println("Data inválida ou passada. Tente novamente.");
                continue;
            }

            boolean horarioOcupado = false;
            for (Consulta c : hospital.getConsultas()) {
                if (c.getMedico().getId().equals(medicoSelecionado.getId()) &&
                        c.getDataHora().equals(dh)) {
                    horarioOcupado = true;
                }
            }

            if (horarioOcupado) {
                Println("Este médico já possui uma consulta marcada neste horário.\nTente outro horário.");
            } else {
                dataHoraValida = dh;
            }
        }

        String id = gerarIdUnico();
        Consulta consulta = new Consulta(id, pacienteSelecionado, medicoSelecionado, dataHoraValida);

        hospital.adicionarConsulta(consulta);
        Println("Consulta agendada com sucesso!\n");
    }

    @Override
    public void cancelarAgendamento() {
        listarConsultas();
        Print("Informe o número do protocolo da consulta que deseja cancelar: ");
        String id = sc.nextLine();

        Consulta c = buscarPorId(id);
        if (c == null) {
            Println("Consulta não encontrada!\n");
            return;
        }

        hospital.getConsultas().remove(c);
        Println("Consulta cancelada com sucesso!\n");
    }

    @Override
    public void listarConsultas() {
        List<Consulta> consultas = hospital.getConsultas();
        if (consultas.isEmpty()) {
            Println("Nenhuma consulta cadastrada.\n");
            return;
        }

        consultas.sort(Comparator.comparing(Consulta::getDataHora));

        Println("\n--- CONSULTAS AGENDADAS ---");
        for (Consulta c : consultas) {
            c.exibirResumo();
        }
        System.out.println();
    }

    @Override
    public void buscarConsulta() {
        Print("Informe o nome do paciente: ");
        String nomeBusca = sc.nextLine().toLowerCase();

        boolean achou = false;

        for (Consulta c : hospital.getConsultas()) {
            if (c.getPaciente().getNome().toLowerCase().contains(nomeBusca)) {
                c.exibirResumo(); // <<--- USANDO EXIBIR RESUMO
                achou = true;
            }
        }

        if (!achou) {
            Println("Nenhuma consulta encontrada para o paciente informado.\n");
        } else {
            System.out.println();
        }
    }

    private Consulta buscarPorId(String id) {
        for (Consulta c : hospital.getConsultas()) {
            if (compararIdentificadores(c.getId(), id)) {
                return c;
            }
        }
        return null;
    }
}
