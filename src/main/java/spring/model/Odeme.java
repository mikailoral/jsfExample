package spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dozac on 15/04/2015.
 */
@Entity
@Table(name="odeme")
public class Odeme {

	@Id
	@GeneratedValue
    private int id;
    private int user;
    private int kredi;

    public Odeme(){
    	
    }

	
    @Column(name="ID", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="user", nullable = false)
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Column(name="kredi", nullable = true)
    public int getKredi() {
        return kredi;
    }

    public void setKredi(int kredi) {
        this.kredi = kredi;
    }


	@Override
    public String toString() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("id : ").append(getId());
        strBuff.append(", user : ").append(getUser());
        strBuff.append(", kredi : ").append(getKredi());
        return strBuff.toString();
    }

}
