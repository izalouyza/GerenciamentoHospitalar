package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.*;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.utilitarios.Utilitarios;


import java.util.Scanner;

public class GerenciamentoSistema implements Gerenciavel<Pessoa> {

    private final Hospital hospital;
    private final Scanner sc;

    public GerenciamentoSistema(Hospital hospital, Scanner sc) {
        this.hospital = hospital;
        this.sc = sc;
    }

    public void cadastrarPaciente() {
        try {
            System.out.println("--- Cadastrar Paciente ---");
            String id = Utilitarios.gerarIdUnico();

            System.out.print("Nome: ");
            String nome = sc.nextLine().trim();

            System.out.print("CPF: ");
            String cpf = sc.nextLine().trim();
            if (!Utilitarios.cpfValido(cpf)) {
                System.out.println("CPF inválido.");
                return;
            }

            System.out.print("Telefone: ");
            String tel = sc.nextLine().trim();
            if (!Utilitarios.telefoneValido(tel)) {
                System.out.println("Telefone inválido.");
                return;
            }

            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            if (!Utilitarios.emailValido(email)) {
                System.out.println("Email inválido.");
                return;
            }

            System.out.print("Endereço: ");
            String end = sc.nextLine().trim();

            System.out.print("Senha: ");
            String senha = sc.nextLine().trim();
            if (senha.length() < 4) {
                System.out.println("Senha curta.");
                return;
            }

            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Histórico clínico: ");
            String hist = sc.nextLine().trim();

            Paciente p = new Paciente(id, nome, cpf, tel, email, end, senha, idade, hist);
            hospital.adicionarPessoa(p);

            System.out.println("Paciente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarMedico() {
        try {
            System.out.println("--- Cadastrar Médico ---");
            String id = Utilitarios.gerarIdUnico();

            System.out.print("Nome: ");
            String nome = sc.nextLine().trim();

            System.out.print("CPF: ");
            String cpf = sc.nextLine().trim();
            if (!Utilitarios.cpfValido(cpf)) {
                System.out.println("CPF inválido.");
                return;
            }

            System.out.print("Telefone: ");
            String tel = sc.nextLine().trim();
            if (!Utilitarios.telefoneValido(tel)) {
                System.out.println("Telefone inválido.");
                return;
            }

            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            if (!Utilitarios.emailValido(email)) {
                System.out.println("Email inválido.");
                return;
            }

            System.out.print("Endereço: ");
            String end = sc.nextLine().trim();

            System.out.print("Senha: ");
            String senha = sc.nextLine().trim();
            if (senha.length() < 4) {
                System.out.println("Senha curta.");
                return;
            }

            System.out.print("CRM: ");
            String crm = sc.nextLine().trim();
            if (!Utilitarios.crmValido(crm)) {
                System.out.println("CRM inválido.");
                return;
            }

            System.out.print("Especialidade: ");
            String esp = sc.nextLine().trim();

            Medico m = new Medico(id, nome, cpf, tel, email, end, senha, crm, esp);
            hospital.adicionarPessoa(m);

            System.out.println("Médico cadastrado!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


    public void buscarPessoaMenu() {
        System.out.print("CPF ou CRM: ");
        String id = sc.nextLine().trim();

        Pessoa p = hospital.buscarPessoa(id);

        if (p == null) {
            System.out.println("Pessoa não encontrada.");
            return;
        }

        p.exibirInformacoes();
    }

    public void editarPessoaMenu() {
        System.out.print("CPF da pessoa: ");
        String cpf = sc.nextLine().trim();

        Pessoa p = hospital.buscarPessoa(cpf);
        if (p == null) {
            System.out.println("Pessoa não encontrada.");
            return;
        }

        System.out.print("Novo nome (enter = manter): ");
        String nome = sc.nextLine().trim();
        if (!nome.isBlank()) p.setNome(nome);

        System.out.print("Novo telefone (enter = manter): ");
        String tel = sc.nextLine().trim();
        if (!tel.isBlank()) {
            if (!Utilitarios.telefoneValido(tel)) {
                System.out.println("Telefone inválido.");
                return;
            }
            p.setTelefone(tel);
        }

        System.out.print("Novo email (enter = manter): ");
        String email = sc.nextLine().trim();
        if (!email.isBlank()) {
            if (!Utilitarios.emailValido(email)) {
                System.out.println("Email inválido.");
                return;
            }
            p.setEmail(email);
        }

        System.out.print("Novo endereço (enter = manter): ");
        String end = sc.nextLine().trim();
        if (!end.isBlank()) p.setEndereco(end);

        boolean ok = hospital.editarPessoa(cpf, p);
        System.out.println(ok ? "Atualizada." : "Erro ao atualizar.");
    }

    public void removerPessoaMenu() {
        System.out.print("CPF da pessoa: ");
        String cpf = sc.nextLine().trim();

        boolean ok = hospital.removerPessoa(cpf);
        System.out.println(ok ? "Removida!" : "Não encontrada.");
    }

    public void agendarConsultaMenu() {
        try {
            System.out.print("CPF do paciente: ");
            Pessoa p = hospital.buscarPessoa(sc.nextLine().trim());

            if (!(p instanceof Paciente)) {
                System.out.println("Paciente inválido.");
                return;
            }

            System.out.print("CRM do médico: ");
            Pessoa m = hospital.buscarPessoa(sc.nextLine().trim());

            if (!(m instanceof Medico)) {
                System.out.println("Médico inválido.");
                return;
            }

            System.out.print("Data/hora (dd/MM/yyyy HH:mm): ");
            String dh = sc.nextLine().trim();
            if (!Utilitarios.dataHoraValida(dh)) {
                System.out.println("Data/hora inválida.");
                return;
            }

            System.out.print("Descrição: ");
            String desc = sc.nextLine().trim();

            String id = Utilitarios.gerarIdUnico();
            Consulta c = new Consulta(id, (Paciente) p, (Medico) m, dh, desc);

            hospital.adicionarConsulta(c);
            System.out.println("Consulta marcada: " + id);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void editarConsultaMenu() {
        System.out.print("ID consulta: ");
        String id = sc.nextLine().trim();

        Consulta c = hospital.buscarConsulta(id);
        if (c == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }

        System.out.print("Nova data/hora: ");
        String dh = sc.nextLine().trim();
        if (!dh.isBlank()) {
            if (!Utilitarios.dataHoraValida(dh)) {
                System.out.println("Data inválida.");
                return;
            }
            c.setDataHora(dh);
        }

        System.out.print("Nova descrição: ");
        String desc = sc.nextLine().trim();
        if (!desc.isBlank()) c.setDescricao(desc);

        boolean ok = hospital.editarConsulta(id, c);
        System.out.println(ok ? "Atualizada." : "Falha.");
    }

    public void removerConsultaMenu() {
        System.out.print("ID consulta: ");
        String id = sc.nextLine().trim();

        boolean ok = hospital.removerConsulta(id);
        System.out.println(ok ? "Removida." : "Não encontrada.");
    }

    @Override
    public void adicionar(Pessoa elemento) {
        hospital.adicionarPessoa(elemento);
    }

    @Override
    public void listar() {
        hospital.listarPessoas();
    }

    @Override
    public Pessoa buscar(String identificador) {
        return hospital.buscarPessoa(identificador);
    }

    @Override
    public boolean editar(String identificador, Pessoa novoElemento) {
        return hospital.editarPessoa(identificador, novoElemento);
    }

    @Override
    public boolean remover(String identificador) {
        return hospital.removerPessoa(identificador);
    }
}
