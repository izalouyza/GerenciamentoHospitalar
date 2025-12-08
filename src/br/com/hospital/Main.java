package br.com.hospital;

import br.com.hospital.exceptions.LoginException;
import br.com.hospital.gerenciadores.GerenciadorConsulta;
import br.com.hospital.gerenciadores.GerenciadorMedico;
import br.com.hospital.gerenciadores.GerenciadorPaciente;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.sistema.Login;
import br.com.hospital.enums.NivelAcesso;
import br.com.hospital.sistema.UsuarioSistema;
import static br.com.hospital.utilitarios.Povoamento.*;
import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Hospital hospital = new Hospital();

        // Lista de usuários
        List<UsuarioSistema> usuarios = new ArrayList<>();
        usuariosTeste(usuarios);
        carregarmedicos (hospital, usuarios);
        carregarpacientes(hospital);// médico padrão de teste

        Login login = new Login(usuarios);

        GerenciadorMedico gerMedico = new GerenciadorMedico(hospital, sc, usuarios);
        GerenciadorPaciente gerPaciente = new GerenciadorPaciente(hospital, sc);
        GerenciadorConsulta gerConsulta = new GerenciadorConsulta(sc, hospital);

        Println("\n--- SISTEMA HOSPITALAR ---\n");

        while (true) {

            UsuarioSistema usuarioLogado = null;

            // =====================================================================
            // MENU INICIAL (LOGIN OU ENCERRAR)
            // =====================================================================
            int escolhaInicial = -1;

            while (escolhaInicial != 0 && escolhaInicial != 1) {
                Printf("""
                        
                        --- MENU INICIAL ---
                        
                        1. Fazer login
                        0. Encerrar sistema
                        
                        Escolha uma opção:\t""");

                try {
                    escolhaInicial = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    Println("Opção inválida.\n");
                    continue;
                }

                if (escolhaInicial == 0) {
                    Println("Sistema finalizado.");
                    sc.close();
                    return;
                }
            }

            // =====================================================================
            // LOGIN
            // =====================================================================
            while (usuarioLogado == null) {

                Println("--- LOGIN ---\n");
                Print("Usuário (digite 0 para voltar):\t");
                String loginDigitado = sc.nextLine();

                // VOLTAR PARA O MENU INICIAL
                if (loginDigitado.equals("0")) {
                    usuarioLogado = null;
                    break;
                }

                Print("Senha:\t");
                String senhaDigitada = sc.nextLine();

                try {
                    usuarioLogado = login.autenticar(loginDigitado, senhaDigitada);
                    Println("\nLogin realizado!\n");
                } catch (LoginException e) {
                    Println(e.getMessage() + "\n");
                }
            }

            // Voltou ao menu inicial
            if (usuarioLogado == null) {
                continue;
            }

            // =====================================================================
            // MENU PRINCIPAL (DE ACORDO COM O NÍVEL DO USUÁRIO LOGADO)
            // =====================================================================
            NivelAcesso nivel = usuarioLogado.getNivel();
            int opcaoPrincipal = -1;

            while (opcaoPrincipal != 0) {

                switch (nivel) {
                    case ADMIN -> exibirMenuPrincipalAdmin();
                    case SECRETARIA -> exibirMenuPrincipalFuncionario();
                    case MEDICO -> exibirMenuPrincipalMedico();
                }

                try {
                    opcaoPrincipal = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    Println("Opção inválida.\n");
                    continue;
                }

                // ADMIN
                if (nivel == NivelAcesso.ADMIN) {
                    switch (opcaoPrincipal) {
                        case 1 -> menuMedico(sc, gerMedico);
                        case 2 -> menuPaciente(sc, gerPaciente);
                        case 3 -> menuConsulta(sc, gerConsulta);
                        case 0 -> Println("Logout realizado.\n");
                        default -> Println("Opção inválida.\n");
                    }
                }

                // SECRETARIA
                else if (nivel == NivelAcesso.SECRETARIA) {
                    switch (opcaoPrincipal) {
                        case 1 -> menuPaciente(sc, gerPaciente);
                        case 2 -> menuConsulta(sc, gerConsulta);
                        case 0 -> Println("Logout realizado.\n");
                        default -> Println("Opção inválida.\n");
                    }
                }

                // MÉDICO
                else if (nivel == NivelAcesso.MEDICO) {
                    switch (opcaoPrincipal) {
                        case 1 -> gerConsulta.listarConsultasPorMedico(usuarioLogado);
                        case 2 -> gerConsulta.solicitarRetorno();
                        case 0 -> Println("Logout realizado.\n");
                        default -> Println("Opção inválida.\n");
                    }
                }
            }
        }
    }

    // =====================================================================
    // SUBMENUS
    // =====================================================================

    private static void menuMedico(Scanner sc, GerenciadorMedico gm) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuMedico();
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (opcao) {
                case 1 -> gm.cadastrarMedico();
                case 2 -> gm.editarMedico();
                case 3 -> gm.listarMedicos();
                case 4 -> gm.removerMedico();
                case 5 -> gm.buscarMedico();
                case 0 -> Println("Voltando...\n");
                default -> Println("Opção inválida.\n");
            }
        }
    }

    private static void menuPaciente(Scanner sc, GerenciadorPaciente gp) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPaciente();
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (opcao) {
                case 1 -> gp.cadastrarPaciente();
                case 2 -> gp.editarPaciente();
                case 3 -> gp.listarPacientes();
                case 4 -> gp.removerPaciente();
                case 5 -> gp.buscarPaciente();
                case 0 -> Println("Voltando...\n");
                default -> Println("Opção inválida.\n");
            }
        }
    }

    private static void menuConsulta(Scanner sc, GerenciadorConsulta gc) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuConsulta();
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (opcao) {
                case 1 -> gc.agendar();
                case 2 -> gc.cancelarAgendamento();
                case 3 -> gc.listarConsultas();
                case 4 -> gc.buscarConsulta();
                case 5 -> gc.solicitarRetorno();
                case 0 -> Println("Voltando...\n");
                default -> Println("Opção inválida.\n");
            }
        }
    }
}