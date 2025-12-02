package br.com.hospital;

import br.com.hospital.entidades.Funcionario;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.exceptions.LoginException;
import br.com.hospital.gerenciadores.GerenciamentoSistema;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.sistema.Login;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Hospital hospital = new Hospital();
        GerenciamentoSistema sistema = new GerenciamentoSistema(hospital, sc);

        Funcionario funcionarioPadrao = new Funcionario(
                "FUNC-1", "Recepcionista Admin", "11122233344",
                "(85)99999-0000", "recep@hosp.com", "Av X",
                "admin", "RECEP", "Recepção", "Recepção"
        );

        List<Pessoa> usuarios = new ArrayList<>();
        usuarios.add(funcionarioPadrao);

        Login login = new Login(usuarios);

        System.out.println("SISTEMA HOSPITALAR\n");

        while (true) {   // permite login → logout → login novamente

            boolean autenticado = false;

            // -------- LOGIN --------
            while (!autenticado) {
                System.out.println("Login\n");
                System.out.print("CPF: ");
                String cpf = sc.nextLine();

                System.out.print("Senha: ");
                String senha = sc.nextLine();

                try {
                    if (login.autenticar(cpf, senha)) {
                        autenticado = true;
                        System.out.println("Login realizado.\n");
                    }
                } catch (LoginException e) {
                    System.out.println(e.getMessage() + "\n");
                }
            }

            // -------- MENU PRINCIPAL --------
            int opcao = -1;
            while (opcao != 0 && opcao != 7) {

                System.out.println("1. Cadastrar Médico");
                System.out.println("2. Listar Médicos");
                System.out.println("3. Cadastrar Paciente");
                System.out.println("4. Listar Pacientes");
                System.out.println("5. Agendar Consulta");
                System.out.println("6. Listar Consultas");
                System.out.println("7. Logout");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Opção inválida.\n");
                    continue;
                }

                switch (opcao) {

                    case 1:
                        sistema.cadastrarMedico();
                        break;

                    case 2:
                        sistema.listar();
                        break;

                    case 3:
                        sistema.cadastrarPaciente();
                        break;

                    case 4:
                        sistema.listar();
                        break;

                    case 5:
                        sistema.agendarConsultaMenu();
                        break;

                    case 6:
                        hospital.listarConsultas();
                        break;

                    case 7:
                        System.out.println("\nVocê saiu da conta.\n");
                        break;

                    case 0:
                        System.out.println("Encerrando...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Opção inválida.\n");
                }
            }

            // Se sair pelo logout (opcao 7), volta ao início do while(true)
            // Se sair pelo 0, já retornou no menu

        }
    }
}
