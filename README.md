# Spring-Boot-Thymeleaf-R-gularisation-Impot-
Systeme de regularisation des impôts pour les contribuables

# Captures d´ecran Application

  #  Home 
    
![Image 22-08-2023 à 14 29](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/f9ad1d75-fd72-4bca-ac82-fc38ebe37e1b)

  # Formulaire d´ajout des declarants
  
![Image 22-08-2023 à 14 21](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/0bcfea36-6ef8-4d15-a38b-7274f7ce1abb)

  # Liste declarants
  
![Image 22-08-2023 à 14 22](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/a47846ae-df49-4be3-a269-2ff63fc79ee7)

  # Formulaire d´ajout declarations

![Image 22-08-2023 à 14 23](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/88dad79c-bedd-498e-b5c4-25421ec0af29)

  # Liste declarations
  
![Image 22-08-2023 à 14 24](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/0988d710-2d4c-47d3-8b9b-ce516cc9a26c)


  # Formulaire de paiement
  
![Image 22-08-2023 à 14 23 (1)](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/bf7c3d21-d04b-42a8-99ee-8ff75b91e351)


  # Liste des paiements

![Image 22-08-2023 à 14 24](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/5573c3fd-db50-4f15-8f6b-10c07d1824fc)

# architecture
![Image 22-08-2023 à 15 09](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/d26b2890-bd69-449e-9426-403993340d7c)

# application.properties

![Image 22-08-2023 à 15 09](https://github.com/khalySALL/Spring-Boot-Thymeleaf-R-gularisation-Impot-/assets/95873034/9610d880-6bcb-470f-919a-3a193cb4cb71)




# CODE DE APPLICATION

#  - Entities
    
#        _Declarant
    
package com.isi.impotregu.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Declarant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min=4, max=20)
    private String raisonSociale;
    @NotEmpty
    @Size(min=4, max=20)
    private String adresse;
    private String email;

    private String telephone;

}


#        _Declaration

package com.isi.impotregu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Declaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Date dateDeclaration;
    private Double montantDeclaration;
    @ManyToOne
    @JoinColumn(name ="declarant_id")
    private Declarant declarant;

}



#        _Paiement

package com.isi.impotregu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date datePaiement;
    private Double montantPaiement;
    @ManyToOne
    @JoinColumn(name="declaration_id")
    private Declaration declaration;
}


- Repository

#        _DeclarantRepository

package com.isi.impotregu.repository;

import com.isi.impotregu.entities.Declarant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeclarantRepository extends JpaRepository<Declarant,Long> {
    Page<Declarant> findByAdresseContains(String keyword, Pageable pageable);
    @Query("select d from Declarant d where d.adresse like :x")
    Page<Declarant> Search(@Param("x") String keyword, Pageable pageable);
}



#      _DeclarationRepository

package com.isi.impotregu.repository;

import com.isi.impotregu.entities.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
}


    _PaiementRepository

package com.isi.impotregu.repository;

import com.isi.impotregu.entities.Paiement;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PaiementRepository extends JpaRepository<Paiement, Long> {

}


WEB


#        _DeclarantController

package com.isi.impotregu.web;

import com.isi.impotregu.entities.Declarant;
import com.isi.impotregu.repository.DeclarantRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;

import java.util.List;

@Controller
@AllArgsConstructor
public class DeclarantController {
    private DeclarantRepository declarantRepository;
    @GetMapping(path="/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "4") int s,
                        @RequestParam(name = "keyword", defaultValue = "") String kw
                        ){
        Page<Declarant> pageDeclarants=declarantRepository.findByAdresseContains(kw,PageRequest.of(p,s));

        model.addAttribute("listDeclarants",pageDeclarants.getContent());
        model.addAttribute("pages",new int[pageDeclarants.getTotalPages()]);
        model.addAttribute("currentPage",p);
        model.addAttribute("keyword",kw);
        return "declarant";
    }
  
    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping ("/formDeclarants")
    public String formDeclarant(Model model){
        model.addAttribute("declarant", new Declarant());
        return "fromDeclarants";
    }

    @PostMapping("/saveDeclarant")
    public String saveDeclarant(@Valid  Declarant declarant, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "fromDeclarants";
        }
        declarantRepository.save(declarant);
        return "redirect:/index";

    }
}


#        _DeclarationController

package com.isi.impotregu.web;

import com.isi.impotregu.entities.Declarant;
import com.isi.impotregu.entities.Declaration;
import com.isi.impotregu.repository.DeclarantRepository;
import com.isi.impotregu.repository.DeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DeclarationController {
    @Autowired
    private DeclarationRepository declarationRepository;
    @Autowired
    private DeclarantRepository declarantRepository;

    @GetMapping("/declarations/new")
    public String showNewDeclarationForm(Model model){
        List<Declarant> listDeclarants = declarantRepository.findAll();
        model.addAttribute("declaration", new Declaration());
        model.addAttribute("listDeclarants", listDeclarants);
        return "declaration_form";
    }
    @PostMapping("/declarations/save")
    public String saveDeclaration(Declaration declaration){
        declarationRepository.save(declaration);

        return "redirect:/declarations";
    }

    @GetMapping("/declarations")
    public String listDeclarations(Model model){
        List<Declaration> listDeclarations = declarationRepository.findAll();
        model.addAttribute("listDeclarations", listDeclarations);
        return "declaration";
    }
}


#        _PaiementController

package com.isi.impotregu.web;

import com.isi.impotregu.entities.Declarant;
import com.isi.impotregu.entities.Declaration;
import com.isi.impotregu.entities.Paiement;
import com.isi.impotregu.repository.DeclarantRepository;
import com.isi.impotregu.repository.DeclarationRepository;
import com.isi.impotregu.repository.PaiementRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PaiementController {
    @Autowired
    private PaiementRepository paiementRepository;
    @Autowired
    private DeclarationRepository declarationRepository;

    @GetMapping(path="/paiements")
    public String index(Model model){
        List<Paiement> listPaiements = paiementRepository.findAll();
        model.addAttribute("listPaiements", listPaiements);
        return "paiement";
    }

    @GetMapping("/paiements/new")
    public String showNewDeclarationForm(Model model){
        List<Declaration> listDeclarations = declarationRepository.findAll();
        model.addAttribute("paiement", new Paiement());
        model.addAttribute("listDeclarations", listDeclarations);
        return "paiement_form";
    }
    @PostMapping("/paiements/save")
    public String savePaiement(Paiement paiement){
        paiementRepository.save(paiement);

        return "redirect:/paiements";
    }

}



# TEMPLATES


#            _home.html


<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
                xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
    <meta charset="UTF-8">
    <title>REGULARISATION DES IMPOTS</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.2.3/css/bootstrap.min.css">
    <script src="/webjars/bootstrap/5.2.3/js/bootstrap.bundle.js"></script>
    <script src="/webjars/jquery/3.6.4/jquery.min.js"></script>
    <link rel="stylesheet" href="/webjars/bootstrap-icons/1.10.3/font/bootstrap-icons.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" th:href="@{/home}">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Declarant
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" th:href="@{/formDeclarants}">New</a></li>
                        <li><a class="dropdown-item" th:href="@{/index}">List</a></li>

                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Declaration
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" th:href="@{/declarations/new}">New</a></li>
                        <li><a class="dropdown-item" th:href="@{/declarations}">List</a></li>

                    </ul>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Paiement
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" th:href="@{/paiements/new}">New</a></li>
                        <li><a class="dropdown-item" th:href="@{/paiements}">List</a></li>

                    </ul>
                </li>


                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                </li>
            </ul>
            <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
<div layout:fragment="content">
    <h2>WELCOME TO ISI SCHOOL - M1-GL - SPRING BOOT - THYMELEAF - NGOR SECK </h2>
    <h4>Papa Khaly SALL</h4>

</div>
</body>
</html>



#        _declarant.html


<!DOCTYPE html>
<html lang="en"
      xmlns:th="htttp://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="home.html"
>
<head>
    <meta charset="UTF-8">
    <title>Declarants</title>

</head>
<body>
    <div layout:fragment="content">

        <div class="p-3">
            <div class="card">
                <div class="card-header">Listes Declarants</div>
                <div class="card-body">
                    <form method="get" th:action="@{index}">
                        <label>Keyword:</label>
                        <input type="text" name="keyword" th:value="${keyword}">
                        <button type="submit" class="btn btn-info">
                            <i class="bi bi-search"></i>
                        </button>
                    </form>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>RaisonSociale</th>
                            <th>Adresse</th>
                            <th>Email</th>
                            <th>Telephone</th>
                        </tr>
                        <tr th:each="d:${listDeclarants}">
                            <td th:text="${d.id}"></td>
                            <td th:text="${d.raisonSociale}"></td>
                            <td th:text="${d.adresse}"></td>
                            <td th:text="${d.email}"></td>
                            <td th:text="${d.telephone}"></td>
                            <!-- <td>
                                <a onclick="return confirm('Etes vous sure?')"
                                   th:href="@{delete(id=${d.id},keyword=${keyword}, page=${currentPage})}" class="btn btn-danger">
                                    <i class="bi bi-trash"></i>
                                </a>
                            </td> -->
                        </tr>
                        </thead>
                    </table>
                    <ul class="nav nav-pills">
                        <li th:each="value,item:${pages}">
                            <a th:href="@{index(page=${item.index},keyword=${keyword})}"
                               th:class="${currentPage==item.index?'btn btn-info ms-1':'btn btn-outline-info ms-1'}"
                               th:text="${1+item.index}"></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </div>


</body>
</html>



   #         _fromsDeclarant.html

<!DOCTYPE html>
<html lang="en"
      xmlns:th="htttp://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="home.html"
>
<head>
    <meta charset="UTF-8">
    <title>Ajout Declarant</title>

</head>
<body>
    <div layout:fragment="content">
        <div class="row mt-3">
            <div class="col-md-6 offset-3">
                <div class="card">
                    <div class="card-header">Nouveau declarant</div>
                    <div class="card-body">

                        <div class="p-3">
                            <form th:action="@{/saveDeclarant}" method="post">
                                <div class="mb-2">
                                    <label class="form-label">Raison sociale</label>
                                    <input type="text" name="raisonSociale" class="form-control" th:value="${declarant.raisonSociale}">
                                    <span class="text-danger" th:errors="${declarant.raisonSociale}"></span>
                                </div>

                                <div class="mb-2">
                                    <label class="form-label">Adresse</label>
                                    <input type="text" name="adresse" class="form-control" th:value="${declarant.adresse}">
                                    <span class="text-danger" th:errors="${declarant.adresse}"></span>
                                </div>

                                <div class="mb-2">
                                    <label class="form-label">Email</label>
                                    <input type="email" name="email" class="form-control" th:value="${declarant.email}">

                                </div>

                                <div class="mb-2">
                                    <label class="form-label">Telephone</label>
                                    <input type="text" name="telephone" class="form-control" th:value="${declarant.telephone}">
                                    <span class="text-danger" th:errors="${declarant.telephone}"></span>
                                </div>

                                <button class="btn btn-success">Save</button>

                            </form>
                        </div>

                    </div>
                </div>

            </div>

        </div>

    </div>


</body>
</html>




#            _declaration.html

<!DOCTYPE html>
<html lang="en"
      xmlns:th="htttp://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="home.html"
>
<head>
    <meta charset="UTF-8">
    <title>Declaration</title>

</head>
<body>
    <div layout:fragment="content">

        <div class="p-3">
            <div class="card">
                <div class="card-header">Listes des Declarations</div>
                <div class="card-body">
                    <form method="get" th:action="@{declarations}">
                        <label>Keyword:</label>
                        <input type="text" name="keyword" th:value="${keyword}">
                        <button type="submit" class="btn btn-info">
                            <i class="bi bi-search"></i>
                        </button>
                    </form>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Date de declaration</th>
                            <th>Montant</th>
                            <th>Declarant</th>
                        </tr>
                        <tr th:each="d:${listDeclarations}">
                            <td th:text="${d.id}"></td>
                            <td th:text="${d.dateDeclaration}"></td>
                            <td th:text="${d.montantDeclaration}"></td>
                            <td th:text="${d.declarant.raisonSociale}"></td>
                            <!--<td>
                                <a onclick="return confirm('Etes vous sure?')"
                                   th:href="@{delete(id=${d.id},keyword=${keyword}, page=${currentPage})}" class="btn btn-danger">
                                    <i class="bi bi-trash"></i>
                                </a> -->
                            </td>
                        </tr>
                        </thead>
                    </table>
                    <ul class="nav nav-pills">
                        <li th:each="value,item:${pages}">
                            <a th:href="@{index(page=${item.index},keyword=${keyword})}"
                               th:class="${currentPage==item.index?'btn btn-info ms-1':'btn btn-outline-info ms-1'}"
                               th:text="${1+item.index}"></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </div>


</body>
</html>



  #      _declaration_form.html

        <!DOCTYPE html>
<html lang="en"
      xmlns:th="htttp://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="home.html"
>
<head>
    <meta charset="UTF-8">
    <title>Ajout Declaration</title>
  <link rel="stylesheet" href="/webjars/bootstrap/5.2.3/css/bootstrap.min.css">
</head>
<body>
<div layout:fragment="content">
  <div class="row mt-3">
    <div class="col-md-6 offset-3">
      <div class="card">
        <div class="card-header">Nouvelle declaration</div>
        <div class="card-body">

        <div class="p-3">

    <form th:action="@{/declarations/save}" th:object="${declaration}" method="post"
      style="max-width: 600px; margin: 0 auto;">



      <div class="mb-2">
        <label class="form-label">Date de declaration</label>
        <input type="date" name="dateDeclaration" class="form-control" th:value="${declaration.dateDeclaration}">
        <span class="text-danger" th:errors="${declaration.dateDeclaration}"></span>
      </div>


        <div class="mb-2">
          <label class="form-label">Montant</label>
          <input type="number" name="montantDeclaration" class="form-control" th:value="${declaration.montantDeclaration}">

        </div>

        <div class="mb-2">
          <label class="col-form-label col-sm-4">Declarant:</label>
          <div class="col-sm-8">
            <select th:field="*{declarant}" class="form-control" required>
              <th:block th:each="d : ${listDeclarants}">
                <option th:text="${d.raisonSociale}" th:value="${d.id}" />
              </th:block>
            </select>
          </div>
        </div>

        <div class="text-center p-3">
          <button type="submit" class="btn btn-success">Save</button>
        </div>



    </form>


  </div>
</div>
      </div>
    </div>
  </div>
</div>

</body>
</html>


 #           _paiement.html

<!DOCTYPE html>
<html lang="en"
      xmlns:th="htttp://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="home.html"
>
<head>
    <meta charset="UTF-8">
    <title>Paiement</title>

</head>
<body>
    <div layout:fragment="content">

        <div class="p-3">
            <div class="card">
                <div class="card-header">Listes Paiements</div>
                <div class="card-body">

                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Date de paiement</th>
                            <th>Montant</th>
                            <th>Declaration</th>
                        </tr>
                        <tr th:each="d:${listPaiements}">
                            <td th:text="${d.id}"></td>
                            <td th:text="${d.datePaiement}"></td>
                            <td th:text="${d.montantPaiement}"></td>
                            <td th:text="${d.declaration.montantDeclaration}"></td>
                            <!--<td>
                                <a onclick="return confirm('Etes vous sure?')"
                                   th:href="@{delete(id=${d.id},keyword=${keyword}, page=${currentPage})}" class="btn btn-danger">
                                    <i class="bi bi-trash"></i>
                                </a>
                            </td> -->
                        </tr>
                        </thead>
                    </table>
                    <ul class="nav nav-pills">
                        <li th:each="value,item:${pages}">
                            <a th:href="@{index(page=${item.index},keyword=${keyword})}"
                               th:class="${currentPage==item.index?'btn btn-info ms-1':'btn btn-outline-info ms-1'}"
                               th:text="${1+item.index}"></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </div>


</body>
</html>


#            _paiement_form.html

<!DOCTYPE html>
<html lang="en"
      xmlns:th="htttp://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="home.html"
>
<head>
    <meta charset="UTF-8">
    <title>Ajout un Paiement</title>
  <link rel="stylesheet" href="/webjars/bootstrap/5.2.3/css/bootstrap.min.css">
</head>
<body>
<div layout:fragment="content">
  <div class="row mt-3">
    <div class="col-md-6 offset-3">
      <div class="card">
        <div class="card-header">Nouveau Paiement</div>
        <div class="card-body">

        <div class="p-3">

    <form th:action="@{/paiements/save}" th:object="${paiement}" method="post"
      style="max-width: 600px; margin: 0 auto;">



      <div class="mb-2">
        <label class="form-label">Date de paiement</label>
        <input type="date" name="datePaiement" class="form-control" th:value="${paiement.datePaiement}">

      </div>


        <div class="mb-2">
          <label class="form-label">Montant</label>
          <input type="number" name="montantPaiement" class="form-control" th:value="${paiement.montantPaiement}">

        </div>

        <div class="mb-2">
          <label class="col-form-label col-sm-4">Declaration:</label>
          <div class="col-sm-8">
            <select th:field="*{declaration}" class="form-control" required>
              <th:block th:each="d : ${listDeclarations}">
                <option th:text="${d.montantDeclaration}" th:value="${d.id}" />
              </th:block>
            </select>
          </div>
        </div>

        <div class="text-center p-3">
          <button type="submit" class="btn btn-success">PAY</button>
        </div>

    </form>


  </div>
</div>
      </div>
    </div>
  </div>
</div>

</body>
</html>


#         pom.xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.isi</groupId>
    <artifactId>impotRegu</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>impotRegu</name>
    <description>impotRegu</description>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>



        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.webjars/bootstrap -->
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>bootstrap</artifactId>
            <version>5.2.3</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.webjars/jquery -->
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>jquery</artifactId>
            <version>3.6.4</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.webjars.npm/bootstrap-icons -->
        <dependency>
            <groupId>org.webjars.npm</groupId>
            <artifactId>bootstrap-icons</artifactId>
            <version>1.10.3</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/nz.net.ultraq.thymeleaf/thymeleaf-layout-dialect -->
        <dependency>
            <groupId>nz.net.ultraq.thymeleaf</groupId>
            <artifactId>thymeleaf-layout-dialect</artifactId>
            <version>3.2.1</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>




    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>



    


