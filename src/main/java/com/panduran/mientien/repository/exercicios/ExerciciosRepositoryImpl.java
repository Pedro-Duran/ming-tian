package com.panduran.mientien.repository.exercicios;

public class ExerciciosRepositoryImpl {

    public static final String BUSCAR_IDEOGRAMAS = """
SELECT * FROM DIA
WHERE (:data IS NULL OR TRUNC(DATA) = :data)
""";
}
