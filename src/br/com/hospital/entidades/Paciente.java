package br.com.hospital.entidades;

import br.com.hospital.interfaces.Validavel;
import br.com.hospital.exceptions.PacienteException;
import br.com.hospital.utilitarios.Utilitarios;

public class Paciente extends Pessoa implements Validavel {
    private int idade;
    private String historicoClinico;

    public Paciente(int id, String nome, String cpf, String telefone, String email, String endereco,String senha, String nivelAcesso, int idade, String historicoClinico) {
        super(id, nome, cpf, telefone, email, endereco,senha,nivelAcesso);

        //Exceções:
        try {
            if (nome == null || nome.isBlank()) {
                throw new PacienteException("Nome inválido.");
            }
            if (!Utilitarios.cpfValido(cpf)) {
                throw new PacienteException("CPF inválido.");
            }
            if (!Utilitarios.telefoneValido(telefone)) {
                throw new PacienteException("Telefone inválido.");
            }
            if (!Utilitarios.emailValido(email)) {
                throw new PacienteException("E-mail inválido.");
            }
            if (endereco == null || endereco.isBlank()) {
                throw new PacienteException("Endereço não pode ser vazio.");
            }
            if (senha == null || senha.length() < 4) {
                throw new PacienteException("Senha inválida. Mínimo de 4 caracteres.");
            }
            if (idade <= 0 || idade > 120) {
                throw new PacienteException("Idade inválida.");
            }
            if (historicoClinico == null || historicoClinico.isBlank()) {
                throw new PacienteException("Histórico clínico inválido.");
            }

        this.idade = idade;
        this.historicoClinico = historicoClinico;

        } catch (PacienteException e) {
            System.out.println("Erro ao criar paciente: " + e.getMessage());
        }
    }

    @Override
    public void exibirInformacoes(){
        super.exibirInformacoes();
        System.out.printf("""
                
                Dados Clínicos:
                Idade: %d
                Histórico Clínico: %s
                """, getIdade(), getHistoricoClinico());
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getHistoricoClinico() {
        return historicoClinico;
    }

    public void setHistoricoClinico(String historicoClinico) {
        this.historicoClinico = historicoClinico;
    }

    @Override
    public boolean validar() {
        if(getNome() == null || getNome().isBlank()){
            return false;
        }
        
        if(getCpf() == null || getCpf().length() != 11){
            return false;
        }

        if(idade <= 0 || idade > 120){
            return false;
        }

        if(historicoClinico == null || historicoClinico.isBlank()){
            return false;
        }
        return true;
    }

    @Override
    public String getMensagemValidacao() {
        if (getNome() == null || getNome().isBlank()) {
            return "Nome não pode ser vazio.";
        }

        if (getCpf() == null || getCpf().length() != 11) {
            return "CPF inválido. Deve conter 11 dígitos.";
        }

        if (idade <= 0 || idade > 120) {
            return "Idade inválida.";
        }

        if (historicoClinico == null || historicoClinico.isBlank()) {
            return "Histórico clínico não pode ser vazio.";
        }

        return "Paciente válido.";
    }
}
