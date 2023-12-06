package model;

import model.UsuarioModel;
public class FileModel {
    private int id;
    private String caminho;
    private String nome;
    private UsuarioModel usuario;
    private int Usuario_id;
    private int aprovador_id;
    private UsuarioModel aprovador;
    private int status;

    public FileModel() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getCaminho() {
        return this.caminho;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public UsuarioModel getUsuario() {
        return this.usuario;
    }

    public void setUsuario_id(int Usuario_id) {
        this.Usuario_id = Usuario_id;
    }

    public int getUsuario_id() {
        return this.Usuario_id;
    }

    public void setAprovador_id(int aprovador_id) {
        this.aprovador_id = aprovador_id;
    }

    public int getAprovador_id() {
        return this.aprovador_id;
    }

public void setAprovador(UsuarioModel aprovador) {
        this.aprovador = aprovador;
    }

    public UsuarioModel getAprovador() {
        return this.aprovador;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "FileModel [id=" + id + ", caminho=" + caminho + ", nome=" + nome + ", usuario=" + usuario + ", Usuario_id=" + Usuario_id + ", aprovador_id=" + aprovador_id + ", aprovador=" + aprovador + ", status=" + status + "]";
    }
}
