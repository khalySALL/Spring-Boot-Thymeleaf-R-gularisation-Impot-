package com.isi.impotregu;

import com.isi.impotregu.entities.Declarant;
import com.isi.impotregu.repository.DeclarantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImpotReguApplication implements CommandLineRunner {
    @Autowired
    private DeclarantRepository declarantRepository;

    public static void main(String[] args) {

        SpringApplication.run(ImpotReguApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*declarantRepository.save(new Declarant(null,"ammmj","hdydytyh","ga@ga.com","777777777"));
        declarantRepository.save(new Declarant(null,"gammmnn","gydtdyh","gam@gam.com","888888888"));
        declarantRepository.save(new Declarant(null,"ihfhji","jjhh","ii@ii.com","666666666"));*/



        /*Declarant declarant = new Declarant();
        declarant.setId(null);
        declarant.setRaisonSociale("gga");
        declarant.setAdresse("km");
        declarant.setEmail("isi@isi.com");
        declarant.setTelephone("667778899");*/

        //Declarant declarant2 = new Declarant(null,"am","hh","ga@ga.com","777777777");

        /*Declarant declarant3 = Declarant.builder()
                .raisonSociale("tt")
                .adresse("hs")
                .email("thsb@hh.com")
                .telephone("hsh")
                .build();*/

    }
}
