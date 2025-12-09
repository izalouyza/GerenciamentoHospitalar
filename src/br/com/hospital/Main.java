package br.com.hospital;

import br.com.hospital.entidades.Pessoa;
import br.com.hospital.exceptions.LoginException;
import br.com.hospital.gerenciadores.GerenciadorConsulta;
import br.com.hospital.gerenciadores.GerenciadorFuncionario;
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
        carregarmedicos(hospital, usuarios);
        carregarpacientes(hospital);

        Login login = new Login(usuarios);

        GerenciadorMedico gerMedico = new GerenciadorMedico(hospital, sc, usuarios);
        GerenciadorPaciente gerPaciente = new GerenciadorPaciente(hospital, sc);
        GerenciadorConsulta gerConsulta = new GerenciadorConsulta(sc, hospital);
        GerenciadorFuncionario gerFuncionario= new GerenciadorFuncionario(hospital,sc,usuarios);

        Println("\n--- SISTEMA HOSPITALAR ---\n");

        while (true) {

            UsuarioSistema usuarioLogado = null;

            // =====================================================================
            // MENU INICIAL
            // =====================================================================
            int escolhaInicial = -1;

            while (escolhaInicial != 0 && escolhaInicial != 1) {
                limparTela();
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

                limparTela();
                Println("--- LOGIN ---\n");

                Print("Usuário (digite 0 para voltar):\t");
                String loginDigitado = sc.nextLine();

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

            if (usuarioLogado == null) {
                continue;
            }

            // =====================================================================
            // MENU PRINCIPAL
            // =====================================================================
            NivelAcesso nivel = usuarioLogado.getNivel();
            int opcaoPrincipal = -1;

            while (opcaoPrincipal != 0) {

                limparTela();
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

                if (nivel == NivelAcesso.ADMIN) {
                    switch (opcaoPrincipal) {
                        case 1 -> menuMedico(sc, gerMedico);
                        case 2 -> menuPaciente(sc, gerPaciente);
                        case 3 -> menuConsulta(sc, gerConsulta);
                        case 4 -> menuFuncionario(sc,gerFuncionario);
                        case 5 -> {buscaGeral(sc, hospital); pausar(sc);}
                        case 0 -> Println("Logout realizado.\n");
                        default -> Println("Opção inválida.\n");
                    }
                } else if (nivel == NivelAcesso.SECRETARIA) {
                    switch (opcaoPrincipal) {
                        case 1 -> menuPaciente(sc, gerPaciente);
                        case 2 -> menuConsulta(sc, gerConsulta);
                        case 0 -> Println("Logout realizado.\n");
                        default -> Println("Opção inválida.\n");
                    }
                } else if (nivel == NivelAcesso.MEDICO) {
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

            limparTela();
            exibirMenuMedico();

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (opcao) {
                case 1 -> { gm.cadastrarMedico(); pausar(sc); }
                case 2 -> { gm.editarMedico(); pausar(sc); }
                case 3 -> { gm.listarMedicos(); pausar(sc); }
                case 4 -> { gm.removerMedico(); pausar(sc); }
                case 5 -> { gm.buscarMedico(); pausar(sc); }
                case 0 -> {}
                default -> Println("Opção inválida.\n");
            }
        }
    }

    private static void menuPaciente(Scanner sc, GerenciadorPaciente gp) {
        int opcao = -1;
        while (opcao != 0) {

            limparTela();
            exibirMenuPaciente();

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (opcao) {
                case 1 -> { gp.cadastrarPaciente(); pausar(sc); }
                case 2 -> { gp.editarPaciente(); pausar(sc); }
                case 3 -> { gp.listarPacientes(); pausar(sc); }
                case 4 -> { gp.removerPaciente(); pausar(sc); }
                case 5 -> { gp.buscarPaciente(); pausar(sc); }
                case 0 -> {}
                default -> Println("Opção inválida.\n");
            }
        }
    }

    private static void menuConsulta(Scanner sc, GerenciadorConsulta gc) {
        int opcao = -1;
        while (opcao != 0) {

            limparTela();
            exibirMenuConsulta();

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (opcao) {
                case 1 -> { gc.agendar(); pausar(sc); }
                case 2 -> { gc.cancelarAgendamento(); pausar(sc); }
                case 3 -> { gc.listarConsultas(); pausar(sc); }
                case 4 -> { gc.buscarConsulta(); pausar(sc); }
                case 5 -> { gc.solicitarRetorno(); pausar(sc); }
                case 0 -> {}
                default -> Println("Opção inválida.\n");
            }
        }
    }

    private static void menuFuncionario(Scanner sc, GerenciadorFuncionario gf) {
        int opcao = -1;
        while (opcao != 0) {

            limparTela();
            exibirMenuAdministraFuncionario();
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }
            switch (opcao) {
                case 1 -> {gf.cadastrarFuncionario(); pausar(sc);}
                case 2 -> {gf.listarFuncionarios(); pausar(sc);}
                case 3 -> {gf.buscarFuncionario(); pausar(sc);}
                case 4 -> {gf.editarFuncionario(); pausar(sc);}
                case 5 -> {gf.removerFuncionario(); pausar(sc);}
                case 0 -> {}
                default -> Println("Opção inválida.\n");
            }
        }

    }
    private static void buscaGeral(Scanner sc, Hospital hospital) {
        Print("Digite o nome para buscar: ");
        String termo = sc.nextLine().toLowerCase();
        boolean encontrou = false;

        Println("\n--- RESULTADO DA BUSCA ---");

        // Uso de for-each na lista única da Superclasse
        for (Pessoa p : hospital.getPessoas()) {

            // Verificamos apenas o NOME (comum a todos).
            // NÃO usamos if(p instanceof Medico) nem switch.
            if (p.getNome().toLowerCase().contains(termo)) {

                // O Java decide qual exibirInformacoes chamar
                p.exibirInformacoes();
                encontrou = true;
            }
        }

        if (!encontrou) {
            Println("Nenhuma pessoa encontrada.");
        }
        Println("--------------------------\n");
    }
}
