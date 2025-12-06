package br.com.hospital;

import br.com.hospital.entidades.Funcionario;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.exceptions.LoginException;
import br.com.hospital.gerenciadores.GerenciadorConsulta;
import br.com.hospital.gerenciadores.GerenciadorMedico;
import br.com.hospital.gerenciadores.GerenciadorPaciente;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.sistema.Login;
import br.com.hospital.utilitarios.Utilitarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hospital hospital = new Hospital();

        // Gerenciadores
        GerenciadorMedico gerMedico = new GerenciadorMedico(hospital, sc);
        GerenciadorPaciente gerPaciente = new GerenciadorPaciente(hospital, sc);
        GerenciadorConsulta gerConsulta = new GerenciadorConsulta(sc, hospital);

        // Usuário padrão
        Funcionario funcionarioPadrao = new Funcionario(
                "FUNC-1", "Recepcionista Admin", "11122233344",
                "(85)99999-0000", "recep@hosp.com", "Av X",
                "admin", "RECEP", "Recepção", "Recepção"
        );

        List<Pessoa> usuarios = new ArrayList<>();
        usuarios.add(funcionarioPadrao);

        Login login = new Login(usuarios);

        Utilitarios.println("---SISTEMA DE GERENCIAMENTO HOSPITALAR---\n");

        while (true) {
            boolean autenticado = false;

            // LOGIN
            while (!autenticado) {
                Utilitarios.println("---LOGIN---\n");
                Utilitarios.print("CPF: ");
                String cpf = sc.nextLine();
                Utilitarios.print("Senha: ");
                String senha = sc.nextLine();

                try {
                    if (login.autenticar(cpf, senha)) {
                        autenticado = true;
                        Utilitarios.println("Login realizado.\n");
                    }
                } catch (LoginException e) {
                    Utilitarios.println(e.getMessage() + "\n");
                }
            }

            // MENU PRINCIPAL
            int opcaoPrincipal = -1;
            while (opcaoPrincipal != 0 && opcaoPrincipal != 7) {
                Utilitarios.println("---- MENU PRINCIPAL ----");
                Utilitarios.println("1. Médico");
                Utilitarios.println("2. Paciente");
                Utilitarios.println("3. Consulta");
                Utilitarios.println("7. Logout");
                Utilitarios.println("0. Sair");
                Utilitarios.print("Escolha uma opção: ");

                try {
                    opcaoPrincipal = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    Utilitarios.println("Opção inválida.\n");
                    continue;
                }

                switch (opcaoPrincipal) {
                    case 1 -> { // MÉDICO
                        int opcao = -1;
                        while (opcao != 0) {
                            Utilitarios.println("\n--- MENU MÉDICO ---");
                            Utilitarios.println("1. Cadastrar Médico");
                            Utilitarios.println("2. Editar Médico");
                            Utilitarios.println("3. Listar Médicos");
                            Utilitarios.println("4. Remover Médico");
                            Utilitarios.println("5. Buscar Médico");
                            Utilitarios.println("0. Voltar");
                            Utilitarios.print("Escolha uma opção: ");
                            try { opcao = Integer.parseInt(sc.nextLine()); } catch (Exception e) { continue; }

                            switch (opcao) {
                                case 1 -> gerMedico.cadastrarMedico();
                                case 2 -> gerMedico.editarMedico();
                                case 3 -> gerMedico.listarMedicos();
                                case 4 -> gerMedico.removerMedico();
                                case 5 -> gerMedico.buscarMedico();
                                case 0 -> Utilitarios.println("Voltando ao menu principal...\n");
                                default -> Utilitarios.println("Opção inválida.\n");
                            }
                        }
                    }

                    case 2 -> { // PACIENTE
                        int opcao = -1;
                        while (opcao != 0) {
                            Utilitarios.println("\n--- MENU PACIENTE ---");
                            Utilitarios.println("1. Cadastrar Paciente");
                            Utilitarios.println("2. Editar Paciente");
                            Utilitarios.println("3. Listar Pacientes");
                            Utilitarios.println("4. Remover Paciente");
                            Utilitarios.println("5. Buscar Paciente");
                            Utilitarios.println("0. Voltar");
                            Utilitarios.print("Escolha uma opção: ");
                            try { opcao = Integer.parseInt(sc.nextLine()); } catch (Exception e) { continue; }

                            switch (opcao) {
                                case 1 -> gerPaciente.cadastrarPaciente();
                                case 2 -> gerPaciente.editarPaciente();
                                case 3 -> gerPaciente.listarPacientes();
                                case 4 -> gerPaciente.removerPaciente();
                                case 5 -> gerPaciente.buscarPaciente();
                                case 0 -> Utilitarios.println("Voltando ao menu principal...\n");
                                default -> Utilitarios.println("Opção inválida.\n");
                            }
                        }
                    }

                    case 3 -> { // CONSULTA
                        int opcao = -1;
                        while (opcao != 0) {
                            Utilitarios.println("\n--- MENU CONSULTA ---");
                            Utilitarios.println("1. Agendar Consulta");
                            Utilitarios.println("2. Cancelar Consulta");
                            Utilitarios.println("3. Listar Consultas");
                            Utilitarios.println("4. Buscar Consulta por Paciente");
                            Utilitarios.println("0. Voltar");
                            Utilitarios.print("Escolha uma opção: ");

                            try {
                                opcao = Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                Utilitarios.println("Opção inválida.\n");
                                continue;
                            }

                            switch (opcao) {
                                case 1 -> gerConsulta.agendar();
                                case 2 -> gerConsulta.cancelarAgendamento();
                                case 3 -> gerConsulta.listarConsultas();
                                case 4 -> gerConsulta.buscarConsulta();
                                case 0 -> Utilitarios.println("Voltando ao menu principal...\n");
                                default -> Utilitarios.println("Opção inválida.\n");
                            }
                        }
                    }

                    case 7 -> Utilitarios.println("\nVocê saiu da conta.\n");

                    case 0 -> {
                        Utilitarios.println("Encerrando...");
                        sc.close();
                        return;
                    }

                    default -> Utilitarios.println("Opção inválida.\n");
                }
            }
        }
    }
}
