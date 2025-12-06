package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Consulta;
import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Paciente;
import br.com.hospital.interfaces.Agendavel;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.utilitarios.Utilitarios;

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

    // Métodos auxiliares para obter listas atualizadas
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
        // Selecionar paciente
        List<Paciente> pacientes = getPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado. Cadastre pacientes antes de agendar.");
            return;
        }

        Paciente pacienteSelecionado = null;
        while (pacienteSelecionado == null) {
            System.out.println("\n--- PACIENTES DISPONÍVEIS ---");
            for (int i = 0; i < pacientes.size(); i++) {
                System.out.printf("%d - %s | CPF: %s\n", i + 1, pacientes.get(i).getNome(), pacientes.get(i).getCpf());
            }
            System.out.print("Escolha o paciente pelo número da lista: ");
            String input = sc.nextLine();
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < pacientes.size()) {
                    pacienteSelecionado = pacientes.get(idx);
                } else {
                    System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }

        // Selecionar médico
        List<Medico> medicos = getMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado. Cadastre médicos antes de agendar.");
            return;
        }

        Medico medicoSelecionado = null;
        while (medicoSelecionado == null) {
            System.out.println("\n--- MÉDICOS DISPONÍVEIS ---");
            for (int i = 0; i < medicos.size(); i++) {
                System.out.printf("%d - %s | CRM: %s | Especialidade: %s\n",
                        i + 1, medicos.get(i).getNome(), medicos.get(i).getCrm(), medicos.get(i).getEspecialidade());
            }
            System.out.print("Escolha o médico pelo número da lista: ");
            String input = sc.nextLine();
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < medicos.size()) {
                    medicoSelecionado = medicos.get(idx);
                } else {
                    System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }

        // Escolher data/hora
        String dataHoraValida = null;
        while (dataHoraValida == null) {
            System.out.print("Informe a data e hora da consulta (dd/MM/yyyy HH:mm): ");
            String dh = sc.nextLine();
            if (Utilitarios.dataHoraValida(dh) && Utilitarios.dataNoFuturo(dh)) {
                dataHoraValida = dh;
            } else {
                System.out.println("Data inválida ou passada. Tente novamente.");
            }
        }

        // Gerar ID e criar consulta
        String id = Utilitarios.gerarIdUnico();
        Consulta consulta = new Consulta(id, pacienteSelecionado, medicoSelecionado, dataHoraValida);

        // Adicionar consulta no Hospital
        hospital.adicionarConsulta(consulta);
        System.out.println("Consulta agendada com sucesso!\n");
    }

    @Override
    public void cancelarAgendamento() {
        listarConsultas();
        System.out.print("Informe o ID da consulta que deseja cancelar: ");
        String id = sc.nextLine();

        Consulta c = buscarPorId(id);
        if (c == null) {
            System.out.println("Consulta não encontrada!\n");
            return;
        }

        hospital.getConsultas().remove(c);
        System.out.println("Consulta cancelada com sucesso!\n");
    }

    @Override
    public void listarConsultas() {
        List<Consulta> consultas = hospital.getConsultas();
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta cadastrada.\n");
            return;
        }

        consultas.sort(Comparator.comparing(Consulta::getDataHora));

        System.out.println("\n--- CONSULTAS AGENDADAS ---");
        for (Consulta c : consultas) {
            System.out.printf("ID: %s | Paciente: %s | Médico: %s | Data: %s\n",
                    c.getId(),
                    c.getPaciente().getNome(),
                    c.getMedico().getNome(),
                    c.getDataHora());
        }
        System.out.println();
    }

    @Override
    public void buscarConsulta() {
        System.out.print("Informe o nome do paciente: ");
        String nomeBusca = sc.nextLine().toLowerCase();

        boolean achou = false;
        for (Consulta c : hospital.getConsultas()) {
            if (c.getPaciente().getNome().toLowerCase().contains(nomeBusca)) {
                System.out.printf("ID: %s | Paciente: %s | Médico: %s | Data: %s\n",
                        c.getId(),
                        c.getPaciente().getNome(),
                        c.getMedico().getNome(),
                        c.getDataHora());
                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Nenhuma consulta encontrada para o paciente informado.\n");
        } else {
            System.out.println();
        }
    }

    private Consulta buscarPorId(String id) {
        for (Consulta c : hospital.getConsultas()) {
            if (Utilitarios.compararIdentificadores(c.getId(), id)) {
                return c;
            }
        }
        return null;
    }
}
