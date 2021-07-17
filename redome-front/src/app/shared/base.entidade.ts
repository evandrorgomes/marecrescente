export abstract  class BaseEntidade {

    /**
	 * Método utilizado pelo JSON.stringfy
	 * 
	 * @returns {string} 
	 * 
	 * @memberOf Paciente
	 */
	public toJSON(): string {
		return this._onlyGetters(this);
	}

	public abstract jsonToEntity(res:any): any;

	/**
	 * 
	 * 
	 * @private
	 * @param {*} obj 
	 * @returns {*} 
	 * 
	 * @memberOf Paciente
	 */
	private _onlyGetters(obj: any): any {
		// somente para objetos
		if (obj === null || obj instanceof Array) {
			return obj;
		}
		let onlyGetters: any = {};

		// Interage sobre cada propriedade para este objeto e seus protótipos. 
		// Obteremos cada propriedade apenas uma vez, independentemente de quantas vezes existe nos protótipos parentes.
		for (let key in obj) {
			let proto = obj;

			//Verifica getOwnPropertyDescriptor para ver se a propriedade é um getter.
			//Ele só retornará a descrição da propriedade neste objeto (e não protótipos), então nós temos que andar na cadeia do protótipo.
			while (proto) {
				let descriptor = Object.getOwnPropertyDescriptor(proto, key);
				
				if (descriptor && descriptor.get) {
					// Acessa o getter no objeto original (não prototype), porque enquanto o getter pode ser definido no proto, 
					// queremos que a propriedade seja a do nível mais baixo
					let val = obj[key];

					if (typeof val === 'object') {
						if (val instanceof Date) {
							onlyGetters[key] = (<Date>val).toJSON();
						}
						else {
							onlyGetters[key] = this._onlyGetters(val);
						}
						
						
					} else {
						if (key === 'type') {
							onlyGetters['@' + key] = val;
						}
						else {
							onlyGetters[key] = val;
						}
					}

					proto = null;
				} else {
					proto = Object.getPrototypeOf(proto);
				}
			}
		}

		return onlyGetters;
	}

}
