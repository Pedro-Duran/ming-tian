CREATE TABLE DIA (
  ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  TEXTO_PT VARCHAR2(4000),
  TEXTO_ZH VARCHAR2(4000),
  PING_YING VARCHAR2(4000),
  CAMINHO_AUDIO VARCHAR2(1000),
  DATA DATE
);