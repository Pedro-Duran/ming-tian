package com.panduran.mientien.repository.exercicios;

import com.panduran.mientien.entity.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.panduran.mientien.repository.exercicios.ExerciciosRepositoryImpl.BUSCAR_IDEOGRAMAS;

@Repository
public interface ExerciciosRepository extends JpaRepository<Dia, Long> {


    @Query(value = BUSCAR_IDEOGRAMAS, nativeQuery=true)
    List<Dia> buscarIdeogramas(@Param("data")LocalDate data);
}
