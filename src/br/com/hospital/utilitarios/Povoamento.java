package br.com.hospital.utilitarios;

import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Paciente;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.enums.NivelAcesso;
import br.com.hospital.sistema.UsuarioSistema;

import java.util.List;

import static br.com.hospital.utilitarios.Utilitarios.*;

public class Povoamento {

    public static void carregarMedicos(Hospital hospital, List<UsuarioSistema> usuarios) {

        UsuarioSistema u1 = new UsuarioSistema("drana", "1234", NivelAcesso.MEDICO);
        usuarios.add(u1);
        Medico m1 = new Medico(
                gerarIdUnico(),
                "Ana Mendes Golçalvez",
                "98765432100",
                "11994296366",
                "anamenvez@hospital.com",
                "Av. Central, 400",
                "98765-SP",
                "Pediatria",
                u1
        );
        hospital.adicionarPessoa(m1);



        UsuarioSistema u2 = new UsuarioSistema("drcaval", "1234", NivelAcesso.MEDICO);
        usuarios.add(u2);
        Medico m2 = new Medico(
                gerarIdUnico(),
                "Cavalcante Henrique de Almeida",
                "11144477735",
                "11999996366",
                "cavalcanteha@gmail.com",
                "Av. Central, 203",
                "98761-SP",
                "Clínico Geral",
                u2
        );
        hospital.adicionarPessoa(m2);

        UsuarioSistema u3 = new UsuarioSistema("guerreiro132", "1234", NivelAcesso.MEDICO);
        usuarios.add(u3);
        Medico m3 = new Medico(
                gerarIdUnico(),
                "Walber José Adriano Silva",
                "50103526056",
                "84915732231",
                "guerreirowalber21@gmail.com",
                "Av. Martinho Ribeira, 231",
                "241-RN",
                "Cirurgião",
                u3
        );
        hospital.adicionarPessoa(m3);

        UsuarioSistema u4 = new UsuarioSistema("margarita", "1234", NivelAcesso.MEDICO);
        usuarios.add(u4);
        Medico m4 = new Medico(
                gerarIdUnico(),
                "Margarete Monte Garcia",
                "936.070.510-11",
                "84961234641",
                "margatemed@gmail.com",
                "Rua José Paulino, 184",
                "111-RN",
                "Dermatologista",
                u4
        );
        hospital.adicionarPessoa(m4);

        Println("Médicos carregados: " + hospital.getPessoas().stream().filter(p -> p instanceof Medico).count());
    }

    public static void carregarPacientes(Hospital hospital) {

        Paciente p1 = new Paciente(
                gerarIdUnico(),
                "José Luiz Feitosa",
                "52998224725",
                "84932142314",
                "zezinho1232@gmail.com",
                "Rua dos Palmares, 213",
                11,
                "Dor nas Costas"
        );
        hospital.adicionarPessoa(p1);

        Paciente p2 = new Paciente(
                gerarIdUnico(),
                "Maria Luiza da Silva",
                "15350946056",
                "84998232415",
                "mariasilv4@gmail.com",
                "Avenida das Dores, 180",
                32,
                "Dor no ventre, enxaqueca"
        );
        hospital.adicionarPessoa(p2);

        Paciente p3 = new Paciente(
                gerarIdUnico(),
                "Gael Oliveira",
                "74697131401",
                "84996913411",
                "gaelsousaa@gmail.com",
                "Avenida Hamilton, 480",
                24,
                "Falta de ar"
        );
        hospital.adicionarPessoa(p3);

        Paciente p4 = new Paciente(
                gerarIdUnico(),
                "Terezinha Maria da Silva",
                "81449378056", // CPF alterado para não duplicar
                "84991752567",
                "terezamaria83@gmail.com",
                "Rua Hemetério Fernandes, 812",
                64,
                "Pressão baixa, tontura"
        );
        hospital.adicionarPessoa(p4);

        Paciente p5 = new Paciente(
                gerarIdUnico(),
                "Ana Sara Ribeira",
                "11673907091",
                "84996777127",
                "mareeomarana@gmail.com",
                "Centro, 281",
                24,
                "Acidente, ferida aberta, hemorragia"
        );
        hospital.adicionarPessoa(p5);

        Paciente p6 = new Paciente(
                gerarIdUnico(),
                "Felipe Ribeira da Silva",
                "31401793088",
                "84991177127",
                "felipejogadorri@gmail.com",
                "Centro, 281",
                27,
                "Desorientação, braço quebrado"
        );
        hospital.adicionarPessoa(p6);

        Println("Pacientes carregados: " + hospital.getPessoas().stream().filter(p -> p instanceof Paciente).count());
    }

    public static void usuariosTeste(List<UsuarioSistema> usuarios){
        usuarios.add(new UsuarioSistema("admin", "admin", NivelAcesso.ADMIN));
        usuarios.add(new UsuarioSistema("secretaria", "1234", NivelAcesso.SECRETARIA));
        usuarios.add(new UsuarioSistema("medico", "1234", NivelAcesso.MEDICO));

        Println("Usuários de teste carregados: " + usuarios.size());
    }
}
