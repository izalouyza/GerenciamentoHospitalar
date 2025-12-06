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

    private final Hospital hospital; // referência ao hospital, para acessar pacientes, médicos e consultas
    private final Scanner sc;

    public GerenciadorConsulta(Scanner sc, Hospital hospital) {
        this.sc = sc;
        this.hospital = hospital;
    }

    // Métodos auxiliares para obter listas atualizadas de pacientes e médicos
    private List<Paciente> getPacientes() {
        return hospital.getPessoas().stream()
                .filter(p -> p instanceof Paciente) // filtra apenas Pacientes
                .map(p -> (Paciente) p) // faz cast para Paciente
                .toList();
    }

    private List<Medico> getMedicos() {
        return hospital.getPessoas().stream()
                .filter(p -> p instanceof Medico) // filtra apenas Médicos
                .map(p -> (Medico) p)
                .toList();
    }

    @Override
    public void agendar() {
        // Selecionar paciente
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
                    pacienteSelecionado = pacientes.get(idx); // paciente escolhido corretamente
                } else {
                    Println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                Println("Digite um número válido.");
            }
        }

        // Selecionar médico
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
                    medicoSelecionado = medicos.get(idx); // médico escolhido corretamente
                } else {
                    Println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                Println("Digite um número válido.");
            }
        }

        // Escolher data/hora + verificar conflito de horário
        String dataHoraValida = null;

        while (dataHoraValida == null) {
            Print("Informe a data e hora da consulta (dd/MM/yyyy HH:mm): ");
            String dh = sc.nextLine();

            boolean dataValida = dataHoraValida(dh); // verifica formato da data
            boolean dataFutura = dataNoFuturo(dh); // garante que a data seja futura

            if (!dataValida || !dataFutura) {
                Println("Data inválida ou passada. Tente novamente.");
                continue;
            }

            // Verificar se médico já tem consulta nesse horário
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
                dataHoraValida = dh; // horário está livre
            }
        }

        // Gerar ID e criar consulta
        String id = gerarIdUnico();
        Consulta consulta = new Consulta(id, pacienteSelecionado, medicoSelecionado, dataHoraValida);

        // Adicionar consulta no Hospital
        hospital.adicionarConsulta(consulta);
        Println("Consulta agendada com sucesso!\n");
    }

    @Override
    public void cancelarAgendamento() {
        listarConsultas(); // mostra todas as consultas antes de cancelar
        Print("Informe o ID da consulta que deseja cancelar: ");
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

        consultas.sort(Comparator.comparing(Consulta::getDataHora)); // ordena por data/hora

        Println("\n--- CONSULTAS AGENDADAS ---");
        for (Consulta c : consultas) {
            System.out.printf(
                    "ID: %s | Paciente: %s | Médico: %s | Data: %s\n",
                    c.getId(),
                    c.getPaciente().getNome(),
                    c.getMedico().getNome(),
                    c.getDataHora()
            );
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
                System.out.printf(
                        "ID: %s | Paciente: %s | Médico: %s | Data: %s\n",
                        c.getId(),
                        c.getPaciente().getNome(),
                        c.getMedico().getNome(),
                        c.getDataHora()
                );
                achou = true;
            }
        }

        if (!achou) {
            Println("Nenhuma consulta encontrada para o paciente informado.\n");
        } else {
            System.out.println();
        }
    }

    private Consulta buscarPorId(String id) { // busca consulta pelo ID
        for (Consulta c : hospital.getConsultas()) {
            if (compararIdentificadores(c.getId(), id)) {
                return c;
            }
        }
        return null;
    }
}
