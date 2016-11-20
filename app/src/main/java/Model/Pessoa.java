package Model;

/**
 * Created by Desenvolvedor on 20/11/2016.
 */
import java.io.Serializable;


public class Pessoa  implements Serializable {

    private static final long serialVersionUID = 1L;

    int id, user_id;
    User user;
    String nome,sobrenome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String surname) {
        this.sobrenome = sobrenome;
    }


}

