package org.plu.RESTSpringBoot.entities;

import javax.persistence.*;

@Entity
@Table(name = "komentari")
public class Komentar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user")
    private String user;

    @Column(name = "tekst")
    private String tekstKomentara;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }
}
