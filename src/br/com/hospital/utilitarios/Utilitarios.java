package br.com.hospital.utilitarios;

import java.util.List;

public class Utilitarios {

    // Gerador incremental de IDs
    private static int contadorId = 1;

    public static int gerarIdIncremental() {
        int idAtual = contadorId; // guarda o valor atual
        contadorId = contadorId + 1; // incrementa para próximo ID
        return idAtual;
    }

    public static String gerarIdUnico() {
        return "ID-" + gerarIdIncremental(); // prefixo ID- + número incremental
    }

    // Comparar IDs ignorando maiúsculas/minúsculas
    public static boolean compararIdentificadores(String id1, String id2) {
        if (id1 == null || id2 == null) return false;
        return id1.equalsIgnoreCase(id2);
    }

    // Verifica se texto não é nulo ou vazio
    public static boolean textoNaoVazio(String txt) {
        return txt != null && !txt.isBlank();
    }

    // Verifica se lista é nula ou vazia
    public static boolean listaVazia(List<?> lista) {
        return lista == null || lista.isEmpty();
    }

    // Validação simples de email
    public static boolean emailValido(String email) {
        if (email == null) return false;
        String tratado = email.trim();
        return tratado.contains("@") && tratado.contains(".");
    }

    // Validação de CRM
    public static boolean crmValido(String crm) {
        if (crm == null) return false;
        String tratado = crm.trim();
        if (tratado.length() > 13) return false;

        String regex = "^[1-9]\\d*-(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$"
                + "|^[1-9]\\d*$";
        return tratado.matches(regex);
    }

    // Telefone válido (8 a 13 dígitos)
    public static boolean telefoneValido(String telefone) {
        if (telefone == null) return false;
        String digitos = telefone.replaceAll("\\D", "");
        return digitos.matches("\\d{8,13}");
    }

    // CPF válido
    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;
        String num = cpf.replaceAll("\\D", "");
        if (num.length() != 11) return false;
        if (num.chars().distinct().count() == 1) return false; // todos iguais

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

    // Verifica tamanho mínimo
    public static boolean tamanhoMinimo(String txt, int min) {
        return txt != null && txt.trim().length() >= min;
    }

    // Especialidade médica válida (mínimo 3 caracteres e pelo menos uma letra)
    public static boolean especialidadeValida(String esp) {
        if (esp == null) return false;
        String trat = esp.trim();
        return trat.length() >= 3 && trat.matches(".*[A-Za-z].*");
    }

    // Verifica formato de data/hora dd/MM/yyyy HH:mm
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

    // Verifica se a data/hora está no futuro
    public static boolean dataNoFuturo(String dataHora) {
        if (!dataHoraValida(dataHora)) return false;

        try {
            int dia = Integer.parseInt(dataHora.substring(0, 2));
            int mes = Integer.parseInt(dataHora.substring(3, 5));
            int ano = Integer.parseInt(dataHora.substring(6, 10));
            int hora = Integer.parseInt(dataHora.substring(11, 13));
            int minuto = Integer.parseInt(dataHora.substring(14, 16));

            java.time.LocalDateTime dataConsulta = java.time.LocalDateTime.of(ano, mes, dia, hora, minuto);
            return dataConsulta.isAfter(java.time.LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }

    // Normaliza texto removendo acentos e caracteres especiais
    public static String normalizarTexto(String texto) {
        if (texto == null) return null;
        String semAcento = java.text.Normalizer
                .normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        semAcento = semAcento.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", " ").trim();
        return semAcento;
    }

    // Capitaliza nomes (primeira letra maiúscula)
    public static String capitalizarNome(String nome) {
        if (nome == null) return null;
        String[] partes = nome.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : partes) {
            if (p.length() > 0) {
                sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1)).append(" ");
            }
        }
        return sb.toString().trim();
    }

    // Métodos de print simplificados
    public static void Print(String string) {
        System.out.print(string);
    }

    public static void Println(String string) {
        System.out.println(string);
    }

    public static void Printf(String string, Object... args) {
        System.out.printf(string, args);
    }
}
