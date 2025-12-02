package br.com.hospital.utilitarios;

import java.util.List;

public class Utilitarios {

    // Gera ID's
    private static int contadorId = 1;

    public static int gerarIdIncremental() {
        return contadorId++;
    }

    // Evita ID's repetidos
    public static String gerarIdUnico() {
        return "ID-" + gerarIdIncremental();
    }

    // Compara ID's
    public static boolean compararIdentificadores(String id1, String id2) {
        if (id1 == null || id2 == null) return false;
        return id1.equalsIgnoreCase(id2);
    }

    // Verifica texto vazio
    public static boolean textoNaoVazio(String txt) {
        return txt != null && !txt.isBlank();
    }

    // Verifica lista vazia
    public static boolean listaVazia(List<?> lista) {
        return lista == null || lista.isEmpty();
    }

    // Valida e-mail
    public static boolean emailValido(String email) {
        if (email == null){
            return false;
        }
        email = email.trim();
        return email.contains("@") && email.contains(".");
    }

    // Valida o CRM
    public static boolean crmValido(String crm) {
        if (crm == null){
            return false;
        }
        crm = crm.trim();
        return crm.length() >= 4 && crm.length() <= 10;
    }

    // Valida telefone
    public static boolean telefoneValido(String telefone) {
        if (telefone == null){
            return false;
        }
        String digitos = telefone.replaceAll("\\D", "");
        return digitos.matches("\\d{8,13}");
    }

    // Valida o CPF
    public static boolean cpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        String num = cpf.replaceAll("\\D", "");

        if (num.length() != 11) {
            return false;
        }

        // rejeita sequências como 00000000000
        if (num.chars().distinct().count() == 1){
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(num.charAt(i)) * (10 - i);
            }

            int dig1 = 11 - (soma % 11);

            if (dig1 >= 10){
                dig1 = 0;
            }

            if (dig1 != Character.getNumericValue(num.charAt(9))){
                return false;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(num.charAt(i)) * (11 - i);
            }

            int dig2 = 11 - (soma % 11);

            if (dig2 >= 10){
                dig2 = 0;
            }

            return dig2 == Character.getNumericValue(num.charAt(10));

        } catch (Exception e) {
            return false;
        }
    }
    //Determina tamanho mínimo para Strings
    public static boolean tamanhoMinimo(String txt, int min){
        if(txt == null) {
            return false;
        }
        return txt.trim().length() >= min;
    }
    //valida a especialidade
    public static boolean especialidadeValida(String esp){
        if(esp == null){
            return false;
        }
        esp = esp.trim();
        if(esp.length() < 3 ){
            return false;
        }
        boolean temLetra = esp.matches(".*[a-zA-Z].*");
        if(temLetra){
            return false;
        }
        return true;
    }
    public static boolean dataHoraValida(String dataHora) {
    if (dataHora == null) return false;
    if (dataHora.length() != 16) return false;

    // partes fixas da data
    if (dataHora.charAt(2) != '/' ||
        dataHora.charAt(5) != '/' ||
        dataHora.charAt(10) != ' ' ||
        dataHora.charAt(13) != ':') {
        return false;
    }

    try {
        int dia = Integer.parseInt(dataHora.substring(0, 2));
        int mes = Integer.parseInt(dataHora.substring(3, 5));
        int ano = Integer.parseInt(dataHora.substring(6, 10));
        int hora = Integer.parseInt(dataHora.substring(11, 13));
        int minuto = Integer.parseInt(dataHora.substring(14, 16));

        // valida os intervalos de mes, dia, hora e minuto
        if (mes < 1 || mes > 12) return false;
        if (dia < 1 || dia > 31) return false;
        if (hora < 0 || hora > 23) return false;
        if (minuto < 0 || minuto > 59) return false;

        // valida se a data EXISTE de verdade
        java.time.LocalDateTime.of(ano, mes, dia, hora, minuto);

        return true;
    } catch (Exception e) {
        return false;
    }
}

    public static boolean dataNoFuturo(String dataHora){
        if(!dataHoraValida(dataHora)){
            return false;
        }
        try{
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
    public static String normalizarTexto(String texto) {
    if (texto == null) return null;

    // Remover acentuação (NFD = decompor acentos)
    String semAcento = java.text.Normalizer
            .normalize(texto, java.text.Normalizer.Form.NFD)
            .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

    // Deixa apenas letras, números e espaço
    semAcento = semAcento.replaceAll("[^a-zA-Z0-9 ]", "");

    // Remove espaços duplicados
    semAcento = semAcento.replaceAll("\\s+", " ").trim();

    return semAcento;
    }
    public static String capitalizarNome(String nome) {
    if (nome == null) return null;

    nome = nome.trim().toLowerCase();

    String[] partes = nome.split("\\s+");
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
        // imprime sem quebra de linha
    public static void print(String texto) {
        System.out.print(texto);
    }

    // imprime com quebra de linha
    public static void println(String texto) {
        System.out.println(texto);
    }
    }

