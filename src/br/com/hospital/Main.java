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

import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hospital hospital = new Hospital();

        GerenciadorMedico gerMedico = new GerenciadorMedico(hospital, sc);
        GerenciadorPaciente gerPaciente = new GerenciadorPaciente(hospital, sc);
        GerenciadorConsulta gerConsulta = new GerenciadorConsulta(sc, hospital);

        // Usuário padrão para login inicial
        Funcionario funcionarioPadrao = new Funcionario(
                "FUNC-1", "Recepcionista Admin", "11122233344",
                "(85)99999-0000", "admin@hosp.com", "Av X",
                "admin", "Geral", "Administrador", "Geral"
        );

        List<Pessoa> usuarios = new ArrayList<>();
        usuarios.add(funcionarioPadrao);

        Login login = new Login(usuarios); // Sistema de login

        Println("---SISTEMA DE GERENCIAMENTO HOSPITALAR---\n");

        while (true) { // Loop principal do sistema
            boolean autenticado = false;

            // LOOP LOGIN
            while (!autenticado) {
                Println("---LOGIN---\n");
                Print("CPF: ");
                String cpf = sc.nextLine();
                Print("Senha: ");
                String senha = sc.nextLine();

                try {
                    if (login.autenticar(cpf, senha)) { // Tenta autenticar
                        autenticado = true;
                        Println("Login realizado.\n");
                    }
                } catch (LoginException e) {
                    Println(e.getMessage() + "\n"); // Erros de login
                }
            }

            // MENU PRINCIPAL
            int opcaoPrincipal = -1;
            while (opcaoPrincipal != 0 && opcaoPrincipal != 7) { // 0 = Fechar, 7 = logout

                Utilitarios.exibirMenuPrincipalAdmin();

                try {
                    opcaoPrincipal = Integer.parseInt(sc.nextLine()); // Lê opção
                } catch (Exception e) {
                    Println("Opção inválida.\n");
                    continue;
                }

                switch (opcaoPrincipal) {
                    case 1 -> { // MENU MÉDICO
                        int opcao = -1;
                        while (opcao != 0) {
                            Utilitarios.exibirMenuMedico();
                            try { opcao = Integer.parseInt(sc.nextLine()); } catch (Exception e) { continue; }

                            switch (opcao) {
                                case 1 -> gerMedico.cadastrarMedico(); // chama método de cadastro
                                case 2 -> gerMedico.editarMedico(); // chama método de edição
                                case 3 -> gerMedico.listarMedicos(); // lista médicos
                                case 4 -> gerMedico.removerMedico(); // remove médico
                                case 5 -> gerMedico.buscarMedico(); // busca médico
                                case 0 -> Println("Voltando ao menu principal...\n");
                                default -> Println("Opção inválida.\n");
                            }
                        }
                    }

                    case 2 -> { // MENU PACIENTE
                        int opcao = -1;
                        while (opcao != 0) {
                           Utilitarios.exibirMenuPaciente();
                            try { opcao = Integer.parseInt(sc.nextLine()); } catch (Exception e) { continue; }

                            switch (opcao) {
                                case 1 -> gerPaciente.cadastrarPaciente();
                                case 2 -> gerPaciente.editarPaciente();
                                case 3 -> gerPaciente.listarPacientes();
                                case 4 -> gerPaciente.removerPaciente();
                                case 5 -> gerPaciente.buscarPaciente();
                                case 0 -> Println("Voltando ao menu principal...\n");
                                default -> Println("Opção inválida.\n");
                            }
                        }
                    }

                    case 3 -> { // MENU CONSULTA
                        int opcao = -1;
                        while (opcao != 0) {
                            Utilitarios.exibirMenuConsulta();

                            try {
                                opcao = Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                Println("Opção inválida.\n");
                                continue;
                            }

                            switch (opcao) {
                                case 1 -> gerConsulta.agendar();
                                case 2 -> gerConsulta.cancelarAgendamento();
                                case 3 -> gerConsulta.listarConsultas();
                                case 4 -> gerConsulta.buscarConsulta();
                                case 0 -> Println("Voltando ao menu principal...\n");
                                default -> Println("Opção inválida.\n");
                            }
                        }
                    }

                    case 7 -> Println("\nVocê saiu da conta.\n"); // Logout

                    case 0 -> { // Sair do sistema
                        Println("Encerrando...");
                        sc.close();
                        return;
                    }

                    default -> Println("Opção inválida.\n");
                }
            }
        }
    }
}
