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

    // -------------------------
    // Adicionar/Cadastrar
    // -------------------------
    @Override
    public void adicionar(Funcionario funcionario) {
        hospital.adicionarPessoa(funcionario);
        Println("Funcionário cadastrado com sucesso!\n");
    }

    public void cadastrarFuncionario() {
        Println("\n--- CADASTRO DE FUNCIONÁRIO ---");
        Println("Digite 0 em qualquer campo para cancelar o cadastro.\n");

        String nome = lerCampoObrigatorioOuCancelar("Nome");
        if (nome == null) return;

        String cpf = lerCpfNovoOuCancelar();
        if (cpf == null) return;

        String telefone = lerTelefoneOuCancelar();
        if (telefone == null) return;

        String email = lerEmailOuCancelar();
        if (email == null) return;

        Print("Endereço: ");
        String endereco = sc.nextLine();
        if (endereco.equals("0")) return;

        Print("Usuário para login: ");
        String usuario = sc.nextLine();
        if (usuario.equals("0")) return;

        String senha = lerSenhaOuCancelar();
        if (senha == null) return;

        NivelAcesso nivel = escolherNivelAcesso();
        UsuarioSistema credenciais = new UsuarioSistema(usuario, senha, nivel);
        usuariosSistema.add(credenciais);

        String cargo = lerCampoObrigatorioOuCancelar("Cargo");
        if (cargo == null) return;

        String setor = lerCampoObrigatorioOuCancelar("Setor");
        if (setor == null) return;

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

    // -------------------------
    // Listar
    // -------------------------
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

    // -------------------------
    // Buscar
    // -------------------------
    @Override
    public Funcionario buscar(String cpf) {
        var pessoa = hospital.buscarPessoa(cpf);
        return (pessoa instanceof Funcionario) ? (Funcionario) pessoa : null;
    }

    public void buscarFuncionario() {
        while (true) {
            Print("Informe o CPF do funcionário (ou 0 para voltar): ");
            String cpf = sc.nextLine();
            if (cpf.equals("0")) {
                Println("Operação cancelada.\n");
                break;
            }

            Funcionario f = buscar(cpf);
            if (f == null) {
                Println("Funcionário não encontrado. Tente novamente.\n");
            } else {
                Println("\n--- DADOS DO FUNCIONÁRIO ---");
                f.exibirInformacoes();
                Println("---------------------------\n");
                break;
            }
        }
    }

    // -------------------------
    // Editar
    // -------------------------
    @Override
    public boolean editar(String cpf, Funcionario novo) {
        Funcionario antigo = buscar(cpf);

        if (antigo == null) return false;

        hospital.getPessoas().remove(antigo);
        hospital.adicionarPessoa(novo);
        return true;
    }

    public void editarFuncionario() {
        while (true) {
            Print("Informe o CPF do funcionário a editar (ou 0 para voltar): ");
            String cpf = sc.nextLine();
            if (cpf.equals("0")) {
                Println("Operação cancelada.\n");
                break;
            }

            Funcionario antigo = buscar(cpf);
            if (antigo == null) {
                Println("Funcionário não encontrado. Tente novamente.\n");
            } else {
                Println("\n--- EDITAR FUNCIONÁRIO ---");
                Println("Digite 0 em qualquer campo para cancelar a edição.\n");

                String nome = lerCampoOpcionalOuCancelar("Novo nome", antigo.getNome());
                if (nome == null) return;

                String telefone = lerTelefoneOpcionalOuCancelar(antigo.getTelefone());
                if (telefone == null) return;

                String email = lerEmailOpcionalOuCancelar(antigo.getEmail());
                if (email == null) return;

                Print("Novo endereço (atual: " + antigo.getEndereco() + "): ");
                String endereco = sc.nextLine();
                if (endereco.equals("0")) return;
                if (!textoNaoVazio(endereco)) endereco = antigo.getEndereco();

                String cargo = lerCampoOpcionalOuCancelar("Novo cargo", antigo.getCargo());
                if (cargo == null) return;

                String setor = lerCampoOpcionalOuCancelar("Novo setor", antigo.getSetor());
                if (setor == null) return;

                Funcionario novo = new Funcionario(
                        antigo.getId(),
                        capitalizarNome(nome),
                        antigo.getCpf(),
                        telefone,
                        email,
                        endereco,
                        cargo,
                        setor,
                        antigo.getCredenciais()
                );

                if (!novo.validar()) {
                    Println("ERRO: " + novo.getMensagemValidacao());
                    return;
                }

                Println("\nDeseja realmente atualizar este funcionário?");
                Println("1 - Confirmar");
                Println("0 - Cancelar");
                while (true) {
                    Print("Escolha: ");
                    String opcao = sc.nextLine();
                    if (opcao.equals("1")) {
                        if (editar(cpf, novo)) {
                            Println("Funcionário atualizado com sucesso!\n");
                        } else {
                            Println("Erro ao atualizar funcionário.\n");
                        }
                        break;
                    } else if (opcao.equals("0")) {
                        Println("Edição cancelada.\n");
                        break;
                    } else {
                        Println("Opção inválida! Digite 1 para confirmar ou 0 para cancelar.");
                    }
                }
                break;
            }
        }
    }

    // -------------------------
    // Remover
    // -------------------------
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
        while (true) {
            Print("CPF do funcionário para remover (ou 0 para voltar): ");
            String cpf = sc.nextLine();
            if (cpf.equals("0")) {
                Println("Operação cancelada.\n");
                break;
            }

            Funcionario f = buscar(cpf);
            if (f == null) {
                Println("Funcionário não encontrado. Tente novamente.\n");
            } else {
                Println("\nDeseja realmente remover este funcionário?");
                Println("1 - Confirmar");
                Println("0 - Cancelar");
                while (true) {
                    Print("Escolha: ");
                    String opcao = sc.nextLine();
                    if (opcao.equals("1")) {
                        if (remover(cpf)) {
                            Println("Funcionário removido com sucesso!");
                            Println("Nome: " + f.getNome());
                            Println("CPF: " + f.getCpf() + "\n");
                        } else {
                            Println("Erro ao remover funcionário.\n");
                        }
                        break;
                    } else if (opcao.equals("0")) {
                        Println("Remoção cancelada.\n");
                        break;
                    } else {
                        Println("Opção inválida! Digite 1 para confirmar ou 0 para cancelar.");
                    }
                }
                break;
            }
        }
    }

    // -------------------------
    // Métodos auxiliares
    // -------------------------
    private String lerCampoObrigatorioOuCancelar(String nomeCampo) {
        String valor = "";
        while (!textoNaoVazio(valor)) {
            Print(nomeCampo + ": ");
            valor = sc.nextLine();
            if (valor.equals("0")) return null;
            if (!textoNaoVazio(valor)) {
                Println(nomeCampo + " não pode ficar vazio.");
            }
        }
        return valor;
    }

    private String lerCampoOpcionalOuCancelar(String mensagem, String atual) {
        Print(mensagem + " (atual: " + atual + "): ");
        String valor = sc.nextLine();
        if (valor.equals("0")) return null;
        return textoNaoVazio(valor) ? valor : atual;
    }

    private String lerCpfNovoOuCancelar() {
        while (true) {
            Print("CPF: ");
            String cpf = sc.nextLine();
            if (cpf.equals("0")) return null;
            if (!cpfValido(cpf)) {
                Println("CPF inválido!");
            } else if (hospital.cpfExiste(cpf)) {
                Println("CPF já cadastrado!");
            } else {
                return cpf;
            }
        }
    }

    private String lerTelefoneOuCancelar() {
        while (true) {
            Print("Telefone: ");
            String t = sc.nextLine();
            if (t.equals("0")) return null;
            if (telefoneValido(t)) return t;
            Println("Telefone inválido!");
        }
    }

    private String lerTelefoneOpcionalOuCancelar(String atual) {
        Print("Novo telefone (atual: " + atual + "): ");
        String t = sc.nextLine();
        if (t.equals("0")) return null;
        if (!textoNaoVazio(t)) return atual;
        if (!telefoneValido(t)) {
            Println("Telefone inválido.");
            return atual;
        }
        return t;
    }

    private String lerEmailOuCancelar() {
        while (true) {
            Print("Email: ");
            String e = sc.nextLine();
            if (e.equals("0")) return null;
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

    private String lerEmailOpcionalOuCancelar(String atual) {
        Print("Novo email (atual: " + atual + "): ");
        String e = sc.nextLine();
        if (e.equals("0")) return null;
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

    private String lerSenhaOuCancelar() {
        String senha = "";
        while (senha.isBlank()) {
            Print("Senha para login: ");
            senha = sc.nextLine();
            if (senha.equals("0")) return null;
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
