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

    // ------------------------------------------------------------
    // MÉTODOS AUXILIARES PARA OBTENÇÃO DE PACIENTES E MÉDICOS
    // ------------------------------------------------------------

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

    // ------------------------------------------------------------
    // MÉTODOS PARA SELEÇÃO DO PACIENTE E MÉDICO
    // ------------------------------------------------------------

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
            Print("Escolha o paciente pelo número: ");
            String input = sc.nextLine();

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
            Print("Escolha o médico pelo número: ");
            String input = sc.nextLine();

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < medicos.size()) {
                    return medicos.get(idx);
                }
            } catch (NumberFormatException ignored) {}

            Println("Opção inválida.");
        }
    }

    // ------------------------------------------------------------
    // VERIFICAR DISPONIBILIDADE DO MÉDICO
    // ------------------------------------------------------------

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

    // ------------------------------------------------------------
    // AGENDAR CONSULTA
    // ------------------------------------------------------------

    @Override
    public void agendar() {
        Paciente paciente = escolherPaciente();
        if (paciente == null) return;

        Medico medico = escolherMedico();
        if (medico == null) return;

        String dataHora = null;
        while (dataHora == null) {

            Print("Informe a data e hora (dd/MM/yyyy HH:mm): ");
            String dh = sc.nextLine();

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

        hospital.adicionarConsulta(consulta);
        Println("Consulta agendada com sucesso!\n");
    }

    // ------------------------------------------------------------
    // CANCELAR CONSULTA
    // ------------------------------------------------------------

    @Override
    public void cancelarAgendamento() {
        listarConsultas();

        Print("Informe o número do protocolo: ");
        String id = sc.nextLine();

        Consulta c = buscarPorId(id);

        if (c == null) {
            Println("Consulta não encontrada.\n");
            return;
        }

        hospital.getConsultas().remove(c);
        Println("Consulta cancelada com sucesso.\n");
    }

    // ------------------------------------------------------------
    // LISTAR TODAS AS CONSULTAS
    // ------------------------------------------------------------

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

    // ------------------------------------------------------------
    // LISTAR CONSULTAS DO MÉDICO LOGADO
    // ------------------------------------------------------------

    public void listarConsultasPorMedico(UsuarioSistema usuario) {
        if (usuario == null || usuario.getNivel() != NivelAcesso.MEDICO) {
            Println("Usuário logado não é médico.\n");
            return;
        }

        var consultasMedico = hospital.getConsultas().stream()
                .filter(c -> c.getMedico() != null &&
                        c.getMedico().getCredenciais() != null &&
                        usuario.getUsuario().equals(
                                c.getMedico().getCredenciais().getUsuario()
                        ))
                .sorted(Comparator.comparing(Consulta::getDataHora))
                .toList();

        if (consultasMedico.isEmpty()) {
            Println("Nenhuma consulta encontrada para este médico.\n");
            return;
        }

        Println("\n--- MINHAS CONSULTAS ---");
        for (Consulta c : consultasMedico) {
            c.exibirResumo();
        }
        Println("");
    }

    // ------------------------------------------------------------
    // BUSCAR CONSULTA POR NOME DO PACIENTE
    // ------------------------------------------------------------

    @Override
    public void buscarConsulta() {
        Print("Informe o nome do paciente: ");
        String nome = sc.nextLine().toLowerCase();

        boolean encontrado = false;

        for (Consulta c : hospital.getConsultas()) {
            if (c.getPaciente().getNome().toLowerCase().contains(nome)) {
                c.exibirResumo();
                encontrado = true;
            }
        }

        if (!encontrado) {
            Println("Nenhuma consulta encontrada.\n");
        }
    }

    // ------------------------------------------------------------
    // BUSCA POR ID
    // ------------------------------------------------------------

    private Consulta buscarPorId(String id) {
        for (Consulta c : hospital.getConsultas()) {
            if (compararIdentificadores(c.getId(), id)) return c;
        }
        return null;
    }

    // ------------------------------------------------------------
    // SOLICITAR RETORNO
    // ------------------------------------------------------------

    public void solicitarRetorno() {
        Println("\n=== SOLICITAÇÃO DE RETORNO ===");

        // 1. Escolhe o paciente
        Paciente paciente = escolherPaciente();

        if (paciente != null) {

            // pergunta o motivo (Ex: Trazer exames)
            Print("Motivo do retorno: ");
            String motivo = sc.nextLine();

            // pergunta a previsão (Ex: 15 dias)
            Print("Prazo sugerido (ex: 7 dias, 1 mês): ");
            String prazo = sc.nextLine();

            // imprime um "Ticket" de confirmação formatado
            Println("\n------------------------------------------------");
            Println("       COMPROVANTE DE SOLICITAÇÃO DE RETORNO      ");
            Println("------------------------------------------------");
            Printf(" PACIENTE: %s\n", paciente.getNome());
            Printf(" CPF:      %s\n", paciente.getCpf());
            Println("------------------------------------------------");
            Printf(" MOTIVO:   %s\n", motivo);
            Printf(" PRAZO:    %s\n", prazo);
            Println("------------------------------------------------");
            Println(" >>> Solicitação registrada com sucesso!");
            Println("------------------------------------------------\n");
        }
    }
}