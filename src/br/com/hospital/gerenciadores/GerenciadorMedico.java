package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Medico;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.sistema.UsuarioSistema;
import br.com.hospital.enums.NivelAcesso;
import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorMedico implements Gerenciavel<Medico> {

    private final Hospital hospital;
    private final Scanner sc;
    private final List<UsuarioSistema> usuariosSistema; // lista usada pelo Login

    public GerenciadorMedico(Hospital hospital, Scanner sc, List<UsuarioSistema> usuariosSistema) {
        this.hospital = hospital;
        this.sc = sc;
        this.usuariosSistema = usuariosSistema;
    }

    // -------------------------
    // Adicionar
    // -------------------------

    @Override
    public void adicionar(Medico medico) {
        hospital.adicionarPessoa(medico);
        Println("Médico cadastrado com sucesso!\n");
    }

    public void cadastrarMedico() {
        Println("\n--- CADASTRO DE MÉDICO ---");

        String nome = lerCampoObrigatorio("Nome");

        // CPF
        String cpf = lerCpfNovo();

        // Telefone
        String telefone = lerTelefone();

        // Email
        String email = lerEmail();

        // Endereço
        Print("Endereço: ");
        String endereco = sc.nextLine();

        // CRM
        String crm = lerCRM();

        // Especialidade
        String especialidade = lerCampoObrigatorio("Especialidade");

        // LOGIN DO MÉDICO
        Print("Usuário para login: ");
        String usuario = sc.nextLine();

        String senha = lerSenha();

        UsuarioSistema credenciais = new UsuarioSistema(
                usuario,
                senha,
                NivelAcesso.MEDICO
        );

        // registra esse usuário na lista usada pelo Login
        usuariosSistema.add(credenciais);

        // Criar objeto médico
        Medico medico = new Medico(
                gerarIdUnico(),
                capitalizarNome(nome),
                cpf,
                telefone,
                email,
                endereco,
                crm,
                especialidade,
                credenciais
        );

        if (!medico.validar()) {
            Println("ERRO: " + medico.getMensagemValidacao());
            return;
        }

        adicionar(medico);
    }

    // -------------------------
    // Listar
    // -------------------------

    @Override
    public void listar() {

        List<Medico> medicos = hospital.getPessoas()
                .stream()
                .filter(p -> p instanceof Medico)
                .map(p -> (Medico) p)
                .sorted(Comparator.comparing(Medico::getNome))
                .toList();

        if (medicos.isEmpty()) {
            Println("Nenhum médico encontrado.\n");
            return;
        }

        Println("\n--- LISTA DE MÉDICOS ---");
        medicos.forEach(m -> {
            m.exibirInformacoes();
            Println("---------------------------");
        });
    }

    public void listarMedicos() {
        listar();
    }

    // -------------------------
    // Buscar
    // -------------------------

    @Override
    public Medico buscar(String crm) {
        var pessoa = hospital.buscarPessoa(crm);
        return (pessoa instanceof Medico) ? (Medico) pessoa : null;
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

    // -------------------------
    // Editar
    // -------------------------

    @Override
    public boolean editar(String crm, Medico novo) {
        Medico antigo = buscar(crm);

        if (antigo == null) return false;

        hospital.getPessoas().remove(antigo);
        hospital.adicionarPessoa(novo);
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

        String nome = lerCampoOpcional("Novo nome", antigo.getNome());
        String telefone = lerTelefoneOpcional(antigo.getTelefone());
        String email = lerEmailOpcional(antigo.getEmail());

        Print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();
        if (!textoNaoVazio(endereco)) endereco = antigo.getEndereco();

        String especialidade = lerCampoOpcional("Nova especialidade", antigo.getEspecialidade());

        // Mantém CRM e credenciais
        Medico novo = new Medico(
                antigo.getId(),
                capitalizarNome(nome),
                antigo.getCpf(),
                telefone,
                email,
                endereco,
                antigo.getCrm(),
                especialidade,
                antigo.getCredenciais()
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

    // -------------------------
    // Remover
    // -------------------------

    @Override
    public boolean remover(String crm) {
        Medico m = buscar(crm);
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

    // ============================================================
    // MÉTODOS AUXILIARES
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

    private String lerCpfNovo() {
        while (true) {
            Print("CPF: ");
            String cpf = sc.nextLine();
            if (!cpfValido(cpf)) {
                Println("CPF inválido!");
            } else if (hospital.cpfExiste(cpf)) {
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
            Print("E-mail: ");
            String e = sc.nextLine();
            if (emailValido(e)) return e;
            Println("E-mail inválido!");
        }
    }

    private String lerEmailOpcional(String atual) {
        Print("Novo e-mail (atual: " + atual + "): ");
        String e = sc.nextLine();
        if (!textoNaoVazio(e)) return atual;
        if (!emailValido(e)) {
            Println("E-mail inválido.");
            return atual;
        }
        return e;
    }

    private String lerCRM() {
        while (true) {
            Print("CRM: ");
            String crm = sc.nextLine();
            if (!crmValido(crm)) {
                Println("CRM inválido!");
            } else if (crmExiste(crm)) {
                Println("Já existe um médico com esse CRM!");
            } else {
                return crm;
            }
        }
    }

    private boolean crmExiste(String crm) {
        return hospital.getPessoas().stream()
                .filter(p -> p instanceof Medico)
                .map(p -> (Medico) p)
                .anyMatch(m -> compararIdentificadores(m.getCrm(), crm));
    }

    private String lerSenha() {
        String senha = "";
        while (senha.isBlank()) {
            Print("Senha para login: ");
            senha = sc.nextLine();
            if (senha.isBlank()) {
                Println("A senha não pode ser vazia!");
            }
        }
        return senha;
    }
}