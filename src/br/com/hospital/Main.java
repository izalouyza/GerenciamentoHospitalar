package br.com.hospital;

import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Paciente;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.entidades.Consulta;
import br.com.hospital.gerenciadores.GerenciadorConsulta;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.sistema.Login;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Hospital hospital = new Hospital();
        GerenciadorConsulta gerenciadorConsulta = new GerenciadorConsulta();
        Login login = new Login(hospital.getPessoasRegistradas());

        boolean executando = true;

        while (executando) {
            if (login.getUsuarioLogado() == null) {
                mostrarMenuLogin();
                String opcao = sc.nextLine();

                switch (opcao) {
                    case "1" -> fazerLogin(sc, login);
                    case "0" -> {
                        System.out.println("Encerrando sistema...");
                        executando = false;
                    }
                    default -> System.out.println("Opção inválida.");
                }

            } else {
                mostrarMenuPrincipal(login.getUsuarioLogado());
                String opcao = sc.nextLine();

                switch (opcao) {
                    case "1" -> cadastrarPaciente(sc, hospital);
                    case "2" -> cadastrarMedico(sc, hospital);
                    case "3" -> listarPessoas(hospital);
                    case "4" -> buscarPessoa(sc, hospital);
                    case "5" -> agendarConsulta(sc, login, hospital, gerenciadorConsulta);
                    case "6" -> gerenciadorConsulta.listar();
                    case "7" -> editarConsulta(sc, gerenciadorConsulta);
                    case "8" -> removerConsulta(sc, gerenciadorConsulta);
                    case "9" -> {
                        login.logout();
                        System.out.println("Logout realizado com sucesso.");
                    }
                    case "0" -> {
                        System.out.println("Encerrando sistema...");
                        executando = false;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
        }

        sc.close();
    }

    //Menus

    private static void mostrarMenuLogin() {
        System.out.println("""
                ====== SISTEMA HOSPITALAR ======
                1 - Fazer login
                0 - Sair
                """);
        System.out.print("Opção: ");
    }

    private static void mostrarMenuPrincipal(Pessoa usuario) {
        System.out.printf("""
                
                ====== MENU PRINCIPAL ======
                Usuário logado: %s (Acesso: %s)
                
                1 - Cadastrar paciente
                2 - Cadastrar médico
                3 - Listar pessoas (polimórfico)
                4 - Buscar pessoa (CPF ou CRM)
                5 - Agendar consulta
                6 - Listar consultas
                7 - Editar consulta
                8 - Remover consulta
                9 - Logout
                0 - Sair
                
                Opção: """, usuario.getNome(), usuario.getNivelAcesso());
    }

    //Login

    private static void fazerLogin(Scanner sc, Login login) {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        if (login.autenticar(cpf, senha)) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("CPF ou senha inválidos.");
        }
    }

    //Cadastro de Paciente

    private static void cadastrarPaciente(Scanner sc, Hospital hospital) {
        try {
            int id = Utilitarios.gerarIdIncremental();

            System.out.println("=== Cadastro de Paciente ===");
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("CPF (somente números): ");
            String cpf = sc.nextLine();
            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Endereço: ");
            String endereco = sc.nextLine();
            System.out.print("Senha de acesso: ");
            String senha = sc.nextLine();
            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());
            System.out.print("Histórico clínico: ");
            String historico = sc.nextLine();

            Paciente paciente = new Paciente(
                    id, nome, cpf, telefone, email, endereco, senha, "PACIENTE", idade, historico
            );

            if (!paciente.validar()) {
                System.out.println("Erro ao cadastrar paciente: " + paciente.getMensagemValidacao());
                return;
            }

            hospital.adicionar(paciente);
            System.out.println("Paciente cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Idade inválida. Cadastro cancelado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    //Cadastro de Médico

    private static void cadastrarMedico(Scanner sc, Hospital hospital) {
        int id = Utilitarios.gerarIdIncremental();

        System.out.println("=== Cadastro de Médico ===");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF (somente números): ");
        String cpf = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();
        System.out.print("Senha de acesso: ");
        String senha = sc.nextLine();
        System.out.print("CRM: ");
        String crm = sc.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        // Nível de acesso fixo para médico
        Medico medico = new Medico(
                id, nome, cpf, telefone, email, endereco, senha, "MEDICO", crm, especialidade
        );

        if (!medico.validar()) {
            System.out.println("Erro ao cadastrar médico: " + medico.getMensagemValidacao());
            return;
        }

        hospital.adicionar(medico);
        System.out.println("Médico cadastrado com sucesso!");
    }

    // ------------------ Pessoas ------------------

    private static void listarPessoas(Hospital hospital) {
        System.out.println("=== Pessoas registradas ===");
        hospital.listar(); // uso polimórfico de exibirInformacoes()
    }

    private static void buscarPessoa(Scanner sc, Hospital hospital) {
        System.out.print("Informe CPF ou CRM: ");
        String identificador = sc.nextLine();

        Pessoa p = hospital.buscar(identificador);
        if (p == null) {
            System.out.println("Nenhuma pessoa encontrada com esse identificador.");
        }
    }

    // ------------------ Consultas ------------------

    private static void agendarConsulta(Scanner sc, Login login,
                                        Hospital hospital, GerenciadorConsulta gerenciadorConsulta) {

        if (!login.temPermissao("Criar Consulta")) {
            System.out.println("Você não tem permissão para criar consultas.");
            return;
        }

        String idConsulta = Utilitarios.gerarIdUnico();

        System.out.println("=== Agendar Consulta ===");
        System.out.print("CPF do paciente: ");
        String cpfPaciente = sc.nextLine();
        System.out.print("CPF ou CRM do médico: ");
        String idMedico = sc.nextLine();
        System.out.print("Data e hora da consulta (texto livre): ");
        String dataHora = sc.nextLine();
        System.out.print("Descrição da consulta: ");
        String descricao = sc.nextLine();

        Pessoa pessoaPaciente = hospital.buscar(cpfPaciente);
        if (!(pessoaPaciente instanceof Paciente paciente)) {
            System.out.println("Paciente não encontrado ou identificador inválido.");
            return;
        }

        Pessoa pessoaMedico = hospital.buscar(idMedico);
        if (!(pessoaMedico instanceof Medico medico)) {
            System.out.println("Médico não encontrado ou identificador inválido.");
            return;
        }

        Consulta consulta = new Consulta(idConsulta, paciente, medico, dataHora, descricao);

        gerenciadorConsulta.adicionar(consulta);
    }

    private static void editarConsulta(Scanner sc, GerenciadorConsulta gerenciadorConsulta) {
        System.out.print("Informe o ID da consulta a ser editada: ");
        String id = sc.nextLine();

        Consulta antiga = gerenciadorConsulta.buscar(id);
        if (antiga == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }

        System.out.println("Deixe em branco para manter o valor atual.");
        System.out.printf("Data/hora atual: %s%nNova data/hora: ", antiga.getDataHora());
        String novaData = sc.nextLine();
        if (novaData.isBlank()) {
            novaData = antiga.getDataHora();
        }

        System.out.printf("Descrição atual: %s%nNova descrição: ", antiga.getDescricao());
        String novaDesc = sc.nextLine();
        if (novaDesc.isBlank()) {
            novaDesc = antiga.getDescricao();
        }

        // Mantém o mesmo paciente e médico
        Consulta novosDados = new Consulta(
                antiga.getIdentificador(),
                antiga.getPaciente(),
                antiga.getMedico(),
                novaData,
                novaDesc
        );

        gerenciadorConsulta.editar(id, novosDados);
    }

    private static void removerConsulta(Scanner sc, GerenciadorConsulta gerenciadorConsulta) {
        System.out.print("Informe o ID da consulta a ser removida: ");
        String id = sc.nextLine();

        gerenciadorConsulta.remover(id);
    }
}
