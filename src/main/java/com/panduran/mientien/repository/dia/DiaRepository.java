package com.panduran.mientien.repository.dia;

import com.panduran.mientien.entity.Dia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

import static com.panduran.mientien.repository.dia.DiaRepositoryImpl.CADASTRAR_DIA;

@Repository
public interface DiaRepository extends JpaRepository<Dia, Long> {

    @Modifying
    @Transactional
    @Query(value = CADASTRAR_DIA, nativeQuery=true)
    void cadastrar(@Param("textoPt") String textoPt,
                   @Param("textoZh") String textoZH,
                   @Param("pingYing") String pingYing,
                   @Param("caminhoAudio") String caminhoAudio,
                   @Param("data") LocalDateTime data,
                   @Param("palavrasTraduzidas") Map<String, String> palavrasTraduzidas);
}

