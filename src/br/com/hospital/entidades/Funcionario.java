package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import br.com.hospital.enums.NivelAcesso;
import br.com.hospital.sistema.UsuarioSistema;

import static br.com.hospital.utilitarios.Utilitarios.*;

public class Funcionario extends Pessoa implements Validavel {

    private String cargo;
    private String setor;

    private UsuarioSistema credenciais; // login do funcionário

    private String mensagemValidacao = "";

    public Funcionario(
            String id,
            String nome,
            String cpf,
            String telefone,
            String email,
            String endereco,
            String cargo,
            String setor,
            UsuarioSistema credenciais
    ) {
        super(id, nome, cpf, telefone, email, endereco);

        this.cargo = cargo;
        this.setor = setor;
        this.credenciais = credenciais;
    }

    // -------------------------
    // GETTERS
    // -------------------------

    public String getCargo() {
        return cargo;
    }

    public String getSetor() {
        return setor;
    }

    public UsuarioSistema getCredenciais() {
        return credenciais;
    }

    // -------------------------
    // Exibição
    // -------------------------

    @Override
    public void exibirInformacoes() {
        super.exibirDadosBasicos(); // Mudou aqui
        Println("Cargo: " + cargo);
        Println("Setor: " + setor);
        Println("Nível de acesso: " + credenciais.getNivel());
        Println("--------------------------------------------------");
    }

    // -------------------------
    // Validação
    // -------------------------

    @Override
    public boolean validar() {

        if (!super.validar()) {
            mensagemValidacao = super.getMensagemValidacao();
            return false;
        }

        if (cargo == null || cargo.isBlank()) {
            mensagemValidacao = "O cargo não pode ser vazio.";
            return false;
        }

        if (setor == null || setor.isBlank()) {
            mensagemValidacao = "O setor não pode ser vazio.";
            return false;
        }

        if (credenciais == null) {
            mensagemValidacao = "Credenciais não atribuídas ao funcionário.";
            return false;
        }

        if (credenciais.getNivel() != NivelAcesso.ADMIN &&
                credenciais.getNivel() != NivelAcesso.SECRETARIA) {

            mensagemValidacao = "Funcionário deve ter nível ADMIN ou SECRETARIA.";
            return false;
        }

        mensagemValidacao = "";
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        return mensagemValidacao;
    }
}