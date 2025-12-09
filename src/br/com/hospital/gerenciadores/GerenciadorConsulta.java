package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Consulta;
import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Paciente;
import br.com.hospital.interfaces.Agendavel;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.sistema.UsuarioSistema;
import br.com.hospital.enums.NivelAcesso;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static br.com.hospital.utilitarios.Utilitarios.*;

public class GerenciadorConsulta implements Agendavel {

    private final Hospital hospital;
    private final Scanner sc;

    public GerenciadorConsulta(Scanner sc, Hospital hospital) {
        this.sc = sc;
        this.hospital = hospital;
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private List<Paciente> getPacientes() {
        return hospital.getPessoas()
                .stream()
                .filter(p -> p instanceof Paciente)
                .map(p -> (Paciente) p)
                .toList();
    }

    private List<Medico> getMedicos() {
        return hospital.getPessoas()
                .stream()
                .filter(p -> p instanceof Medico)
                .map(p -> (Medico) p)
                .toList();
    }

    private Paciente escolherPaciente() {
        List<Paciente> pacientes = getPacientes();

        if (pacientes.isEmpty()) {
            Println("Nenhum paciente cadastrado.\n");
            return null;
        }

        Println("\n--- PACIENTES DISPONÍVEIS ---");
        for (int i = 0; i < pacientes.size(); i++) {
            Printf("%d - %s | CPF: %s\n", i + 1, pacientes.get(i).getNome(), pacientes.get(i).getCpf());
        }

        while (true) {
            Print("Escolha o paciente pelo número (0 para cancelar): ");
            String input = sc.nextLine();
            if (input.equals("0")) return null;

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < pacientes.size()) {
                    return pacientes.get(idx);
                }
            } catch (NumberFormatException ignored) {}

            Println("Opção inválida.");
        }
    }

    private Medico escolherMedico() {
        List<Medico> medicos = getMedicos();

        if (medicos.isEmpty()) {
            Println("Nenhum médico cadastrado.\n");
            return null;
        }

        Println("\n--- MÉDICOS DISPONÍVEIS ---");
        for (int i = 0; i < medicos.size(); i++) {
            Printf(
                    "%d - %s | CRM: %s | Especialidade: %s\n",
                    i + 1,
                    medicos.get(i).getNome(),
                    medicos.get(i).getCrm(),
                    medicos.get(i).getEspecialidade()
            );
        }

        while (true) {
            Print("Escolha o médico pelo número (0 para cancelar): ");
            String input = sc.nextLine();
            if (input.equals("0")) return null;

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < medicos.size()) {
                    return medicos.get(idx);
                }
            } catch (NumberFormatException ignored) {}

            Println("Opção inválida.");
        }
    }

    private boolean medicoOcupado(Medico m, String dataHora) {
        for (Consulta c : hospital.getConsultas()) {
            if (c.getMedico() != null &&
                    c.getMedico().getId().equals(m.getId()) &&
                    c.getDataHora().equals(dataHora)) {
                return true;
            }
        }
        return false;
    }

    // ---------------- AGENDAR CONSULTA ----------------

    @Override
    public void agendar() {
        Println("\n--- AGENDAR CONSULTA ---");
        Println("Digite 0 em qualquer etapa para cancelar a operação.\n");

        Paciente paciente = escolherPaciente();
        if (paciente == null) {
            Println("Agendamento cancelado.\n");
            return;
        }

        Medico medico = escolherMedico();
        if (medico == null) {
            Println("Agendamento cancelado.\n");
            return;
        }

        String dataHora = null;
        while (dataHora == null) {
            Print("Informe a data e hora (dd/MM/yyyy HH:mm) ou 0 para cancelar: ");
            String dh = sc.nextLine();
            if (dh.equals("0")) {
                Println("Agendamento cancelado.\n");
                return;
            }

            if (!dataHoraValida(dh)) {
                Println("Data/hora inválida.");
                continue;
            }

            if (!dataNoFuturo(dh)) {
                Println("Não é permitido agendar para datas passadas.");
                continue;
            }

            if (medicoOcupado(medico, dh)) {
                Println("O médico já possui consulta nesse horário.");
                continue;
            }

            dataHora = dh;
        }

        Consulta consulta = new Consulta(
                gerarIdUnico(),
                paciente,
                medico,
                dataHora
        );

        if (!consulta.validar()) {
            Println("Erro: " + consulta.getMensagemValidacao());
            return;
        }

        // Confirmação antes de salvar
        Println("\nDeseja confirmar o agendamento da consulta?");
        Println("1 - Confirmar");
        Println("0 - Cancelar");
        while (true) {
            Print("Escolha: ");
            String opcao = sc.nextLine();
            if (opcao.equals("1")) {
                hospital.adicionarConsulta(consulta);
                Println("Consulta agendada com sucesso!\n");
                return;
            } else if (opcao.equals("0")) {
                Println("Agendamento cancelado.\n");
                return;
            } else {
                Println("Opção inválida! Digite 1 para confirmar ou 0 para cancelar.");
            }
        }
    }

    // ---------------- CANCELAR CONSULTA ----------------

    @Override
    public void cancelarAgendamento() {
        Println("\n--- CANCELAR CONSULTA ---");
        Println("Digite 0 para voltar a qualquer momento.\n");

        listarConsultas();

        while (true) {
            Print("Informe o número do protocolo (ou 0 para cancelar): ");
            String id = sc.nextLine();
            if (id.equals("0")) {
                Println("Operação cancelada.\n");
                return;
            }

            Consulta c = buscarPorId(id);
            if (c == null) {
                Println("Consulta não encontrada. Tente novamente.");
            } else {
                Println("\nDeseja realmente cancelar esta consulta?");
                Println("1 - Confirmar");
                Println("0 - Cancelar");
                while (true) {
                    Print("Escolha: ");
                    String opcao = sc.nextLine();
                    if (opcao.equals("1")) {
                        hospital.getConsultas().remove(c);
                        Println("\nConsulta cancelada com sucesso!");
                        Println("Paciente: " + c.getPaciente().getNome());
                        Println("Médico:   " + c.getMedico().getNome());
                        Println("Data/Hora: " + c.getDataHora() + "\n");
                        return;
                    } else if (opcao.equals("0")) {
                        Println("Cancelamento abortado.\n");
                        return;
                    } else {
                        Println("Opção inválida! Digite 1 para confirmar ou 0 para cancelar.");
                    }
                }
            }
        }
    }

    // ---------------- LISTAR CONSULTAS ----------------

    @Override
    public void listarConsultas() {
        List<Consulta> consultas = hospital.getConsultas();

        if (consultas.isEmpty()) {
            Println("Nenhuma consulta cadastrada.\n");
            return;
        }

        consultas.sort(Comparator.comparing(Consulta::getDataHora));

        Println("\n--- CONSULTAS AGENDADAS ---");
        consultas.forEach(Consulta::exibirResumo);
        Println("");
    }

    // ---------------- LISTAR CONSULTAS DO MÉDICO (INCLUINDO RETORNOS) ----------------

    public void listarConsultasPorMedico(Medico medicoLogado) {
        if (medicoLogado == null) {
            Println("Médico não logado.\n");
            return;
        }

        List<Consulta> consultasMedico = hospital.getConsultas().stream()
                .filter(c -> c.getMedico() != null &&
                        c.getMedico().getId().equals(medicoLogado.getId()))
                .sorted(Comparator.comparing(Consulta::getDataHora))
                .toList();

        if (consultasMedico.isEmpty()) {
            Println("Nenhuma consulta encontrada para este médico.\n");
            return;
        }

        Println("\n--- MINHAS CONSULTAS (INCLUINDO RETORNOS) ---");
        for (Consulta c : consultasMedico) {
            String tipo = c.getTipo() != null ? c.getTipo() : "CONSULTA";
            Printf("[%s]\n", tipo);
            c.exibirResumo();
        }
        Println("");
    }

    // ---------------- BUSCAR CONSULTA POR PACIENTE ----------------

    @Override
    public void buscarConsulta() {
        while (true) {
            Print("Informe o CPF do paciente (ou 0 para cancelar): ");
            String cpfBuscado = sc.nextLine().trim();
            if (cpfBuscado.equals("0")) {
                Println("Busca cancelada.\n");
                return;
            }

            boolean encontrado = false;

            for (Consulta c : hospital.getConsultas()) {
                String cpfPaciente = c.getPaciente().getCpf().trim();

                if (cpfPaciente.equals(cpfBuscado)) {
                    c.exibirResumo();
                    encontrado = true;
                }
            }

            if (!encontrado) {
                Println("Nenhuma consulta encontrada para o CPF informado. Tente novamente.\n");
            } else {
                break;
            }
        }
    }

    // ---------------- BUSCAR POR ID ----------------

    private Consulta buscarPorId(String id) {
        for (Consulta c : hospital.getConsultas()) {
            if (compararIdentificadores(c.getId(), id)) return c;
        }
        return null;
    }

    // ---------------- SOLICITAR RETORNO ----------------

    public void solicitarRetorno() {
        Println("\n=== AGENDAR RETORNO ===");
        Println("Digite 0 em qualquer etapa para cancelar a operação.\n");

        Paciente paciente = escolherPaciente();
        if (paciente == null) {
            Println("Operação cancelada.\n");
            return;
        }

        Medico medico = escolherMedico();
        if (medico == null) {
            Println("Operação cancelada.\n");
            return;
        }

        String dataHora = null;
        while (dataHora == null) {
            Print("Data e Hora do Retorno (dd/MM/yyyy HH:mm) ou 0 para cancelar: ");
            String dh = sc.nextLine();
            if (dh.equals("0")) {
                Println("Operação cancelada.\n");
                return;
            }

            if (dataHoraValida(dh) && dataNoFuturo(dh)) {
                if (!medicoOcupado(medico, dh)) {
                    dataHora = dh;
                } else {
                    Println("Erro: O médico já tem consulta neste horário.");
                }
            } else {
                Println("Erro: Data inválida ou no passado.");
            }
        }

        Consulta consultaRetorno = new Consulta(
                gerarIdUnico(),
                paciente,
                medico,
                dataHora
        );
        consultaRetorno.setTipo("RETORNO");

        // Confirmação antes de salvar o retorno
        Println("\nDeseja confirmar o agendamento do retorno?");
        Println("1 - Confirmar");
        Println("0 - Cancelar");
        while (true) {
            Print("Escolha: ");
            String opcao = sc.nextLine();
            if (opcao.equals("1")) {
                hospital.adicionarConsulta(consultaRetorno);
                Println("\nRetorno agendado com sucesso!");
                Printf("PACIENTE: %s\n", paciente.getNome());
                Printf("DATA:     %s\n", dataHora);
                return;
            } else if (opcao.equals("0")) {
                Println("Agendamento de retorno cancelado.\n");
                return;
            } else {
                Println("Opção inválida! Digite 1 para confirmar ou 0 para cancelar.");
            }
        }
    }
}
