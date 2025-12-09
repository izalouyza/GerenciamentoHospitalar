package br.com.hospital;

import br.com.hospital.entidades.Medico;
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
        carregarMedicos(hospital, usuarios);
        carregarPacientes(hospital);

        Login login = new Login(usuarios);

        GerenciadorMedico gerMedico = new GerenciadorMedico(hospital, sc, usuarios);
        GerenciadorPaciente gerPaciente = new GerenciadorPaciente(hospital, sc);
        GerenciadorConsulta gerConsulta = new GerenciadorConsulta(sc, hospital);
        GerenciadorFuncionario gerFuncionario= new GerenciadorFuncionario(hospital, sc, usuarios);

        while (true) {
            // =============================================
            // MENU INICIAL
            // =============================================
            int escolhaInicial = -1;
            UsuarioSistema usuarioLogado = null;

            while (escolhaInicial != 0 && escolhaInicial != 1) {
                limparTela();
                // Imprime o cabeçalho do sistema e o menu juntos
                Printf("""
                        --- SISTEMA HOSPITALAR ---
                        
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

            // =============================================
            // LOGIN
            // =============================================
            while (usuarioLogado == null) {
                limparTela();
                Println("--- LOGIN ---\n");

                Print("Usuário (digite 0 para voltar):\t");
                String loginDigitado = sc.nextLine();

                if (loginDigitado.equals("0")) break;

                Print("Senha:\t");
                String senhaDigitada = sc.nextLine();

                try {
                    usuarioLogado = login.autenticar(loginDigitado, senhaDigitada);
                    Println("\nLogin realizado!\n");
                } catch (LoginException e) {
                    Println(e.getMessage() + "\n");
                }
            }

            if (usuarioLogado == null) continue;

            // =============================================
            // MENU PRINCIPAL
            // =============================================
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

                switch (nivel) {
                    case ADMIN -> handleMenuAdmin(opcaoPrincipal, sc, gerMedico, gerPaciente, gerConsulta, gerFuncionario, hospital);
                    case SECRETARIA -> handleMenuSecretaria(opcaoPrincipal, sc, gerPaciente, gerConsulta);
                    case MEDICO -> handleMenuMedico(opcaoPrincipal, sc, gerConsulta, hospital, usuarioLogado);
                }

                if (opcaoPrincipal == 0) {
                    Println("Logout realizado.\n");
                    usuarioLogado = null;
                }
            }
        }
    }

    // ====================
    // MENUS ADMIN
    // ====================
    private static void handleMenuAdmin(int opcao, Scanner sc, GerenciadorMedico gm, GerenciadorPaciente gp,
                                        GerenciadorConsulta gc, GerenciadorFuncionario gf, Hospital hospital) {
        switch (opcao) {
            case 1 -> { gm.cadastrarMedico(); pausar(sc); }
            case 2 -> { gm.editarMedico(); pausar(sc); }
            case 3 -> { gm.listarMedicos(); pausar(sc); }
            case 4 -> { gm.removerMedico(); pausar(sc); }
            case 5 -> { buscaGeral(sc, hospital); pausar(sc); }
            case 6 -> { gp.cadastrarPaciente(); pausar(sc); }
            case 7 -> { gc.listarConsultas(); pausar(sc); }
            case 8 -> { gf.cadastrarFuncionario(); pausar(sc); }
            case 0 -> {}
            default -> Println("Opção inválida.\n");
        }
    }

    // ====================
    // MENUS SECRETARIA
    // ====================
    private static void handleMenuSecretaria(int opcao, Scanner sc, GerenciadorPaciente gp, GerenciadorConsulta gc) {
        switch (opcao) {
            case 1 -> { gp.cadastrarPaciente(); pausar(sc); }
            case 2 -> { gc.agendar(); pausar(sc); }
            case 3 -> { gc.cancelarAgendamento(); pausar(sc); }
            case 0 -> {}
            default -> Println("Opção inválida.\n");
        }
    }

    // ====================
    // MENUS MÉDICO
    // ====================
    private static void handleMenuMedico(int opcao, Scanner sc, GerenciadorConsulta gc,
                                         Hospital hospital, UsuarioSistema usuarioLogado) {
        switch (opcao) {
            case 1 -> {
                // pega o médico logado pelo usuario
                Medico medicoLogado = hospital.getPessoas().stream()
                        .filter(p -> p instanceof Medico)
                        .map(p -> (Medico) p)
                        .filter(m -> m.getCredenciais() != null &&
                                m.getCredenciais().getUsuario().equals(usuarioLogado.getUsuario()))
                        .findFirst()
                        .orElse(null);

                if (medicoLogado != null) {
                    gc.listarConsultasPorMedico(medicoLogado);
                } else {
                    Println("Nenhum médico encontrado para este usuário.\n");
                }
                pausar(sc);
            }
            case 2 -> { gc.solicitarRetorno(); pausar(sc); }
            case 0 -> {}
            default -> Println("Opção inválida.\n");
        }
    }

    // ====================
    // SUBMENUS AUXILIARES
    // ====================
    private static void buscaGeral(Scanner sc, Hospital hospital) {
        Print("Digite o nome para buscar: ");
        String termo = sc.nextLine().toLowerCase();
        boolean encontrou = false;

        Println("\n--- RESULTADO DA BUSCA ---");

        for (Pessoa p : hospital.getPessoas()) {
            if (p.getNome().toLowerCase().contains(termo)) {
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
