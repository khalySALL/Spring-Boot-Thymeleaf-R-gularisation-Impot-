package com.isi.impotregu.repository;

import com.isi.impotregu.entities.Declarant;
import com.isi.impotregu.entities.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

}
