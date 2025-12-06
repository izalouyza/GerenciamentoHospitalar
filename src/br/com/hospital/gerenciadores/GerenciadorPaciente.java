package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Paciente;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.utilitarios.Utilitarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorPaciente implements Gerenciavel<Paciente> {

    private final List<Paciente> pacientes = new ArrayList<>();
    private final Hospital hospital;
    private final Scanner sc;

    public GerenciadorPaciente(Hospital hospital, Scanner sc) {
        this.hospital = hospital;
        this.sc = sc;
    }

    @Override
    public void adicionar(Paciente paciente) {
        hospital.adicionarPessoa(paciente);
        Utilitarios.println("Paciente cadastrado com sucesso!\n");
    }

    public void cadastrarPaciente() {
        Utilitarios.println("\n---CADASTRO DE PACIENTE---");

        Utilitarios.print("Nome: ");
        String nome = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(nome)) {
        Utilitarios.println("Nome não pode ficar vazio.\n");
        return;
        }

        Utilitarios.print("Idade: ");
        int idade = Integer.parseInt(sc.nextLine());

        Utilitarios.print("Principal Queixa: ");
        String principalQueixa = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(principalQueixa)) {
        Utilitarios.println("Principal queixa não pode ficar vazia.\n");
        return;
        }

        Utilitarios.print("CPF: ");
        String cpf = sc.nextLine();
        if (!Utilitarios.cpfValido(cpf)) {
            Utilitarios.println("CPF inválido!\n");
            return;
        }

        Utilitarios.print("Telefone: ");
        String telefone = sc.nextLine();
        if (!Utilitarios.telefoneValido(telefone)) {
        Utilitarios.println("Telefone inválido!\n");
        return;
        }   

        Utilitarios.print("Email: ");
        String email = sc.nextLine();
        if (!Utilitarios.emailValido(email)) {
        Utilitarios.println("Email inválido!\n");
        return;
        }

        Utilitarios.print("Endereço: ");
        String endereco = sc.nextLine();

        Utilitarios.print("Senha: ");
        String senha = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(senha) || senha.length() < 4) {
        Utilitarios.println("Senha inválida (mínimo 4 caracteres).\n");
        return;
    }

        Paciente p = new Paciente(
                Utilitarios.gerarIdUnico(),
                Utilitarios.capitalizarNome(nome),
                cpf,
                telefone,
                email,
                endereco,
                senha,
                idade,
                principalQueixa
        );

        adicionar(p);
    }

    @Override
    public void listar() {
        List<Paciente> pacientes = hospital.getPessoas().stream()
                .filter(p -> p instanceof Paciente)
                .map(p -> (Paciente) p)
                .toList();

        if (pacientes.isEmpty()) {
            Utilitarios.println("Nenhum paciente encontrado.\n");
            return;
        }

        Utilitarios.println("\n---LISTA DE PACIENTES---");
        for (Paciente p : pacientes) {
            p.exibirInformacoes();
            Utilitarios.println("---------------------------");
        }
    }

    public void listarPacientes() {
        Utilitarios.println("\n--- LISTA DE PACIENTES ---");
        listar();
    }

    @Override
    public Paciente buscar(String cpf) {
        var pessoa = hospital.buscarPessoa(cpf);
        return (pessoa instanceof Paciente p) ? p : null;
    }

    public void buscarPaciente() {
        Utilitarios.print("Informe o CPF do paciente: ");
        String cpf = sc.nextLine();

        Paciente p = buscar(cpf);

        if (p == null) {
            Utilitarios.println("Paciente não encontrado.\n");
            return;
        }

        Utilitarios.println("\n---DADOS DO PACIENTE---");
        p.exibirInformacoes();
        Utilitarios.println("---------------------------\n");
    }

    @Override
    public boolean editar(String cpf, Paciente novo) {
        var antigo = buscar(cpf);

        if (antigo == null) return false;

        hospital.getPessoas().remove(antigo);
        hospital.adicionarPessoa(novo);
        return true;
    }

    public void editarPaciente() {
        Utilitarios.print("Informe o CPF do paciente a editar: ");
        String cpf = sc.nextLine();

        Paciente antigo = buscar(cpf);
        if (antigo == null) {
            Utilitarios.println("Paciente não encontrado.\n");
            return;
        }

        Utilitarios.println("\n---EDITAR PACIENTE---");

        Utilitarios.print("Novo nome (atual: " + antigo.getNome() + "): ");
        String nome = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(nome)) {
            nome = antigo.getNome();
    }

        Utilitarios.print("Nova idade (atual: " + antigo.getIdade() + "): ");
        int idade = sc.nextInt();

        Utilitarios.print("Nova principalQueixa (atual: " + antigo.getprincipalQueixa() + "): ");
        String principalQueixa = sc.nextLine();

        Utilitarios.print("Novo telefone (atual: " + antigo.getTelefone() + "): ");
        String telefone = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(telefone)) {
            telefone = antigo.getTelefone();
        } else if (!Utilitarios.telefoneValido(telefone)) {
        Utilitarios.println("Telefone inválido.\n");
            return;
        }

        Utilitarios.print("Novo email (atual: " + antigo.getEmail() + "): ");
        String email = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(email)) {
            email = antigo.getEmail();
        } else if (!Utilitarios.emailValido(email)) {
            Utilitarios.println("Email inválido.\n");
        return;
        }
        Utilitarios.print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(endereco)) {
            endereco = antigo.getEndereco();
        }
        Paciente novo = new Paciente(
                antigo.getId(),
                nome,
                antigo.getCpf(),
                telefone,
                email,
                endereco,
                antigo.getSenha(),
                idade,
                principalQueixa
        );

        if (editar(cpf, novo)) {
            Utilitarios.println("Paciente atualizado com sucesso!\n");
        } else {
            Utilitarios.println("Erro ao atualizar paciente.\n");
        }
    }

    @Override
    public boolean remover(String cpf) {
        var p = buscar(cpf);
        if (p != null) {
            hospital.getPessoas().remove(p);
            return true;
        }
        return false;
    }

    public void removerPaciente() {
        Utilitarios.print("CPF do paciente para remover: ");
        String cpf = sc.nextLine();

        if (remover(cpf)) {
            Utilitarios.println("Paciente removido com sucesso!\n");
        } else {
            Utilitarios.println("Paciente não encontrado.\n");
        }
    }

    public List<Paciente> getListaPacientes() {
        return pacientes; // ou como você chamou a lista interna
    }
}
