package com.panduran.mientien.repository;
import com.panduran.mientien.entity.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaRepository extends JpaRepository<Dia, Long> {
}
