package br.com.hospital.utilitarios;

import java.util.Scanner;

public class Utilitarios {

    // -----------------------------
    //        GERAÇÃO DE ID
    // -----------------------------
    private static int contadorId = 1;

    public static int gerarIdIncremental() {
        return contadorId++;
    }

    public static String gerarIdUnico() {
        return String.valueOf(gerarIdIncremental());
    }

    // -----------------------------
    //        COMPARAÇÕES
    // -----------------------------
    public static boolean compararIdentificadores(String id1, String id2) {
        if (id1 == null || id2 == null) return false;
        return id1.equalsIgnoreCase(id2);
    }

    // -----------------------------
    //      VALIDAÇÃO DE TEXTO
    // -----------------------------
    public static boolean textoNaoVazio(String txt) {
        return txt != null && !txt.isBlank();
    }

    // -----------------------------
    //      VALIDAÇÃO DE EMAIL
    // -----------------------------
    public static boolean emailValido(String email) {
        if (email == null) return false;
        String tratado = email.trim();
        return tratado.contains("@") && tratado.contains(".");
    }

    // -----------------------------
    //        VALIDAÇÃO CRM
    // -----------------------------
    public static boolean crmValido(String crm) {
        if (crm == null) return false;
        String tratado = crm.trim();
        if (tratado.length() > 13) return false;

        // permite CRM com UF ou apenas número
        String regex = "^[1-9]\\d*-(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$"
                + "|^[1-9]\\d*$";
        return tratado.matches(regex);
    }

    // -----------------------------
    //     VALIDAÇÃO DE TELEFONE
    // -----------------------------
    public static boolean telefoneValido(String telefone) {
        if (telefone == null) return false;
        String digitos = telefone.replaceAll("\\D", "");
        return digitos.matches("\\d{8,13}");
    }

    // -----------------------------
    //       VALIDAÇÃO CPF
    // -----------------------------
    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;

        String num = cpf.replaceAll("\\D", "");
        if (num.length() != 11) return false;
        if (num.chars().distinct().count() == 1) return false;

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) soma += Character.getNumericValue(num.charAt(i)) * (10 - i);
            int dig1 = 11 - (soma % 11);
            if (dig1 >= 10) dig1 = 0;
            if (dig1 != Character.getNumericValue(num.charAt(9))) return false;

            soma = 0;
            for (int i = 0; i < 10; i++) soma += Character.getNumericValue(num.charAt(i)) * (11 - i);
            int dig2 = 11 - (soma % 11);
            if (dig2 >= 10) dig2 = 0;

            return dig2 == Character.getNumericValue(num.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

    // -----------------------------
    //   VALIDAÇÃO DATA E HORA
    // -----------------------------
    public static boolean dataHoraValida(String dataHora) {
        if (dataHora == null || dataHora.length() != 16) return false;
        if (dataHora.charAt(2) != '/' || dataHora.charAt(5) != '/' || dataHora.charAt(10) != ' '
                || dataHora.charAt(13) != ':') return false;

        try {
            int dia = Integer.parseInt(dataHora.substring(0, 2));
            int mes = Integer.parseInt(dataHora.substring(3, 5));
            int ano = Integer.parseInt(dataHora.substring(6, 10));
            int hora = Integer.parseInt(dataHora.substring(11, 13));
            int minuto = Integer.parseInt(dataHora.substring(14, 16));

            java.time.LocalDateTime.of(ano, mes, dia, hora, minuto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dataNoFuturo(String dataHora) {
        if (!dataHoraValida(dataHora)) return false;
        try {
            int dia = Integer.parseInt(dataHora.substring(0, 2));
            int mes = Integer.parseInt(dataHora.substring(3, 5));
            int ano = Integer.parseInt(dataHora.substring(6, 10));
            int hora = Integer.parseInt(dataHora.substring(11, 13));
            int minuto = Integer.parseInt(dataHora.substring(14, 16));

            java.time.LocalDateTime dataConsulta =
                    java.time.LocalDateTime.of(ano, mes, dia, hora, minuto);

            return dataConsulta.isAfter(java.time.LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }

    // -----------------------------
    //     NORMALIZAÇÃO DE TEXTO
    // -----------------------------
    public static String normalizarTexto(String texto) {
        if (texto == null) return null;
        String semAcento = java.text.Normalizer
                .normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return semAcento.replaceAll("[^a-zA-Z0-9 ]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public static String capitalizarNome(String nome) {
        if (nome == null) return null;
        String[] partes = nome.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String p : partes) {
            if (p.length() > 0) {
                sb.append(Character.toUpperCase(p.charAt(0)))
                        .append(p.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    // -----------------------------
    //         PRINTS
    // -----------------------------
    public static void Print(String string) {
        System.out.print(string);
    }

    public static void Println(String string) {
        System.out.println(string);
    }

    public static void Printf(String string, Object... args) {
        System.out.printf(string, args);
    }

    // -----------------------------
    //     MENUS DO SISTEMA
    // -----------------------------

    // ADMIN — pode tudo
    public static void exibirMenuPrincipalAdmin() {
        Printf("""
                
                ---- MENU PRINCIPAL (ADMIN) ----
                
                1. Médico
                2. Paciente
                3. Consulta
                4. Busca Geral
                5. Administrar Funcionários
                0. Logout
                
                Escolha uma opção:\t""");
    }

    // SECRETARIA — pacientes + consultas
    public static void exibirMenuPrincipalFuncionario() {
        Printf("""
                
                ---- MENU PRINCIPAL (SECRETARIA) ----
                
                1. Paciente
                2. Consulta
                0. Logout
                
                Escolha uma opção:\t""");
    }

    // MÉDICO — visualizar consultas
    public static void exibirMenuPrincipalMedico() {
        Printf("""
                
                ---- MENU PRINCIPAL (MÉDICO) ----
                
                1. Minhas consultas
                2. Solicitar retorno
                0. Logout
                
                Escolha uma opção:\t""");
    }

    public static void exibirMenuMedico() {
        Print("""
                
                --- MENU MÉDICO ---
                
                1. Cadastrar Médico
                2. Editar Médico
                3. Listar Médicos
                4. Remover Médico
                5. Buscar Médico
                0. Voltar
                
                Escolha uma opção:\t""");
    }

    public static void exibirMenuPaciente() {
        Printf("""
                
                --- MENU PACIENTE ---
                
                1. Cadastrar Paciente
                2. Editar Paciente
                3. Listar Pacientes
                4. Remover Paciente
                5. Buscar Paciente
                0. Voltar
                
                Escolha uma opção:\t""");
    }

    public static void exibirMenuConsulta() {
        Printf("""
                
                --- MENU CONSULTA ---
                
                1. Agendar Consulta
                2. Cancelar Consulta
                3. Listar Consultas
                4. Buscar Consulta por Paciente
                5. Solicitar retorno
                0. Voltar
                
                Escolha uma opção:\t""");
    }

    public static void limparTela() {
        for (int i = 0; i < 60; i++) {
            System.out.println();
        }
    }
    public static void pausar(Scanner sc) {
        Print("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    public static void exibirMenuAdministraFuncionario() {
        System.out.printf("""
                
                --- MENU ADMIN FUNCIONÁRIOS ---
                
                1. Cadastrar Funcionário
                2. Listar Funcionários
                3. Buscar Funcionário
                4. Editar Funcionário
                5. Remover Funcionário
                0. Voltar ao menu anterior
                
                Escolha uma opção: """);
    }

}

