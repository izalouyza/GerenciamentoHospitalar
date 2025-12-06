package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Medico;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.utilitarios.Utilitarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorMedico implements Gerenciavel<Medico> {

    private List<Medico> medicos = new ArrayList<>();
    private final Hospital hospital;
    private final Scanner sc;

    public GerenciadorMedico(Hospital hospital, Scanner sc) {
        this.hospital = hospital;
        this.sc = sc;
    }

    @Override
    public void adicionar(Medico medico) {
        hospital.adicionarPessoa(medico);
        Utilitarios.println("Médico cadastrado com sucesso!\n");
    }

    public void cadastrarMedico() {
        Utilitarios.println("\n---CADASTRO DE MÉDICO---");

        String nome = "";
        while (true) {
            Utilitarios.print("Nome: ");
            nome = sc.nextLine();

            if (!nome.isBlank()) break;

            Utilitarios.println("O nome não pode ser vazio!");
        }

        String cpf = "";
        while (true) {
            Utilitarios.print("CPF: ");
            cpf = sc.nextLine();

            if (!Utilitarios.cpfValido(cpf)) {
                Utilitarios.println("CPF inválido!");
            } else if (hospital.cpfExiste(cpf)) {
                Utilitarios.println("Já existe uma pessoa com esse CPF!");
            } else {
                break;
            }
        }

        Utilitarios.print("Telefone: ");
        String telefone = sc.nextLine();

        Utilitarios.print("Email: ");
        String email = sc.nextLine();

        Utilitarios.print("Endereço: ");
        String endereco = sc.nextLine();

        String senha = "";
        while (true) {
            Utilitarios.print("Senha: ");
            senha = sc.nextLine();

            if (!senha.isBlank()) break;

            Utilitarios.println("A senha não pode ser vazia!");
        }

        String crm = "";
        while (true) {
            Utilitarios.print("CRM: ");
            crm = sc.nextLine();

            if (!Utilitarios.crmValido(crm)) {
                Utilitarios.println("CRM inválido!");
            } else if (crmExiste(crm)) {
                Utilitarios.println("Já existe um médico com esse CRM!");
            } else {
                break;
            }
        }

        String especialidade = "";
        while (true) {
            Utilitarios.print("Especialidade: ");
            especialidade = sc.nextLine();

            if (!especialidade.isBlank()) break;

            Utilitarios.println("A especialidade não pode ser vazia!");
        }

        Medico m = new Medico(
                Utilitarios.gerarIdUnico(),
                nome,
                cpf,
                telefone,
                email,
                endereco,
                senha,
                crm,
                especialidade
        );

        if (!m.validar()) {
            Utilitarios.println("ERRO: " + m.getMensagemValidacao());
            return;
        }

        adicionar(m);
    }

    @Override
    public void listar() {
        List<Medico> medicos = hospital.getPessoas().stream()
                .filter(p -> p instanceof Medico)
                .map(p -> (Medico) p)
                .toList();

        if (medicos.isEmpty()) {
            Utilitarios.println("Nenhum médico encontrado.\n");
            return;
        }

        Utilitarios.println("\n--- LISTA DE MÉDICOS ---");
        for (Medico m : medicos) {
            m.exibirInformacoes();
            Utilitarios.println("---------------------------");
        }
    }

    public void listarMedicos() {
        listar(); // chama o método listar() que já está implementado
    }

    @Override
    public Medico buscar(String crm) {
        var pessoa = hospital.buscarPessoa(crm);

        if (pessoa instanceof Medico) {
            return (Medico) pessoa;
        }

        return null;
    }

    public boolean crmExiste(String crm) {
        for (var p : hospital.getPessoas()) {
            if (p instanceof Medico) {
                Medico m = (Medico) p;
                if (Utilitarios.compararIdentificadores(m.getCrm(), crm)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void buscarMedico() {
        Utilitarios.print("Informe o CRM do médico: ");
        String crm = sc.nextLine();

        Medico m = buscar(crm);

        if (m == null) {
            Utilitarios.println("Médico não encontrado.\n");
            return;
        }

        Utilitarios.println("\n--- DADOS DO MÉDICO ---");
        m.exibirInformacoes();
        Utilitarios.println("---------------------------\n");
    }

    @Override
    public boolean editar(String crm, Medico novo) {
        Medico antigo = buscar(crm);

        if (antigo == null) return false;

        hospital.getPessoas().remove(antigo);
        hospital.adicionarPessoa(novo);

        return true;
    }

    public void editarMedico() {
        Utilitarios.print("Informe o CRM do médico a editar: ");
        String crm = sc.nextLine();

        Medico antigo = buscar(crm);
        if (antigo == null) {
            Utilitarios.println("Médico não encontrado.\n");
            return;
        }

        Utilitarios.println("\n--- EDITAR MÉDICO ---");

        Utilitarios.print("Novo nome (atual: " + antigo.getNome() + "): ");
        String nome = sc.nextLine();

        Utilitarios.print("Novo telefone (atual: " + antigo.getTelefone() + "): ");
        String telefone = sc.nextLine();

        Utilitarios.print("Novo email (atual: " + antigo.getEmail() + "): ");
        String email = sc.nextLine();

        Utilitarios.print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();

        Utilitarios.print("Nova especialidade (atual: " + antigo.getEspecialidade() + "): ");
        String especialidade = sc.nextLine();

        Medico novo = new Medico(
                antigo.getId(),
                nome,
                antigo.getCpf(),
                telefone,
                email,
                endereco,
                antigo.getSenha(),
                antigo.getCrm(),
                especialidade
        );

        if (!novo.validar()) {
            Utilitarios.println("ERRO: " + novo.getMensagemValidacao());
            return;
        }

        if (editar(crm, novo)) {
            Utilitarios.println("Médico atualizado com sucesso!\n");
        } else {
            Utilitarios.println("Erro ao atualizar médico.\n");
        }
    }

    @Override
    public boolean remover(String crm) {
        var m = buscar(crm);
        if (m != null) {
            hospital.getPessoas().remove(m);
            return true;
        }
        return false;
    }

    public void removerMedico() {
        Utilitarios.print("CRM do médico para remover: ");
        String crm = sc.nextLine();

        if (remover(crm)) {
            Utilitarios.println("Médico removido com sucesso!\n");
        } else {
            Utilitarios.println("Médico não encontrado.\n");
        }
    }

    public List<Medico> getListaMedicos() {
        return medicos;
    }
}
