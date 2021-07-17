interface IStatusRetornoInclusaoDTO {
    id: number,
    equals: (value: any) => boolean;
}

class StatusRetornoInclusao implements IStatusRetornoInclusaoDTO {

    private _id: number;
    private _name: string;

    constructor(id: number, name: string) {
        this._id = id;
        this._name = name;
    }

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    public equals(value: any): boolean {
        if (value instanceof StatusRetornoInclusao) {
            return value == this
        }
        if ((typeof value) == "number" ) {
            return new Number(value) == this._id;
        }
        
        return value == this._name;        
    }

}

const StatusRetornoInclusaoDTO = {
	SUCESSO: new StatusRetornoInclusao(1, "SUCESSO") as IStatusRetornoInclusaoDTO,
	FRACASSO: new StatusRetornoInclusao(2, "FRACASSO") as IStatusRetornoInclusaoDTO,
    values: (): IStatusRetornoInclusaoDTO[] => {
        return [
            StatusRetornoInclusaoDTO.SUCESSO,
            StatusRetornoInclusaoDTO.FRACASSO
        ];
    },
    valueOf: function (value: any): IStatusRetornoInclusaoDTO {
        if (!value) {
            return null;
        }
        return StatusRetornoInclusaoDTO.values().find(status => status.equals(value));
    }
}
type StatusRetornoInclusaoDTO = (typeof StatusRetornoInclusaoDTO)[keyof typeof StatusRetornoInclusaoDTO];
export { StatusRetornoInclusaoDTO };