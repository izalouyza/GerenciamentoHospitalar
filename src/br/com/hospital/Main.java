package br.com.hospital;

import br.com.hospital.entidades.Funcionario;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.exceptions.LoginException;
import br.com.hospital.gerenciadores.GerenciamentoSistema;
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
        GerenciamentoSistema sistema = new GerenciamentoSistema(hospital, sc);

        Funcionario funcionarioPadrao = new Funcionario(
                "FUNC-1", "Recepcionista Admin", "11122233344",
                "(85)99999-0000", "recep@hosp.com", "Av X",
                "admin", "RECEP", "Recepção", "Recepção"
        );

        List<Pessoa> usuarios = new ArrayList<>();
        usuarios.add(funcionarioPadrao);

        Login login = new Login(usuarios);

        Utilitarios.println("SISTEMA HOSPITALAR\n");

        while (true) {   // permite login → logout → login novamente

            boolean autenticado = false;

            // -------- LOGIN --------
            while (!autenticado) {
                Utilitarios.println("Login\n");
                Utilitarios.print("CPF: ");
                String cpf = sc.nextLine();

                System.out.print("Senha: ");
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

            // -------- MENU PRINCIPAL --------
            int opcao = -1;
            while (opcao != 0 && opcao != 7) {

                Utilitarios.println("1. Cadastrar Médico");
                Utilitarios.println("2. Listar Médicos");
                Utilitarios.println("3. Cadastrar Paciente");
                Utilitarios.println("4. Listar Pacientes");
                Utilitarios.println("5. Agendar Consulta");
                Utilitarios.println("6. Listar Consultas");
                Utilitarios.println("7. Logout");
                Utilitarios.println("0. Sair");
                Utilitarios.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    Utilitarios.println("Opção inválida.\n");
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
                        Utilitarios.println("\nVocê saiu da conta.\n");
                        break;

                    case 0:
                        Utilitarios.println("Encerrando...");
                        sc.close();
                        return;

                    default:
                        Utilitarios.println("Opção inválida.\n");
                }
            }

            // Se sair pelo logout (opcao 7), volta ao início do while(true)
            // Se sair pelo 0, já retornou no menu

        }
    }
}
