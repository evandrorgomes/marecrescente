enum FontesCelulasType  {    
    MEDULA_OSSEA,
	SANGUE_PERIFERICO,
	LINFOCITOS,
	CORDAO_UMBILICAL
}

interface FontesCelula {
	id,
	sigla
}

const FontesCelulas = {
	MEDULA_OSSEA: {
		id: 1,
		sigla: 'MO'		

	} as FontesCelula,
	SANGUE_PERIFERICO: {
		id: 2,
		sigla: 'SP'
	} as FontesCelula,
	CORDAO_UMBILICAL: {
		id: 4,
		sigla: 'CU'
	} as FontesCelula
}
type FontesCelulas = (typeof FontesCelulas)[keyof typeof FontesCelulas];
export { FontesCelulas };