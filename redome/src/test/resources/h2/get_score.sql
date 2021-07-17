CREATE ALIAS GET_SCORE FOR "br.org.cancer.modred.test.util.H2Functions.getScore";

ALTER TABLE "PACIENTE" 
ADD "PACI_NR_SCORE" number AS GET_SCORE(PACI_NR_RMR);

CREATE ALIAS PROC_CRIAR_MATCH_FAKE FOR "br.org.cancer.modred.test.util.H2Functions.procCriarMatchFake";

CREATE ALIAS PROC_MATCH_DOADOR FOR "br.org.cancer.modred.test.util.H2Functions.proc_match_doador";
