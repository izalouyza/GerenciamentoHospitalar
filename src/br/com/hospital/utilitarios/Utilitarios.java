package br.com.hospital.utilitarios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Utilitarios {

    private static final DateTimeFormatter FORMATADOR =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);

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
    
    // Verifica se a data e a hora são válidas
    public static boolean dataHoraValida(String dataHora) {

        if (dataHora == null || dataHora.isBlank()) return false;

        // Formato esperado: 16 caracteres → "dd/MM/yyyy HH:mm"
        if (dataHora.length() != 16) return false;

        // Checagem manual do padrão básico
        if (dataHora.charAt(2) != '/' || dataHora.charAt(5) != '/' ||
                dataHora.charAt(10) != ' ' || dataHora.charAt(13) != ':') {
            return false;
        }

        // Se chegou até aqui, tentamos parsear SEM try/catch
        LocalDateTime dt = LocalDateTime.parse(dataHora, FORMATADOR);

        // Se não explodiu, é válido
        return dt != null;
    }

}
