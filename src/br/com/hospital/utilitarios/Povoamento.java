package br.com.hospital.utilitarios;

import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Paciente;
import br.com.hospital.sistema.Hospital;
import br.com.hospital.enums.NivelAcesso;
import br.com.hospital.sistema.UsuarioSistema;

import java.util.List;

import static br.com.hospital.utilitarios.Utilitarios.*;

public class Povoamento {

    public static void carregarmedicos (Hospital hospital, List<UsuarioSistema> usuarios) {
        UsuarioSistema u1 = new UsuarioSistema("drana", "1234", NivelAcesso.MEDICO);
        usuarios.add(u1);
        Medico m1 = new Medico(
                gerarIdUnico(),
                "Ana Mendes Golçalvez",
                "987.654.321-00",
                "(11)99429-6366",
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
                "111.444.777-35",
                "(11)99999-6366",
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
                "987.654.321-00",
                "(84)91573-2231",
                "guerreirowalber21@gmail.com",
                "Av. Martinho Ribeira, 231",
                "241-RN",
                "Cirugião",
                u3
        );
        hospital.adicionarPessoa(m3);

        UsuarioSistema u4 = new UsuarioSistema("guerreiro132", "1234", NivelAcesso.MEDICO);
        usuarios.add(u4);
        Medico m4 = new Medico(
                gerarIdUnico(),
                "Margarete Monte Garcia",
                "147.258.369-00\n",
                "(84)96123-4641",
                "margatemed@gmail.com",
                "Rua José Paulino, 184",
                "111-RN",
                "Dermatologista",
                u4
        );
        hospital.adicionarPessoa(m4);


    }

    public static void carregarpacientes (Hospital hospital){
        Paciente p1 = new Paciente(
                gerarIdUnico(),
                "José Luiz Feitosa",
                "529.982.247-25",
                "(84)93214-2314",
                "zezinho1232@gmail.com",
                "Rua dos Palmares, 213",
                11,
                "Dor nas Costas"
        );
        hospital.adicionarPessoa(p1);
        Paciente p2 = new Paciente(
                gerarIdUnico(),
                "Maria Luiza da Silva",
                "153.509.460-56",
                "(84)99823-2415",
                "mariasilv4@gmail.com",
                "Avenida das Dores, 180",
                32,
                "Dor no ventre, enxaqueca"
        );
        hospital.adicionarPessoa(p2);

        Paciente p3 = new Paciente(
                gerarIdUnico(),
                "Gael Oliveira",
                "746.971.314-01",
                "(84)99691-3411",
                "gaelsousaa@gmail.com",
                "Avenida Hamilton, 480",
                24,
                "Falta de ar"
        );
        hospital.adicionarPessoa(p3);


        Paciente p4 = new Paciente(
                gerarIdUnico(),
                "Terezinha Maria da Silva",
                "111.444.777-35",
                "(84)99175-2567",
                "terezamaria83@gmail.com",
                "Rua Hemetério Fernandes, 812 ",
                64,
                "Pressão baixa, tontura"
        );
        hospital.adicionarPessoa(p4);

        Paciente p5 = new Paciente(
                gerarIdUnico(),
                "Ana Sara Ribeira",
                "350.452.618-19",
                "(84)99677-7127",
                "mareeomarana@gmail.com",
                "Centro, 281 ",
                24,
                "Acidente, ferida aberta, hemorragia"
        );
        hospital.adicionarPessoa(p5);

        Paciente p6 = new Paciente(
                gerarIdUnico(),
                "Felipe Ribeira da Silva",
                "529.982.247-25",
                "(84)99117-7127",
                "felipejogadorri@gmail.com",
                "Centro,281",
                27,
                "Desorientação, braço quebrado"
        );
        hospital.adicionarPessoa(p6);

    }

    public static void usuariosTeste (List<UsuarioSistema> usuarios){
        usuarios.add(new UsuarioSistema("admin", "admin", NivelAcesso.ADMIN));
        usuarios.add(new UsuarioSistema("secretaria", "1234", NivelAcesso.SECRETARIA));
        usuarios.add(new UsuarioSistema("medico", "1234", NivelAcesso.MEDICO));
    }
}
