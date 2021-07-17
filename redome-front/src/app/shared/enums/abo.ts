export enum ABOType {
    A,
    B,
    AB,
    O

}



interface ABOS {
	codigo,
    positivo,
    negativo
}

const ABO = {
	A: {
		codigo: 'A',
        positivo: 'A+',
        negativo: 'A-'

	} as ABOS,
	B: {
		codigo: 'B',
        positivo: 'B+',
        negativo: 'B-'

    } as ABOS,
    AB: {
		codigo: 'AB',
        positivo: 'AB+',
        negativo: 'AB-'

    } as ABOS,
    O: {
		codigo: 'O',
        positivo: 'O+',
        negativo: 'O-'

	} as ABOS
	
}
type ABO = (typeof ABO)[keyof typeof ABO];
export { ABO };