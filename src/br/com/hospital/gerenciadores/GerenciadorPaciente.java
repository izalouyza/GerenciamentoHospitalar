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

    // ==========================================================
    // CONFIRMAÇÃO 0 OU 1 (NOVO MÉTODO)
    // ==========================================================

    private boolean confirmarZeroUm(String mensagem) { // ALTERADO
        while (true) {
            Print(mensagem + " (1 = SIM / 0 = CANCELAR): ");
            String r = sc.nextLine().trim();
            if (r.equals("1")) return true;
            if (r.equals("0")) return false;
            Println("Opção inválida! Digite apenas 1 ou 0.");
        }
    }

    // ==========================================================
    // CADASTRAR PACIENTE
    // ==========================================================

    @Override
    public void adicionar(Paciente paciente) {
        hospital.adicionarPessoa(paciente);
        Println("Paciente cadastrado com sucesso!");
    }

    public void cadastrarPaciente() {
        Println("\n--- CADASTRO DE PACIENTE ---");
        Println("(Digite 0 a qualquer momento para CANCELAR)");

        String nome = lerCampoObrigatorioComCancelarZero("Nome");
        if (nome == null) return;

        int idade = lerIdadeComCancelarZero();
        if (idade == -1) return;

        String principalQueixa = lerCampoObrigatorioComCancelarZero("Principal queixa");
        if (principalQueixa == null) return;

        String cpf = lerCpfNovoComCancelarZero();
        if (cpf == null) return;

        String telefone = lerTelefoneComCancelarZero();
        if (telefone == null) return;

        String email = lerEmailComCancelarZero();
        if (email == null) return;

        String endereco = lerCampoObrigatorioComCancelarZero("Endereço");
        if (endereco == null) return;

        // CONFIRMAÇÃO FINAL COM VALIDAÇÃO 0 OU 1
        Println("\n--- CONFIRMAR CADASTRO ---");
        Println("Nome: " + nome);
        Println("Idade: " + idade);
        Println("Queixa: " + principalQueixa);
        Println("CPF: " + cpf);
        Println("Telefone: " + telefone);
        Println("Email: " + email);
        Println("Endereço: " + endereco);

        if (!confirmarZeroUm("\nConfirmar cadastro?")) return; // ALTERADO

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

    // ==========================================================
    // LISTAR
    // ==========================================================

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

    // ==========================================================
    // BUSCAR (AGORA TENTA DE NOVO ATÉ ACHAR)
    // ==========================================================

    @Override
    public Paciente buscar(String cpf) {
        var pessoa = hospital.buscarPessoa(cpf);
        return (pessoa instanceof Paciente) ? (Paciente) pessoa : null;
    }

    public void buscarPaciente() {
        while (true) { // ALTERADO (loop até encontrar)
            Print("Informe o CPF do paciente (ou 0 para CANCELAR): ");
            String cpf = sc.nextLine();
            if (cpf.equals("0")) return;

            Paciente p = buscar(cpf);
            if (p == null) {
                Println("Paciente não encontrado! Tente novamente.\n");
                continue;
            }

            Println("\n--- DADOS DO PACIENTE ---");
            p.exibirInformacoes();
            Println("---------------------------\n");
            return;
        }
    }

    // ==========================================================
    // EDITAR (CONFIRMAÇÃO 0 OU 1)
    // ==========================================================

    @Override
    public boolean editar(String cpf, Paciente novo) {
        Paciente antigo = buscar(cpf);

        if (antigo == null) return false;

        hospital.getPessoas().remove(antigo);
        hospital.adicionarPessoa(novo);
        return true;
    }

    public void editarPaciente() {

        Println("\n--- EDITAR PACIENTE ---");

        // BUSCA COM LOOP (mesma lógica da busca)
        Paciente antigo;
        while (true) { // ALTERADO
            Print("Informe o CPF do paciente a editar (ou 0 para CANCELAR): ");
            String cpf = sc.nextLine();
            if (cpf.equals("0")) return;

            antigo = buscar(cpf);
            if (antigo != null) break;

            Println("Paciente não encontrado! Tente novamente.\n");
        }

        Println("\n(ENTER = manter atual / 0 = CANCELAR)");

        String nome = lerCampoOpcionalComCancelarZeroLoop("Novo nome", antigo.getNome());
        if (nome == null) return;

        int idade = lerIdadeOpcionalComCancelarZeroLoop("Nova idade", antigo.getIdade());
        if (idade == -1) return;

        String principalQueixa = lerCampoOpcionalComCancelarZeroLoop("Nova queixa", antigo.getPrincipalQueixa());
        if (principalQueixa == null) return;

        String telefone = lerTelefoneOpcionalComCancelarZeroLoop(antigo.getTelefone());
        if (telefone == null) return;

        String email = lerEmailOpcionalComCancelarZeroLoop(antigo.getEmail());
        if (email == null) return;

        Print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();
        if (endereco.equals("0")) return;
        if (!textoNaoVazio(endereco)) endereco = antigo.getEndereco();

        // CONFIRMAÇÃO FINAL COM 0/1
        Println("\n--- CONFIRMAR ALTERAÇÕES ---");
        Println("Nome: " + nome);
        Println("Idade: " + idade);
        Println("Queixa: " + principalQueixa);
        Println("Telefone: " + telefone);
        Println("Email: " + email);
        Println("Endereço: " + endereco);

        if (!confirmarZeroUm("\nConfirmar alterações?")) return; // ALTERADO

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

        editar(antigo.getCpf(), novo);
        Println("Paciente atualizado com sucesso!\n");
    }

    // ==========================================================
    // REMOVER (TENTAR ATÉ ACHAR + CONFIRMAÇÃO 0/1)
    // ==========================================================

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
        Println("\n--- REMOVER PACIENTE ---");

        Paciente p;

        while (true) { // ALTERADO
            Print("CPF do paciente para remover (ou 0 para CANCELAR): ");
            String cpf = sc.nextLine();
            if (cpf.equals("0")) return;

            p = buscar(cpf);
            if (p != null) break;

            Println("Paciente não encontrado! Tente novamente.\n");
        }

        Println("\n--- CONFIRMAR REMOÇÃO ---");
        p.exibirInformacoes();

        if (!confirmarZeroUm("\nDeseja realmente remover?")) return; // ALTERADO

        hospital.getPessoas().remove(p);
        Println("Paciente removido com sucesso!");
        Println("Nome: " + p.getNome());
        Println("CPF: " + p.getCpf() + "\n");
    }

    // ==========================================================
    // MÉTODOS AUXILIARES (mesmos do seu código)
    // ==========================================================

    private boolean cancelouComZero(String s) {
        return s != null && s.trim().equals("0");
    }

    private String lerCampoObrigatorioComCancelarZero(String nomeCampo) {
        String valor;
        while (true) {
            Print(nomeCampo + ": ");
            valor = sc.nextLine();
            if (cancelouComZero(valor)) return null;
            if (!textoNaoVazio(valor)) {
                Println(nomeCampo + " não pode ficar vazio.");
                continue;
            }
            return valor;
        }
    }

    private String lerCampoOpcionalComCancelarZeroLoop(String mensagem, String atual) {
        while (true) {
            Print(mensagem + " (atual: " + atual + "): ");
            String valor = sc.nextLine();
            if (cancelouComZero(valor)) return null;
            if (!textoNaoVazio(valor)) return atual;
            return valor;
        }
    }

    private int lerIdadeComCancelarZero() {
        while (true) {
            Print("Idade: ");
            String v = sc.nextLine();
            if (cancelouComZero(v)) return -1;
            try {
                int idade = Integer.parseInt(v);
                if (idade > 0) return idade;
            } catch (Exception ignored) {}
            Println("Idade inválida.");
        }
    }

    private int lerIdadeOpcionalComCancelarZeroLoop(String msg, int atual) {
        while (true) {
            Print(msg + " (atual: " + atual + "): ");
            String valor = sc.nextLine();
            if (cancelouComZero(valor)) return -1;
            if (!textoNaoVazio(valor)) return atual;
            try {
                int idade = Integer.parseInt(valor);
                if (idade > 0) return idade;
            } catch (Exception ignored) {}
            Println("Idade inválida.");
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
            if (hospital.buscarPessoa(cpf) != null) {
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
            Println("Telefone inválido!");
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
                Println("Esse email já está cadastrado!");
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
}
