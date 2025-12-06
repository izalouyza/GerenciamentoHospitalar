package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Medico;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.sistema.Hospital;
import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorMedico implements Gerenciavel<Medico> {

    private List<Medico> medicos = new ArrayList<>(); // lista local, opcional, geralmente pegamos do Hospital
    private final Hospital hospital; // referência ao hospital para manipular pessoas
    private final Scanner sc;

    public GerenciadorMedico(Hospital hospital, Scanner sc) {
        this.hospital = hospital;
        this.sc = sc;
    }

    @Override
    public void adicionar(Medico medico) {
        hospital.adicionarPessoa(medico); // adiciona ao hospital
        Println("Médico cadastrado com sucesso!\n");
    }

    public void cadastrarMedico() {
        Println("\n---CADASTRO DE MÉDICO---");

        // captura do nome do médico
        String nome = "";
        while (true) {
            Print("Nome: ");
            nome = sc.nextLine();

            if (!nome.isBlank()) break;
            Println("O nome não pode ser vazio!");
        }

        // captura do CPF e validação
        String cpf = "";
        while (true) {
            Print("CPF: ");
            cpf = sc.nextLine();

            if (!cpfValido(cpf)) {
                Println("CPF inválido!");
            } else if (hospital.cpfExiste(cpf)) {
                Println("Já existe uma pessoa com esse CPF!");
            } else {
                break;
            }
        }

        // captura do telefone e validação
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

        // captura do email e validação
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

        Print("Endereço: ");
        String endereco = sc.nextLine();

        // coleta de senha
        String senha = "";
        while (true) {
            Print("Senha: ");
            senha = sc.nextLine();

            if (!senha.isBlank()) break;
            Println("A senha não pode ser vazia!");
        }

        // coleta e validação do CRM
        String crm = "";
        while (true) {
            Print("CRM: ");
            crm = sc.nextLine();

            if (!crmValido(crm)) {
                Println("CRM inválido!");
            } else if (crmExiste(crm)) {
                Println("Já existe um médico com esse CRM!");
            } else {
                break;
            }
        }

        // coleta da especialidade
        String especialidade = "";
        while (true) {
            Print("Especialidade: ");
            especialidade = sc.nextLine();

            if (!especialidade.isBlank()) break;
            Println("A especialidade não pode ser vazia!");
        }

        // cria o objeto médico
        Medico m = new Medico(
                gerarIdUnico(),
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
            Println("ERRO: " + m.getMensagemValidacao());
            return;
        }

        adicionar(m); // adiciona ao hospital
    }

    @Override
    public void listar() {
        // filtra apenas os médicos cadastrados no hospital
        List<Medico> medicos = hospital.getPessoas().stream()
                .filter(p -> p instanceof Medico)
                .map(p -> (Medico) p)
                .sorted(Comparator.comparing(Medico::getNome)) // ordena pelo nome
                .toList();

        if (medicos.isEmpty()) {
            Println("Nenhum médico encontrado.\n");
            return;
        }

        // exibe cada médico
        Println("\n--- LISTA DE MÉDICOS ---");
        for (Medico m : medicos) {
            m.exibirInformacoes();
            Println("---------------------------");
        }
    }

    public void listarMedicos() {
        listar(); // método de apoio para o main
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
        // verifica se o CRM informado já existe no hospital
        for (var p : hospital.getPessoas()) {
            if (p instanceof Medico) {
                Medico m = (Medico) p;
                if (compararIdentificadores(m.getCrm(), crm)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void buscarMedico() {
        Print("Informe o CRM do médico: ");
        String crm = sc.nextLine();

        Medico m = buscar(crm);

        if (m == null) {
            Println("Médico não encontrado.\n");
            return;
        }

        Println("\n--- DADOS DO MÉDICO ---");
        m.exibirInformacoes();
        Println("---------------------------\n");
    }

    @Override
    public boolean editar(String crm, Medico novo) {
        Medico antigo = buscar(crm);

        if (antigo == null) return false;

        hospital.getPessoas().remove(antigo); // remove antigo
        hospital.adicionarPessoa(novo); // adiciona novo

        return true;
    }

    public void editarMedico() {
        Print("Informe o CRM do médico a editar: ");
        String crm = sc.nextLine();

        Medico antigo = buscar(crm);
        if (antigo == null) {
            Println("Médico não encontrado.\n");
            return;
        }

        Println("\n--- EDITAR MÉDICO ---");

        Print("Novo nome (atual: " + antigo.getNome() + "): ");
        String nome = sc.nextLine();

        Print("Novo telefone (atual: " + antigo.getTelefone() + "): ");
        String telefone = sc.nextLine();

        Print("Novo email (atual: " + antigo.getEmail() + "): ");
        String email = sc.nextLine();

        Print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();

        Print("Nova especialidade (atual: " + antigo.getEspecialidade() + "): ");
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
            Println("ERRO: " + novo.getMensagemValidacao());
            return;
        }

        if (editar(crm, novo)) {
            Println("Médico atualizado com sucesso!\n");
        } else {
            Println("Erro ao atualizar médico.\n");
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
        Print("CRM do médico para remover: ");
        String crm = sc.nextLine();

        if (remover(crm)) {
            Println("Médico removido com sucesso!\n");
        } else {
            Println("Médico não encontrado.\n");
        }
    }

   /* método não utilizado

    public List<Medico> getListaMedicos() {
        return medicos; // retorna a lista local (não necessariamente usada no main)
    }

    */
}
