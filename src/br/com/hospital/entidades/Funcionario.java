package br.com.hospital.entidades;

import br.com.hospital.exceptions.FuncionarioException;
import br.com.hospital.exceptions.PacienteException;
import br.com.hospital.interfaces.Validavel;
import br.com.hospital.utilitarios.Utilitarios;

public class Funcionario extends Pessoa {
    private String cargo;
    private String setor;
    private String nivelAcesso; // Ver se é necessário o uso de nivel Acesso aqui e em Pessoa :D

    public Funcionario(int id, String nome, String cpf, String telefone, String email, String endereco,String senha, String nivelAcesso, String cargo, String setor)
        throws FuncionarioException {

        //Exceções:
        if (!Utilitarios.textoNaoVazio(nome)) {
            throw new FuncionarioException("Nome inválido.");
        }
        if (!Utilitarios.cpfValido(cpf)) {
            throw new FuncionarioException("CPF inválido.");
        }
        if (!Utilitarios.telefoneValido(telefone)) {
            throw new FuncionarioException("Telefone inválido.");
        }
        if (!Utilitarios.emailValido(email)) {
            throw new FuncionarioException("E-mail inválido.");
        }
        if (!Utilitarios.textoNaoVazio(endereco)) {
            throw new FuncionarioException("Endereço inválido.");
        }
        if (!Utilitarios.textoNaoVazio(senha) || senha.length() < 4) {
            throw new FuncionarioException("Senha inválida. Mínimo de 4 caracteres.");
        }
        if (!Utilitarios.textoNaoVazio(nivelAcesso)) {
            throw new FuncionarioException("Nível de acesso inválido.");
        }

        // -------- Validações específicas do funcionario --------
        if (!Utilitarios.textoNaoVazio(cargo)) {
            throw new FuncionarioException("Cargo inválido.");
        }
        if (cargo.length() < 2) {
            throw new FuncionarioException("Cargo deve ter pelo menos 2 caracteres.");
        }
        if (!Utilitarios.textoNaoVazio(setor)) {
            throw new FuncionarioException("Setor inválido.");
        }
        if (setor.length() < 2) {
            throw new FuncionarioException("Setor deve ter pelo menos 2 caracteres.");
        }

        super(id, nome, cpf, telefone, email, endereco, senha, nivelAcesso);

        this.cargo = cargo;
        this.setor = setor;

    }

    //getters e setters
    public String getCargo(){
        return cargo;
    }

    public void setCargo(String cargo){
        this.cargo = cargo;
    }

    public String getSetor(){
        return setor;
    }

    public void setSetor(String setor){
        this.setor = setor;
    }

    public String getNivelAcesso(){
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso){
        this.nivelAcesso = nivelAcesso;
    }

    //métodos
    @Override
    public void exibirInformacoes(){
        super.exibirInformacoes();
        System.out.printf("""
                Cargo: %s
                Setor: %s
                Nível de Acesso: %s
                
                """, getCargo(), getSetor(), getNivelAcesso());
    }
}
