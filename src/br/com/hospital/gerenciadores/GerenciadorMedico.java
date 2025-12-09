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
    private final List<UsuarioSistema> usuariosSistema;

    public GerenciadorMedico(Hospital hospital, Scanner sc, List<UsuarioSistema> usuariosSistema) {
        this.hospital = hospital;
        this.sc = sc;
        this.usuariosSistema = usuariosSistema;
    }

    // ======================================================
    // CONFIRMAR 0 OU 1
    // ======================================================

    private boolean confirmarZeroUm(String msg) {
        while (true) {
            Print(msg + " (1 = SIM / 0 = CANCELAR): ");
            String r = sc.nextLine().trim();

            if (r.equals("1")) return true;
            if (r.equals("0")) return false;

            Println("Opção inválida! Digite apenas 1 ou 0.");
        }
    }

    private boolean cancelouComZero(String s) {
        return s != null && s.trim().equals("0");
    }

    // ======================================================
    // ADICIONAR
    // ======================================================

    @Override
    public void adicionar(Medico medico) {
        hospital.adicionarPessoa(medico);
        Println("Médico cadastrado com sucesso!\n");
    }

    public void cadastrarMedico() {
        Println("\n--- CADASTRO DE MÉDICO ---");
        Println("(Digite 0 para CANCELAR a qualquer momento)");

        // Nome
        String nome = lerCampoObrigatorioComCancelarZero("Nome");
        if (nome == null) return;

        // CPF
        String cpf = lerCpfNovoComCancelarZero();
        if (cpf == null) return;

        // Telefone
        String telefone = lerTelefoneComCancelarZero();
        if (telefone == null) return;

        // Email
        String email = lerEmailComCancelarZero();
        if (email == null) return;

        // Endereço
        String endereco = lerCampoObrigatorioComCancelarZero("Endereço");
        if (endereco == null) return;

        // CRM
        String crm = lerCRMComCancelarZero();
        if (crm == null) return;

        // Especialidade
        String especialidade = lerCampoObrigatorioComCancelarZero("Especialidade");
        if (especialidade == null) return;

        // Login
        Print("Usuário para login (0 = cancelar): ");
        String usuario = sc.nextLine();
        if (cancelouComZero(usuario)) return;

        String senha = lerSenhaComCancelarZero();
        if (senha == null) return;

        UsuarioSistema credenciais = new UsuarioSistema(usuario, senha, NivelAcesso.MEDICO);
        usuariosSistema.add(credenciais);

        // CONFIRMAR DADOS
        Println("\n--- CONFIRMAR CADASTRO ---");
        Println("Nome: " + nome);
        Println("CPF: " + cpf);
        Println("Telefone: " + telefone);
        Println("Email: " + email);
        Println("Endereço: " + endereco);
        Println("CRM: " + crm);
        Println("Especialidade: " + especialidade);
        Println("Usuário: " + usuario);

        if (!confirmarZeroUm("Confirmar cadastro?")) return;

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

        adicionar(medico);
    }

    // ======================================================
    // LISTAR
    // ======================================================

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

    // ======================================================
    // BUSCAR (LOOP ATÉ ACHAR)
    // ======================================================

    @Override
    public Medico buscar(String crm) {
        var pessoa = hospital.buscarPessoa(crm);
        return (pessoa instanceof Medico) ? (Medico) pessoa : null;
    }

    public void buscarMedico() {
        while (true) {
            Print("Informe o CRM (ou 0 para CANCELAR): ");
            String crm = sc.nextLine();

            if (crm.equals("0")) return;

            Medico m = buscar(crm);
            if (m == null) {
                Println("Médico não encontrado! Tente novamente.\n");
                continue;
            }

            Println("\n--- DADOS DO MÉDICO ---");
            m.exibirInformacoes();
            Println("---------------------------\n");
            return;
        }
    }

    // ======================================================
    // EDITAR (PADRÃO PACIENTE)
    // ======================================================

    @Override
    public boolean editar(String crm, Medico novo) {
        Medico antigo = buscar(crm);
        if (antigo == null) return false;

        hospital.getPessoas().remove(antigo);
        hospital.adicionarPessoa(novo);
        return true;
    }

    public void editarMedico() {

        Println("\n--- EDITAR MÉDICO ---");

        Medico antigo;

        while (true) {
            Print("CRM do médico (ou 0 para cancelar): ");
            String crm = sc.nextLine();
            if (crm.equals("0")) return;

            antigo = buscar(crm);
            if (antigo != null) break;

            Println("Médico não encontrado! Tente novamente.\n");
        }

        Println("\n(ENTER = manter atual / 0 = CANCELAR)");

        String nome = lerCampoOpcionalComCancelarZeroLoop("Novo nome", antigo.getNome());
        if (nome == null) return;

        String telefone = lerTelefoneOpcionalComCancelarZeroLoop(antigo.getTelefone());
        if (telefone == null) return;

        String email = lerEmailOpcionalComCancelarZeroLoop(antigo.getEmail());
        if (email == null) return;

        Print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();
        if (cancelouComZero(endereco)) return;
        if (!textoNaoVazio(endereco)) endereco = antigo.getEndereco();

        String especialidade = lerCampoOpcionalComCancelarZeroLoop("Nova especialidade", antigo.getEspecialidade());
        if (especialidade == null) return;

        // CONFIRMAR
        Println("\n--- CONFIRMAR ALTERAÇÕES ---");
        Println("Nome: " + nome);
        Println("Telefone: " + telefone);
        Println("Email: " + email);
        Println("Endereço: " + endereco);
        Println("Especialidade: " + especialidade);

        if (!confirmarZeroUm("Confirmar alterações?")) return;

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

        editar(antigo.getCrm(), novo);
        Println("Médico atualizado com sucesso!\n");
    }

    // ======================================================
    // REMOVER (LOOP + CONFIRMAÇÃO)
    // ======================================================

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
        Println("\n--- REMOVER MÉDICO ---");

        Medico m;

        while (true) {
            Print("Informe o CRM (ou 0 para CANCELAR): ");
            String crm = sc.nextLine();

            if (crm.equals("0")) return;

            m = buscar(crm);
            if (m != null) break;

            Println("Médico não encontrado! Tente novamente.\n");
        }

        Println("\n--- CONFIRMAR REMOÇÃO ---");
        m.exibirInformacoes();

        if (!confirmarZeroUm("Deseja realmente remover?")) return;

        remover(m.getCrm());
        Println("Médico removido com sucesso!");
    }

    // ======================================================
    // MÉTODOS AUXILIARES REUTILIZADOS DO PACIENTE
    // ======================================================

    private String lerCampoObrigatorioComCancelarZero(String nomeCampo) {
        while (true) {
            Print(nomeCampo + ": ");
            String valor = sc.nextLine();

            if (cancelouComZero(valor)) return null;
            if (!textoNaoVazio(valor)) {
                Println(nomeCampo + " não pode ficar vazio.");
                continue;
            }
            return valor;
        }
    }

    private String lerCampoOpcionalComCancelarZeroLoop(String msg, String atual) {
        while (true) {
            Print(msg + " (atual: " + atual + "): ");
            String v = sc.nextLine();

            if (cancelouComZero(v)) return null;
            if (!textoNaoVazio(v)) return atual;
            return v;
        }
    }

    private String lerCpfNovoComCancelarZero() {
        while (true) {
            Print("CPF: ");
            String cpf = sc.nextLine();

            if (cancelouComZero(cpf)) return null;

            if (!cpfValido(cpf)) {
                Println("CPF inválido!");
                continue;
            }

            if (hospital.cpfExiste(cpf)) {
                Println("CPF já cadastrado!");
                continue;
            }

            return cpf;
        }
    }

    private String lerTelefoneComCancelarZero() {
        while (true) {
            Print("Telefone: ");
            String t = sc.nextLine();

            if (cancelouComZero(t)) return null;
            if (telefoneValido(t)) return t;

            Println("Telefone inválido.");
        }
    }

    private String lerTelefoneOpcionalComCancelarZeroLoop(String atual) {
        while (true) {
            Print("Novo telefone (atual: " + atual + "): ");
            String t = sc.nextLine();

            if (cancelouComZero(t)) return null;
            if (!textoNaoVazio(t)) return atual;

            if (telefoneValido(t)) return t;
            Println("Telefone inválido.");
        }
    }

    private String lerEmailComCancelarZero() {
        while (true) {
            Print("Email: ");
            String e = sc.nextLine();

            if (cancelouComZero(e)) return null;

            if (!emailValido(e)) {
                Println("Email inválido!");
                continue;
            }

            if (hospital.emailExiste(e)) {
                Println("Email já cadastrado!");
                continue;
            }

            return e;
        }
    }

    private String lerEmailOpcionalComCancelarZeroLoop(String atual) {
        while (true) {
            Print("Novo email (atual: " + atual + "): ");
            String e = sc.nextLine();

            if (cancelouComZero(e)) return null;
            if (!textoNaoVazio(e)) return atual;

            if (!emailValido(e)) {
                Println("Email inválido.");
                continue;
            }

            if (hospital.emailExiste(e)) {
                Println("Email já cadastrado.");
                continue;
            }

            return e;
        }
    }

    private String lerCRMComCancelarZero() {
        while (true) {
            Print("CRM: ");
            String crm = sc.nextLine();

            if (cancelouComZero(crm)) return null;

            if (!crmValido(crm)) {
                Println("CRM inválido!");
                continue;
            }

            if (crmExiste(crm)) {
                Println("CRM já cadastrado!");
                continue;
            }

            return crm;
        }
    }

    private boolean crmExiste(String crm) {
        return hospital.getPessoas().stream()
                .filter(p -> p instanceof Medico)
                .map(p -> (Medico) p)
                .anyMatch(m -> compararIdentificadores(m.getCrm(), crm));
    }

    private String lerSenhaComCancelarZero() {
        while (true) {
            Print("Senha para login: ");
            String s = sc.nextLine();

            if (cancelouComZero(s)) return null;
            if (!textoNaoVazio(s)) {
                Println("Senha não pode ser vazia.");
                continue;
            }

            return s;
        }
    }
}
