package br.com.hospital.entidades;

import br.com.hospital.exceptions.FuncionarioException;
import br.com.hospital.utilitarios.Utilitarios;

public class Funcionario extends Pessoa {
    private String cargo;
    private String setor;
    private String nivelAcesso; // Ver se é necessário o uso de nivel Acesso aqui e em Pessoa :D

    public Funcionario(int id, String nome, String cpf, String telefone, String email, String endereco,String senha, String nivelAcesso, String cargo, String setor) {
        super(id, nome, cpf, telefone, email, endereco, senha, nivelAcesso);

        //Exceções:
        try {
            if (cargo == null || cargo.isBlank()) {
                throw new FuncionarioException("Cargo não pode ser vazio.");
            }
            if (setor == null || setor.isBlank()) {
                throw new FuncionarioException("Setor não pode ser vazio.");
            }
            if (getNome() == null || getNome().isBlank()) {
                throw new FuncionarioException("Nome inválido.");
            }
            if (!Utilitarios.cpfValido(getCpf())) {
                throw new FuncionarioException("CPF inválido.");
            }
            if (!Utilitarios.telefoneValido(getTelefone())) {
                throw new FuncionarioException("Telefone inválido.");
            }
            if (!Utilitarios.emailValido(getEmail())) {
                throw new FuncionarioException("Email inválido.");
            }

            // ----- Nível de Acesso -----
            if (nivelAcesso == null || nivelAcesso.isBlank()) {
                throw new FuncionarioException("Nível de acesso inválido.");
            }
            if (!nivelAcesso.matches("ADMIN|MEDICO|RECEPCIONISTA|FUNCIONARIO")) {
                throw new FuncionarioException("Nível de acesso não permitido.");
            }

        this.cargo = cargo;
        this.setor = setor;

        } catch (FuncionarioException e) {
            System.out.println("Erro ao criar funcionário: " + e.getMessage());
        }
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
