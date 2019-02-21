package org.plu.RESTSpringBoot.rest.controllers;

import org.apache.commons.io.FileUtils;
import org.plu.RESTSpringBoot.entities.Komentar;
import org.plu.RESTSpringBoot.entities.Korisnik;
import org.plu.RESTSpringBoot.entities.Slika;
import org.plu.RESTSpringBoot.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/korisnik")
@CrossOrigin
public class KorisnikController {


    private KorisnikRepository korisnikRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public KorisnikController(KorisnikRepository korisnikRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.korisnikRepository = korisnikRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/slike/stavi/{usname}")
    public String sacuvajSliku(@PathVariable String usname, @RequestParam("file") MultipartFile img){
        System.out.println("Pokusavam da sacuvam sliku "+img.getOriginalFilename());
        Korisnik kor = this.korisnikRepository.getByUsername(usname);
        if(!img.isEmpty()){
            try {
                byte[] bytes = img.getBytes();
                String path = ""+kor.getUsername()+(kor.getSlikeKorisnika().size()+1)+".png";
                String ime = ""+kor.getUsername()+(kor.getSlikeKorisnika().size()+1);
                File out = new File(path);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(out));
                bos.write(bytes);
                bos.close();
                Slika slika = new Slika();
                slika.setPath(path);
                slika.setIme(ime);
                slika.setBrojLajkova(0);
                kor.getSlikeKorisnika().add(slika);
                this.korisnikRepository.save(kor);
                return "uspelo cuvanje";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "Nije uspelo cuvanje";
    }

    @PostMapping("/promeniPass/{unam}")
    public void promeniPass(@PathVariable String unam, @RequestBody Korisnik u){
        System.out.println("menjam pass");
        Korisnik k = this.korisnikRepository.getByUsername(unam);
        k.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        this.korisnikRepository.save(k);
    }

    @PostMapping("/promeniEma/{unam}")
    public void promeniEma(@PathVariable String unam, @RequestBody Korisnik u){
        System.out.println("menjam email");
        Korisnik k = this.korisnikRepository.getByUsername(unam);
        k.setEmail(u.getEmail());
        this.korisnikRepository.save(k);
    }

    @PostMapping("/slike/profilna/{usname}")
    public String sacuvajProfilnuSliku(@PathVariable String usname, @RequestParam("file") MultipartFile img){
        System.out.println("Pokusavam da sacuvam profilnu sliku "+img.getOriginalFilename());
        Korisnik kor = this.korisnikRepository.getByUsername(usname);
        if(!img.isEmpty()){
            try {
                byte[] bytes = img.getBytes();
                String path = ""+kor.getUsername()+"profilna"+".png";
                String ime = ""+kor.getUsername()+"profilna";
                File out = new File(path);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(out));
                bos.write(bytes);
                bos.close();
                Slika slika = new Slika();
                slika.setPath(path);
                slika.setIme(ime);
                slika.setBrojLajkova(0);
                kor.setProfilna(slika);
                this.korisnikRepository.save(kor);
                return "uspelo cuvanje";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "Nije uspelo cuvanje";
    }

    @GetMapping("/slike/get/{path}")
    public @ResponseBody byte[] getSlika(@PathVariable String path){
        File f = new File(path);
        try {
            BufferedImage bimg = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bimg,"png",baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] b = null;
        return b;
    }

    @GetMapping
    public List<Korisnik> getAllKorisnik() {
        return this.korisnikRepository.findAll();
    }

    @GetMapping("/{username}")
    public Korisnik getByFirstName(@PathVariable String username) {
        return this.korisnikRepository.getByUsername(username);
    }

    @GetMapping("/nadjisve/{partial}")
    public List<Korisnik> getAllContaining(@PathVariable String partial) {
        return this.korisnikRepository.getAllByUsernameContains(partial);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/zaprati")
    public void zaprati(@RequestParam("ko") String koPrati, @RequestParam("koga") String kogaPrati){
        System.out.println("zapracuje "+ koPrati+ " prati "+ kogaPrati);
        Korisnik ko = this.korisnikRepository.getByUsername(koPrati);
        Korisnik koga = this.korisnikRepository.getByUsername(kogaPrati);
        ko.getKojePrati().add(koga);
        koga.getKojiGaPrate().add(ko);
        this.korisnikRepository.save(ko);
        this.korisnikRepository.save(koga);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/kojeprati/{username}")
    public List<Korisnik> sveKojePrati(@PathVariable String username){
        return this.korisnikRepository.getByUsername(username).getKojePrati();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/kojigaprate/{username}")
    public List<Korisnik> sveKojiGaPratw(@PathVariable String username){
        return this.korisnikRepository.getByUsername(username).getKojiGaPrate();
    }

    @PostMapping("komentar/{user}/{slik}")
    public void komentarisi(@PathVariable String user,@PathVariable String slik, @RequestBody Komentar kom){
        Korisnik ko = this.korisnikRepository.getByUsername(user);
        for (Slika s: ko.getSlikeKorisnika()) {
            if(s.getPath().equals(slik)){
                s.getKomentars().add(kom);
                this.korisnikRepository.save(ko);
                return;
            }
        }
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Korisnik user) {
        sendActivationEmail(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        korisnikRepository.save(user);
    }
    @PutMapping("/{korisnikId}")
    public Korisnik setLoggedIn(@PathVariable Integer korisnikId, @RequestBody Korisnik korReq) {
        return korisnikRepository.findById(korisnikId).map(korisnik -> {
            //korisnik.setLogged(true);

            return this.korisnikRepository.save(korisnik);
        }).orElseThrow(() -> new ResourceNotFoundException("StudID " + korisnikId + " not found"));
    }

    @GetMapping("/activate/broj/{username}")
    public Korisnik proveraKorisnika(@PathVariable String username){
        return this.korisnikRepository.getByUsername(username);
    }

    @GetMapping("/activate/{id}")
    public Korisnik aktivirajKorisnika(@PathVariable Integer id){
        return korisnikRepository.findById(id).map(korisnik -> {
            korisnik.setAktiviran(true);
            return this.korisnikRepository.save(korisnik);
        }).orElseThrow(() -> new ResourceNotFoundException("ID: "+id+" nije pronadjen"));
    }

    public void sendActivationEmail(Korisnik user) {

        final String username = "test1231234666111@gmail.com";
        final String password = "123aaa123";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            try {
                message.setSubject("Aktivacioni mail");
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            // Now set the actual message
            message.setText("Yoyo " + user.getUsername() + ", klikni OVDE: http://localhost:8080/korisnik/activate/" + (user.getId()+1));

            Transport.send(message);

            System.out.println("Mejl poslat :[");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
