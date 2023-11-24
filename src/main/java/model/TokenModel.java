package model;
import dao.UsuarioDAO;

public class TokenModel {
    private Integer id;
    private Integer usuario_id;
    private UsuarioModel usuario;
    private String token;
    private Integer status;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsuarioId(Integer usuario_id) {
        this.usuario_id = usuario_id;
        this.usuario = new UsuarioDAO().buscarPorId(usuario_id);
    }
    private static void setUsuario(Integer usuario_id) {
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getId() {
        return id;
    }
    public Integer getUsuarioId() {
        return usuario_id;
    }
    public UsuarioModel getUsuario() {
        return usuario;
    }
    public String getToken() {
        return token;
    }
    public Integer getStatus() {
        return status;
    }
}
