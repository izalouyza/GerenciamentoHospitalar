package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Paciente;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.utilitarios.Utilitarios;

import java.util.List;
import java.util.Scanner;

public class GerenciadorPaciente implements Gerenciavel<Paciente> {

    private final Hospital hospital;
    private final Scanner sc;

    public GerenciadorPaciente(Hospital hospital, Scanner sc) {
        this.hospital = hospital;
        this.sc = sc;
    }

    // Adicionar
    @Override
    public void adicionar(Paciente paciente) {
        hospital.adicionarPessoa(paciente);
        Utilitarios.println("Paciente cadastrado com sucesso!\n");
    }

    public void cadastrarPaciente() {
        Utilitarios.println("\n--- CADASTRO DE PACIENTE ---");

        // Nome
        String nome = "";
        while (!Utilitarios.textoNaoVazio(nome)) {
            Utilitarios.print("Nome: ");
            nome = sc.nextLine();
            if (!Utilitarios.textoNaoVazio(nome)) {
                Utilitarios.println("Nome não pode ficar vazio.\n");
            }
        }

        // Idade
        int idade = -1;
        while (idade <= 0) {
            Utilitarios.print("Idade: ");
            try {
                idade = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                idade = -1;
            }
            if (idade <= 0) {
                Utilitarios.println("Idade inválida.\n");
            }
        }

        // Principal queixa
        String principalQueixa = "";
        while (!Utilitarios.textoNaoVazio(principalQueixa)) {
            Utilitarios.print("Principal Queixa: ");
            principalQueixa = sc.nextLine();
            if (!Utilitarios.textoNaoVazio(principalQueixa)) {
                Utilitarios.println("Principal queixa não pode ficar vazia.\n");
            }
        }

        // CPF
        String cpf = "";
        while (true) {
            Utilitarios.print("CPF: ");
            cpf = sc.nextLine();

            if (!Utilitarios.cpfValido(cpf)) {
                Utilitarios.println("CPF inválido!\n");
            } else if (hospital.buscarPessoa(cpf) != null) {
                Utilitarios.println("CPF já cadastrado!\n");
            } else {
                break;
            }
        }

        // Telefone
        String telefone = "";
        while (true) {
            Utilitarios.print("Telefone: ");
            telefone = sc.nextLine();

            if (!Utilitarios.telefoneValido(telefone)) {
                Utilitarios.println("Telefone inválido!\n");
            } else {
                break;
            }
        }

        // Email
        String email = "";
        while (true) {
            Utilitarios.print("Email: ");
            email = sc.nextLine();

            if (!Utilitarios.emailValido(email)) {
                Utilitarios.println("Email inválido!\n");
            } else {
                break;
            }
        }

        Utilitarios.print("Endereço: ");
        String endereco = sc.nextLine();

        // Senha
        String senha = "";
        while (!Utilitarios.textoNaoVazio(senha) || senha.length() < 4) {
            Utilitarios.print("Senha: ");
            senha = sc.nextLine();
            if (!Utilitarios.textoNaoVazio(senha) || senha.length() < 4) {
                Utilitarios.println("Senha inválida (mínimo 4 caracteres).\n");
            }
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

    // Listar
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

        Utilitarios.println("\n--- LISTA DE PACIENTES ---");
        for (Paciente p : pacientes) {
            p.exibirInformacoes();
            Utilitarios.println("---------------------------");
        }
    }

    public void listarPacientes() {
        listar();
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
        Utilitarios.print("Informe o CPF do paciente: ");
        String cpf = sc.nextLine();

        Paciente p = buscar(cpf);

        if (p == null) {
            Utilitarios.println("Paciente não encontrado.\n");
            return;
        }

        Utilitarios.println("\n--- DADOS DO PACIENTE ---");
        p.exibirInformacoes();
        Utilitarios.println("---------------------------\n");
    }

    // Editar
    @Override
    public boolean editar(String cpf, Paciente novo) {
        var antigo = buscar(cpf);

        if (antigo == null) {
            return false;
        }

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

        Utilitarios.println("\n--- EDITAR PACIENTE ---");

        // Nome
        Utilitarios.print("Novo nome (atual: " + antigo.getNome() + "): ");
        String nome = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(nome)) {
            nome = antigo.getNome();
        }

        // Idade
        int idade = antigo.getIdade();
        Utilitarios.print("Nova idade (atual: " + antigo.getIdade() + "): ");
        String idadeStr = sc.nextLine();
        if (Utilitarios.textoNaoVazio(idadeStr)) {
            try {
                int novaIdade = Integer.parseInt(idadeStr);
                if (novaIdade > 0) {
                    idade = novaIdade;
                }
            } catch (Exception ignored) {}
        }

        // Principal Queixa
        Utilitarios.print("Nova principal queixa (atual: " + antigo.getPrincipalQueixa() + "): ");
        String principalQueixa = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(principalQueixa)) {
            principalQueixa = antigo.getPrincipalQueixa();
        }

        // Telefone
        Utilitarios.print("Novo telefone (atual: " + antigo.getTelefone() + "): ");
        String telefone = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(telefone)) {
            telefone = antigo.getTelefone();
        } else {
            if (!Utilitarios.telefoneValido(telefone)) {
                Utilitarios.println("Telefone inválido.\n");
                return;
            }
        }

        // Email
        Utilitarios.print("Novo email (atual: " + antigo.getEmail() + "): ");
        String email = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(email)) {
            email = antigo.getEmail();
        } else {
            if (!Utilitarios.emailValido(email)) {
                Utilitarios.println("Email inválido.\n");
                return;
            }
        }

        // Endereço
        Utilitarios.print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();
        if (!Utilitarios.textoNaoVazio(endereco)) {
            endereco = antigo.getEndereco();
        }

        // Criação do novo paciente atualizado
        Paciente novo = new Paciente(
                antigo.getId(),
                Utilitarios.capitalizarNome(nome),
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
        Utilitarios.print("CPF do paciente para remover: ");
        String cpf = sc.nextLine();

        if (remover(cpf)) {
            Utilitarios.println("Paciente removido com sucesso!\n");
        } else {
            Utilitarios.println("Paciente não encontrado.\n");
        }
    }

}
