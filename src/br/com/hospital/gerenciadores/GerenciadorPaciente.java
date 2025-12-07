package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Paciente;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.sistema.Hospital;

import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorPaciente implements Gerenciavel<Paciente> {

    private final Hospital hospital;
    private final Scanner sc;

    public GerenciadorPaciente(Hospital hospital, Scanner sc) {
        this.hospital = hospital;
        this.sc = sc;
    }

    // -------------------------
    // Adicionar
    // -------------------------

    @Override
    public void adicionar(Paciente paciente) {
        hospital.adicionarPessoa(paciente);
        Println("Paciente cadastrado com sucesso!");
    }

    public void cadastrarPaciente() {
        Println("\n--- CADASTRO DE PACIENTE ---");

        // Nome
        String nome = lerCampoObrigatorio("Nome");

        // Idade
        int idade = lerIdade();

        // Queixa
        String principalQueixa = lerCampoObrigatorio("Principal queixa");

        // CPF
        String cpf = lerCpfNovo();

        // Telefone
        String telefone = lerTelefone();

        // Email
        String email = lerEmail();

        // Endereço
        Print("Endereço: ");
        String endereco = sc.nextLine();

        // Criar paciente
        Paciente paciente = new Paciente(
                gerarIdUnico(),
                capitalizarNome(nome),
                cpf,
                telefone,
                email,
                endereco,
                idade,
                principalQueixa
        );

        adicionar(paciente);
    }

    // -------------------------
    // Listar
    // -------------------------

    @Override
    public void listar() {
        List<Paciente> pacientes = hospital.getPessoas()
                .stream()
                .filter(p -> p instanceof Paciente)
                .map(p -> (Paciente) p)
                .sorted(Comparator.comparing(Paciente::getNome))
                .toList();

        if (pacientes.isEmpty()) {
            Println("Nenhum paciente encontrado.\n");
            return;
        }

        Println("\n--- LISTA DE PACIENTES ---");
        pacientes.forEach(p -> {
            p.exibirInformacoes();
            Println("---------------------------");
        });
    }

    public void listarPacientes() {
        listar();
    }

    // -------------------------
    // Buscar
    // -------------------------

    @Override
    public Paciente buscar(String cpf) {
        var pessoa = hospital.buscarPessoa(cpf);
        return (pessoa instanceof Paciente) ? (Paciente) pessoa : null;
    }

    public void buscarPaciente() {
        Print("Informe o CPF do paciente: ");
        String cpf = sc.nextLine();

        Paciente p = buscar(cpf);
        if (p == null) {
            Println("Paciente não encontrado.\n");
            return;
        }

        Println("\n--- DADOS DO PACIENTE ---");
        p.exibirInformacoes();
        Println("---------------------------\n");
    }

    // -------------------------
    // Editar
    // -------------------------

    @Override
    public boolean editar(String cpf, Paciente novo) {
        Paciente antigo = buscar(cpf);

        if (antigo == null) {
            return false;
        }

        hospital.getPessoas().remove(antigo);
        hospital.adicionarPessoa(novo);
        return true;
    }

    public void editarPaciente() {

        Print("Informe o CPF do paciente a editar: ");
        String cpf = sc.nextLine();

        Paciente antigo = buscar(cpf);
        if (antigo == null) {
            Println("Paciente não encontrado.\n");
            return;
        }

        Println("\n--- EDITAR PACIENTE ---");

        String nome = lerCampoOpcional("Novo nome", antigo.getNome());
        int idade = lerIdadeOpcional("Nova idade", antigo.getIdade());
        String principalQueixa = lerCampoOpcional("Nova queixa", antigo.getPrincipalQueixa());
        String telefone = lerTelefoneOpcional(antigo.getTelefone());
        String email = lerEmailOpcional(antigo.getEmail());

        Print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();
        if (!textoNaoVazio(endereco)) endereco = antigo.getEndereco();

        Paciente novo = new Paciente(
                antigo.getId(),
                capitalizarNome(nome),
                antigo.getCpf(),
                telefone,
                email,
                endereco,
                idade,
                principalQueixa
        );

        if (editar(cpf, novo)) {
            Println("Paciente atualizado com sucesso!\n");
        } else {
            Println("Erro ao atualizar paciente.\n");
        }
    }

    // -------------------------
    // Remover
    // -------------------------

    @Override
    public boolean remover(String cpf) {
        Paciente p = buscar(cpf);
        if (p != null) {
            hospital.getPessoas().remove(p);
            return true;
        }
        return false;
    }

    public void removerPaciente() {
        Print("CPF do paciente para remover: ");
        String cpf = sc.nextLine();

        if (remover(cpf)) {
            Println("Paciente removido com sucesso!\n");
        } else {
            Println("Paciente não encontrado.\n");
        }
    }

    // ============================================================
    // MÉTODOS AUXILIARES PARA EVITAR DUPLICAÇÃO DE CÓDIGO
    // ============================================================

    private String lerCampoObrigatorio(String nomeCampo) {
        String valor = "";
        while (!textoNaoVazio(valor)) {
            Print(nomeCampo + ": ");
            valor = sc.nextLine();
            if (!textoNaoVazio(valor)) {
                Println(nomeCampo + " não pode ficar vazio.");
            }
        }
        return valor;
    }

    private String lerCampoOpcional(String mensagem, String atual) {
        Print(mensagem + " (atual: " + atual + "): ");
        String valor = sc.nextLine();
        return textoNaoVazio(valor) ? valor : atual;
    }

    private int lerIdade() {
        int idade = -1;
        while (idade <= 0) {
            Print("Idade: ");
            try {
                idade = Integer.parseInt(sc.nextLine());
            } catch (Exception ignored) {}
            if (idade <= 0) Println("Idade inválida.");
        }
        return idade;
    }

    private int lerIdadeOpcional(String mensagem, int atual) {
        Print(mensagem + " (atual: " + atual + "): ");
        String valor = sc.nextLine();
        if (!textoNaoVazio(valor)) return atual;

        try {
            int idade = Integer.parseInt(valor);
            return idade > 0 ? idade : atual;
        } catch (Exception e) {
            return atual;
        }
    }

    private String lerCpfNovo() {
        while (true) {
            Print("CPF: ");
            String cpf = sc.nextLine();
            if (!cpfValido(cpf)) {
                Println("CPF inválido!");
            } else if (hospital.buscarPessoa(cpf) != null) {
                Println("CPF já cadastrado!");
            } else {
                return cpf;
            }
        }
    }

    private String lerTelefone() {
        while (true) {
            Print("Telefone: ");
            String t = sc.nextLine();
            if (telefoneValido(t)) return t;
            Println("Telefone inválido!");
        }
    }

    private String lerTelefoneOpcional(String atual) {
        Print("Novo telefone (atual: " + atual + "): ");
        String t = sc.nextLine();
        if (!textoNaoVazio(t)) return atual;
        if (!telefoneValido(t)) {
            Println("Telefone inválido.");
            return atual;
        }
        return t;
    }

    private String lerEmail() {
        while (true) {
            Print("Email: ");
            String e = sc.nextLine();
            if (emailValido(e)) return e;
            Println("Email inválido!");
        }
    }

    private String lerEmailOpcional(String atual) {
        Print("Novo email (atual: " + atual + "): ");
        String e = sc.nextLine();
        if (!textoNaoVazio(e)) return atual;
        if (!emailValido(e)) {
            Println("Email inválido.");
            return atual;
        }
        return e;
    }
}