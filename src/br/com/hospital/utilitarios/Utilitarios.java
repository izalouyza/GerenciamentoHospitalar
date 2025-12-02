package br.com.hospital.utilitarios;

import java.util.List;

public class Utilitarios {

    // Gerador incremental de IDs
    private static int contadorId = 1;

    public static int gerarIdIncremental() {
        int idAtual = contadorId;
        contadorId = contadorId + 1;
        return idAtual;
    }

    public static String gerarIdUnico() {
        return "ID-" + gerarIdIncremental();
    }

    // Comparar IDs
    public static boolean compararIdentificadores(String id1, String id2) {
        if (id1 == null) {
            return false;
        }
        if (id2 == null) {
            return false;
        }
        return id1.equalsIgnoreCase(id2);
    }

    // texto não vazio
    public static boolean textoNaoVazio(String txt) {
        if (txt == null) {
            return false;
        }
        return !txt.isBlank();
    }

    // lista vazia
    public static boolean listaVazia(List<?> lista) {
        if (lista == null) {
            return true;
        }
        return lista.isEmpty();
    }

    // email válido (simples)
    public static boolean emailValido(String email) {
        if (email == null) {
            return false;
        }

        String tratado = email.trim();

        if (!tratado.contains("@")) {
            return false;
        }
        if (!tratado.contains(".")) {
            return false;
        }

        return true;
    }

    // CRM válido
    public static boolean crmValido(String crm) {
        if (crm == null) {
            return false;
        }

        String tratado = crm.trim();
        int tamanho = tratado.length();

        if (tamanho < 4) {
            return false;
        }
        if (tamanho > 10) {
            return false;
        }

        return true;
    }

    // telefone válido
    public static boolean telefoneValido(String telefone) {
        if (telefone == null) {
            return false;
        }

        String digitos = telefone.replaceAll("\\D", "");

        return digitos.matches("\\d{8,13}");
    }

    // CPF válido (completo)
    public static boolean cpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        String num = cpf.replaceAll("\\D", "");

        if (num.length() != 11) {
            return false;
        }

        if (num.chars().distinct().count() == 1) {
            return false;
        }

        try {
            int soma = 0;

            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(num.charAt(i)) * (10 - i);
            }

            int dig1 = 11 - (soma % 11);

            if (dig1 >= 10) {
                dig1 = 0;
            }

            if (dig1 != Character.getNumericValue(num.charAt(9))) {
                return false;
            }

            soma = 0;

            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(num.charAt(i)) * (11 - i);
            }

            int dig2 = 11 - (soma % 11);

            if (dig2 >= 10) {
                dig2 = 0;
            }

            return dig2 == Character.getNumericValue(num.charAt(10));

        } catch (Exception e) {
            return false;
        }
    }

    // tamanho mínimo
    public static boolean tamanhoMinimo(String txt, int min) {
        if (txt == null) {
            return false;
        }

        String trat = txt.trim();

        return trat.length() >= min;
    }

    // especialidade médica válida
    public static boolean especialidadeValida(String esp) {
        if (esp == null) {
            return false;
        }

        String trat = esp.trim();

        if (trat.length() < 3) {
            return false;
        }

        boolean contemLetra = trat.matches(".*[A-Za-z].*");

        if (!contemLetra) {
            return false;
        }

        return true;
    }

    public static boolean dataHoraValida(String dataHora) {
        if (dataHora == null) {
            return false;
        }

        if (dataHora.length() != 16) {
            return false;
        }

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

            if (mes < 1 || mes > 12) {
                return false;
            }
            if (dia < 1 || dia > 31) {
                return false;
            }
            if (hora < 0 || hora > 23) {
                return false;
            }
            if (minuto < 0 || minuto > 59) {
                return false;
            }

            java.time.LocalDateTime.of(ano, mes, dia, hora, minuto);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dataNoFuturo(String dataHora) {
        if (!dataHoraValida(dataHora)) {
            return false;
        }

        try {
            int dia = Integer.parseInt(dataHora.substring(0, 2));
            int mes = Integer.parseInt(dataHora.substring(3, 5));
            int ano = Integer.parseInt(dataHora.substring(6, 10));
            int hora = Integer.parseInt(dataHora.substring(11, 13));
            int minuto = Integer.parseInt(dataHora.substring(14, 16));

            java.time.LocalDateTime dataConsulta =
                    java.time.LocalDateTime.of(ano, mes, dia, hora, minuto);

            java.time.LocalDateTime agora = java.time.LocalDateTime.now();

            return dataConsulta.isAfter(agora);

        } catch (Exception e) {
            return false;
        }
    }

    public static String normalizarTexto(String texto) {
        if (texto == null) {
            return null;
        }

        String semAcento = java.text.Normalizer
                .normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        semAcento = semAcento.replaceAll("[^a-zA-Z0-9 ]", "");
        semAcento = semAcento.replaceAll("\\s+", " ").trim();

        return semAcento;
    }

    public static String capitalizarNome(String nome) {
        if (nome == null) {
            return null;
        }

        String tratado = nome.trim().toLowerCase();
        String[] partes = tratado.split("\\s+");

        StringBuilder sb = new StringBuilder();

        for (String p : partes) {
            if (p.length() > 0) {
                char primeira = Character.toUpperCase(p.charAt(0));
                String resto = p.substring(1);

                sb.append(primeira);
                sb.append(resto);
                sb.append(" ");
            }
        }

        return sb.toString().trim();
    }

    public static void print(String texto) {
        System.out.print(texto);
    }

    public static void println(String texto) {
        System.out.println(texto);
    }
}
