package org.plu.RESTSpringBoot.entities;

public class Pratilac {
    private String ko;
    private String koga;

    public Pratilac(String ko, String koga){
        this.ko=ko;
        this.koga= koga;
    }

    public String getKo() {
        return ko;
    }

    public void setKo(String ko) {
        this.ko = ko;
    }

    public String getKoga() {
        return koga;
    }

    public void setKoga(String koga) {
        this.koga = koga;
    }
}
