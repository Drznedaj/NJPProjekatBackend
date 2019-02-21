package org.plu.RESTSpringBoot.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "korisnici")
public class Korisnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "aktiviran")
    private boolean aktiviran;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Slika profilna;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Slika> slikeKorisnika;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Korisnik> kojePrati;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Korisnik> kojiGaPrate;

    public Slika getProfilna() {
        return profilna;
    }

    public void setProfilna(Slika profilna) {
        this.profilna = profilna;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Slika> getSlikeKorisnika() {
        return slikeKorisnika;
    }

    public void setSlikeKorisnika(List<Slika> slikeKorisnika) {
        this.slikeKorisnika = slikeKorisnika;
    }

    public List<Korisnik> getKojePrati() {
        return kojePrati;
    }

    public void setKojePrati(List<Korisnik> kojePrati) {
        this.kojePrati = kojePrati;
    }
    @JsonBackReference
    public List<Korisnik> getKojiGaPrate() {
        return kojiGaPrate;
    }

    public void setKojiGaPrate(List<Korisnik> kojiGaPrate) {
        this.kojiGaPrate = kojiGaPrate;
    }

    public boolean isAktiviran() {
        return aktiviran;
    }

    public void setAktiviran(boolean aktiviran) {
        this.aktiviran = aktiviran;
    }

    public int getId() {
        return id;
    }
}
