package Model;

/**
 * Created by Desenvolvedor on 20/11/2016.
 */
import java.io.Serializable;

/**
 * Created by DevMAchine on 08/11/2016.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    String email, password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

