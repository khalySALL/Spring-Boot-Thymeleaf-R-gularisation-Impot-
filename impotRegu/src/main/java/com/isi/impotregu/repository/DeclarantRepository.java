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
