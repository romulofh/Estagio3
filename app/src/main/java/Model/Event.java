package Model;

import java.io.Serializable;

/**
 * Created by DevMAchine on 20/11/2016.
 */
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    String nome, data, horario;
    int estabelecimento_id, id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getEstabelecimento_id() {
        return estabelecimento_id;
    }

    public void setEstabelecimento_id(int estabelecimento_id) {
        this.estabelecimento_id = estabelecimento_id;
    }
}
