package promoz.com.br.promoz.model;

import java.io.Serializable;

/**
 * Created by vallux on 25/01/17.
 */

public class User implements Serializable {

    private Integer _id;
    private String nome;
    private String password;
    private String email;
    private String cpf;
    private byte img[];
    private static String chaveID = "_ID";
    String abreTag = "<";
    String fechaTag = ">";

    public User() {
    }

    public User(Integer _id, String nome, String password, String email, byte[] img) {
        this._id = _id;
        this.nome = nome;
        this.email = email;
        this.img = img;
    }

    public User(Integer _id, String nome, String password, String email, String cpf, byte[] img) {
        this._id = _id;
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.cpf = cpf;
        this.img = img;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public static String getChave_ID(){
        return chaveID;
    }

    @Override
    public String toString() {
        return this.nome + " " + abreTag + this.email + fechaTag;
    }
}