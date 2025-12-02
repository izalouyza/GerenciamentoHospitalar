package br.com.hospital.sistema;

import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;

public class Hospital implements Gerenciavel<Pessoa> {
    final private List<Pessoa> pessoasRegistradas;

    public Hospital() {
        this.pessoasRegistradas = new ArrayList<>();
    }

    public List<Pessoa> getPessoasRegistradas() {
        return pessoasRegistradas;
    }

    @Override
    public void adicionar(Pessoa elemento) {
        pessoasRegistradas.add(elemento);
    }

    @Override
    public void listar() {
        if (pessoasRegistradas.isEmpty()) {
            System.out.println("Nenhuma pessoa registrada, não é possível listar.");
            return;
        }
        for (Pessoa pessoaListar : pessoasRegistradas) {
            pessoaListar.exibirInformacoes();
        }
    }

    @Override
    public Pessoa buscar(String identificador) {
        for (Pessoa pessoaBuscar : pessoasRegistradas) {

            // CPF pode ser null
            if (pessoaBuscar.getCpf() != null &&
                    pessoaBuscar.getCpf().equals(identificador)) {

                pessoaBuscar.exibirInformacoes();
                return pessoaBuscar;
            }

            // CRM também pode ser null
            if (pessoaBuscar instanceof Medico) {
                Medico medico = (Medico) pessoaBuscar;

                if (medico.getCrm() != null &&
                        medico.getCrm().equals(identificador)) {

                    pessoaBuscar.exibirInformacoes();
                    return pessoaBuscar;
                }
            }
        }
        return null;
    }

    @Override
    public boolean editar(String identificador, Pessoa novoElemento) {
        for (int i = 0; i < pessoasRegistradas.size(); i++) {

            Pessoa atual = pessoasRegistradas.get(i);

            if (atual.getCpf() != null &&
                    atual.getCpf().equals(identificador)) {

                pessoasRegistradas.set(i, novoElemento);
                return true;
            }

            if (atual instanceof Medico) {
                Medico medico = (Medico) atual;

                if (medico.getCrm() != null &&
                        medico.getCrm().equals(identificador)) {

                    pessoasRegistradas.set(i, novoElemento);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean remover(String identificador) {
        return pessoasRegistradas.removeIf(pessoaRemovivel -> {

            if (pessoaRemovivel.getCpf() != null &&
                    pessoaRemovivel.getCpf().equals(identificador)) {

                return true;
            }

            if (pessoaRemovivel instanceof Medico) {
                Medico medico = (Medico) pessoaRemovivel;

                return medico.getCrm() != null &&
                        medico.getCrm().equals(identificador);
            }

            return false;
        });
    }
}
