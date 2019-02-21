package org.plu.RESTSpringBoot.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "slike")

public class Slika implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "path")
    private String path;

    @Column(name = "ime")
    private String ime;

    @Column(name = "lajkovi")
    private int brojLajkova;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Komentar> komentars;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getBrojLajkova() {
        return brojLajkova;
    }

    public void setBrojLajkova(int brojLajkova) {
        this.brojLajkova = brojLajkova;
    }

    public List<Komentar> getKomentars() {
        return komentars;
    }

    public void setKomentars(List<Komentar> komentars) {
        this.komentars = komentars;
    }
}
