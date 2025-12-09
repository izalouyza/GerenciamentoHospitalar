package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Funcionario;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.enums.NivelAcesso;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.sistema.UsuarioSistema;

import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorFuncionario implements Gerenciavel<Funcionario> {

    private final Hospital hospital;
    private final Scanner sc;
    private final List<UsuarioSistema> usuariosSistema;

    public GerenciadorFuncionario(Hospital hospital, Scanner sc, List<UsuarioSistema> usuariosSistema) {
        this.hospital = hospital;
        this.sc = sc;
        this.usuariosSistema = usuariosSistema;
    }

    // Adicionar/Cadastrar
    @Override
    public void adicionar(Funcionario funcionario) {
        hospital.adicionarPessoa(funcionario);
        Println("Funcionário cadastrado com sucesso!\n");
    }

    public void cadastrarFuncionario() {
        Println("\n--- CADASTRO DE FUNCIONÁRIO ---");

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

        // LOGIN DO FUNCIONÁRIO
        Print("Usuário para login: ");
        String usuario = sc.nextLine();

        String senha = lerSenha();

        NivelAcesso nivel = escolherNivelAcesso();

        UsuarioSistema credenciais = new UsuarioSistema(
                usuario,
                senha,
                nivel
        );

        usuariosSistema.add(credenciais);

        // Cargo e Setor
        String cargo = lerCampoObrigatorio("Cargo");
        String setor = lerCampoObrigatorio("Setor");

        Funcionario funcionario = new Funcionario(
                gerarIdUnico(),
                capitalizarNome(nome),
                cpf,
                telefone,
                email,
                endereco,
                cargo,
                setor,
                credenciais
        );

        if (!funcionario.validar()) {
            Println("ERRO: " + funcionario.getMensagemValidacao());
            return;
        }

        adicionar(funcionario);
    }

    // Listar
    @Override
    public void listar() {

        List<Funcionario> funcionarios = hospital.getPessoas()
                .stream()
                .filter(p -> p instanceof Funcionario)
                .map(p -> (Funcionario) p)
                .sorted(Comparator.comparing(Funcionario::getNome))
                .toList();

        if (funcionarios.isEmpty()) {
            Println("Nenhum funcionário encontrado.\n");
            return;
        }

        Println("\n--- LISTA DE FUNCIONÁRIOS ---");
        funcionarios.forEach(f -> {
            f.exibirInformacoes();
            Println("---------------------------");
        });
    }

    public void listarFuncionarios() {
        listar();
    }

    //Buscar
    @Override
    public Funcionario buscar(String cpf) {
        var pessoa = hospital.buscarPessoa(cpf);
        return (pessoa instanceof Funcionario) ? (Funcionario) pessoa : null;
    }

    public void buscarFuncionario() {
        Print("Informe o CPF do funcionário: ");
        String cpf = sc.nextLine();

        Funcionario f = buscar(cpf);
        if (f == null) {
            Println("Funcionário não encontrado.\n");
            return;
        }

        Println("\n--- DADOS DO FUNCIONÁRIO ---");
        f.exibirInformacoes();
        Println("---------------------------\n");
    }

    //Editar
    @Override
    public boolean editar(String cpf, Funcionario novo) {
        Funcionario antigo = buscar(cpf);

        if (antigo == null) return false;

        hospital.getPessoas().remove(antigo);
        hospital.adicionarPessoa(novo);
        return true;
    }

    public void editarFuncionario() {

        Print("Informe o CPF do funcionário a editar: ");
        String cpf = sc.nextLine();

        Funcionario antigo = buscar(cpf);
        if (antigo == null) {
            Println("Funcionário não encontrado.\n");
            return;
        }

        Println("\n--- EDITAR FUNCIONÁRIO ---");

        String nome = lerCampoOpcional("Novo nome", antigo.getNome());
        String telefone = lerTelefoneOpcional(antigo.getTelefone());
        String email = lerEmailOpcional(antigo.getEmail());

        Print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
        String endereco = sc.nextLine();
        if (!textoNaoVazio(endereco)) endereco = antigo.getEndereco();

        String cargo = lerCampoOpcional("Novo cargo", antigo.getCargo());
        String setor = lerCampoOpcional("Novo setor", antigo.getSetor());

        Funcionario novo = new Funcionario(
                antigo.getId(),
                capitalizarNome(nome),
                antigo.getCpf(),
                telefone,
                email,
                endereco,
                cargo,
                setor,
                antigo.getCredenciais() // mantém usuário e senha
        );

        if (!novo.validar()) {
            Println("ERRO: " + novo.getMensagemValidacao());
            return;
        }

        if (editar(cpf, novo)) {
            Println("Funcionário atualizado com sucesso!\n");
        } else {
            Println("Erro ao atualizar funcionário.\n");
        }
    }

    //Remover
    @Override
    public boolean remover(String cpf) {
        Funcionario f = buscar(cpf);
        if (f != null) {
            hospital.getPessoas().remove(f);
            return true;
        }
        return false;
    }

    public void removerFuncionario() {
        Print("CPF do funcionário para remover: ");
        String cpf = sc.nextLine();

        if (remover(cpf)) {
            Println("Funcionário removido com sucesso!\n");
        } else {
            Println("Funcionário não encontrado.\n");
        }
    }

   // Métodos de Auxílio
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
            Print("Email: ");
            String e = sc.nextLine();

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

    private String lerEmailOpcional(String atual) {
        Print("Novo email (atual: " + atual + "): ");
        String e = sc.nextLine();

        if (!textoNaoVazio(e)) return atual;

        if (!emailValido(e)) {
            Println("Email inválido.");
            return atual;
        }

        if (hospital.emailExiste(e)) {
            Println("Email já cadastrado.");
            return atual;
        }

        return e;
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

    private NivelAcesso escolherNivelAcesso() {
        while (true) {
            Println("Nível de acesso:");
            Println("1 - ADMIN");
            Println("2 - SECRETARIA");
            Print("Escolha: ");
            String op = sc.nextLine();

            switch (op) {
                case "1": return NivelAcesso.ADMIN;
                case "2": return NivelAcesso.SECRETARIA;
                default:
                    Println("Opção inválida!");
            }
        }
    }
}
