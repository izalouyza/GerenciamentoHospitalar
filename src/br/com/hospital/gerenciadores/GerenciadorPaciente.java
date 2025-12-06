package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Paciente;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.sistema.Hospital;
import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.List;
import java.util.Scanner;

public class GerenciadorPaciente implements Gerenciavel<Paciente> {

    private final Hospital hospital; // referência ao hospital para manipular pessoas
    private final Scanner sc;

    public GerenciadorPaciente(Hospital hospital, Scanner sc) {
        this.hospital = hospital;
        this.sc = sc;
    }

    // Adicionar
    @Override
    public void adicionar(Paciente paciente) {
        hospital.adicionarPessoa(paciente); // adiciona paciente ao hospital
        Println("Paciente cadastrado com sucesso!");
    }

    public void cadastrarPaciente() {
        Println("\n--- CADASTRO DE PACIENTE ---");

        // nome
        String nome = "";
        while (!textoNaoVazio(nome)) {
            Print("Nome: ");
            nome = sc.nextLine();
            if (!textoNaoVazio(nome)) {
                Println("Nome não pode ficar vazio.");
            }
        }

        // idade
        int idade = -1;
        while (idade <= 0) {
            Print("Idade: ");
            try {
                idade = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                idade = -1; // entrada inválida
            }
            if (idade <= 0) {
                Println("Idade inválida.");
            }
        }

        // principal queixa
        String principalQueixa = "";
        while (!textoNaoVazio(principalQueixa)) {
            Print("Principal Queixa: ");
            principalQueixa = sc.nextLine();
            if (!textoNaoVazio(principalQueixa)) {
                Println("Principal queixa não pode ficar vazia.");
            }
        }

        // CPF e validação
        String cpf = "";
        while (true) {
            Print("CPF: ");
            cpf = sc.nextLine();

            if (!cpfValido(cpf)) {
                Println("CPF inválido!");
            } else if (hospital.buscarPessoa(cpf) != null) {
                Println("CPF já cadastrado!");
            } else {
                break;
            }
        }

        // telefone
        String telefone = "";
        while (true) {
            Print("Telefone: ");
            telefone = sc.nextLine();

            if (!telefoneValido(telefone)) {
                Println("Telefone inválido!");
            } else {
                break;
            }
        }

        // email
        String email = "";
        while (true) {
            Print("Email: ");
            email = sc.nextLine();

            if (!emailValido(email)) {
                Println("Email inválido!");
            } else {
                break;
            }
        }

        // endereço
        Print("Endereço: ");
        String endereco = sc.nextLine();

        // senha
        String senha = "";
        while (!textoNaoVazio(senha) || senha.length() < 4) {
            Print("Senha: ");
            senha = sc.nextLine();
            if (!textoNaoVazio(senha) || senha.length() < 4) {
                Println("Senha inválida (mínimo 4 caracteres).\n");
            }
        }

        // criação do paciente
        Paciente p = new Paciente(
                gerarIdUnico(),
                capitalizarNome(nome),
                cpf,
                telefone,
                email,
                endereco,
                senha,
                idade,
                principalQueixa
        );

        adicionar(p); // adiciona paciente ao hospital
    }

    // Listar
    @Override
    public void listar() {
        List<Paciente> pacientes = hospital.getPessoas().stream()
                .filter(p -> p instanceof Paciente)
                .map(p -> (Paciente) p)
                .toList();

        if (pacientes.isEmpty()) {
            Println("Nenhum paciente encontrado.\n");
            return;
        }

        Println("\n--- LISTA DE PACIENTES ---");
        for (Paciente p : pacientes) {
            p.exibirInformacoes(); // exibe informações detalhadas
            Println("---------------------------");
        }
    }

    public void listarPacientes() {
        listar(); // chama o listar padrão
    }

    // Buscar
    @Override
    public Paciente buscar(String cpf) {
        var pessoa = hospital.buscarPessoa(cpf);
        if (pessoa instanceof Paciente) {
            return (Paciente) pessoa;
        }
        return null;
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

    // Editar
    @Override
    public boolean editar(String cpf, Paciente novo) {
        var antigo = buscar(cpf);

        if (antigo == null) {
            return false;
        }

        hospital.getPessoas().remove(antigo); // remove antigo
        hospital.adicionarPessoa(novo); // adiciona atualizado
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

        // atualiza nome
        Print("Novo nome (atual: " + antigo.getNome() + "): ");
        String nome = sc.nextLine();
        if (!textoNaoVazio(nome)) {
            nome = antigo.getNome();
        }

        // atualiza idade
        int idade = antigo.getIdade();
        Print("Nova idade (atual: " + antigo.getIdade() + "): ");
        String idadeStr = sc.nextLine();
        if (textoNaoVazio(idadeStr)) {
            try {
                int novaIdade = Integer.parseInt(idadeStr);
                if (novaIdade > 0) idade = novaIdade;
            } catch (Exception ignored) {}
        }

        // atualiza principal queixa
        Print("Nova principal queixa (atual: " + antigo.getPrincipalQueixa() + "): ");
        String principalQueixa = sc.nextLine();
        if (!textoNaoVazio(principalQueixa)) {
            principalQueixa = antigo.getPrincipalQueixa();
        }

        // atualiza telefone
        Print("Novo telefone (atual: " + antigo.getTelefone() + "): ");
        String telefone = sc.nextLine();
        if (!textoNaoVazio(telefone)) {
            telefone = antigo.getTelefone();
        } else if (!telefoneValido(telefone)) {
            Println("Telefone inválido.\n");
            return;
        }

        // atualiza email
        Print("Novo email (atual: " + antigo.getEmail() + "): ");
        String email = sc.nextLine();
        if (!textoNaoVazio(email)) {
            email = antigo.getEmail();
        } else if (!emailValido(email)) {
            Println("Email inválido.\n");
            return;
        }

        // atualiza endereço
        Print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();
        if (!textoNaoVazio(endereco)) {
            endereco = antigo.getEndereco();
        }

        // cria paciente atualizado
        Paciente novo = new Paciente(
                antigo.getId(),
                capitalizarNome(nome),
                antigo.getCpf(),
                telefone,
                email,
                endereco,
                antigo.getSenha(),
                idade,
                principalQueixa
        );

        if (editar(cpf, novo)) {
            Println("Paciente atualizado com sucesso!\n");
        } else {
            Println("Erro ao atualizar paciente.\n");
        }
    }

    // Remover
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
        Print("CPF do paciente para remover: ");
        String cpf = sc.nextLine();

        if (remover(cpf)) {
            Println("Paciente removido com sucesso!\n");
        } else {
            Println("Paciente não encontrado.\n");
        }
    }
}
