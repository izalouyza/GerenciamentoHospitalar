package br.com.hospital.sistema;

public class UsuarioSistema {

    private String usuario;     // login
    private String senha;       // senha
    private NivelAcesso nivel;  // nível de acesso: ADMIN, MEDICO, SECRETARIA

    public UsuarioSistema(String usuario, String senha, NivelAcesso nivel) {
        this.usuario = usuario;
        this.senha = senha;
        this.nivel = nivel;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public NivelAcesso getNivel() {
        return nivel;
    }

    // Se no futuro quiser alterar senha:
    public void setSenha(String novaSenha) {
        this.senha = novaSenha;
    }
}