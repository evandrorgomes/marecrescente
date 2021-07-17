import { IFiltrosOrdenacaoBuscaDTO } from "app/shared/dto/interface.filtros.ordenacao.busca.dto";
import { TiposDoador } from 'app/shared/enums/tipos.doador';
import { MatchDTO } from './../../../shared/component/listamatch/match.dto';
import { ClassificacaoABO } from "./classificacao.abo";

export class OrdenacaoMatchUtil {
    public mapaMetodos: Map<string, any> = new Map<string, any>();



    constructor(public atributosOrdenacao: string[], response: any) {
        this.atributosOrdenacao = atributosOrdenacao;
        this.mapaMetodos.set(response['wmda'], this.ordenarPorWmda);
        this.mapaMetodos.set(response['idade'], this.ordenarPorIdade);
        this.mapaMetodos.set(response['sexo'], this.ordenarPorSexo);
        this.mapaMetodos.set(response['mismatch'], this.ordenarPorMismatch);
        this.mapaMetodos.set(response['peso'], this.ordenarPorPeso);
        this.mapaMetodos.set(response['dtUltimaAtua'], this.ordenarPorDataUltimaAtualizacao);
        this.mapaMetodos.set(response['abo'], this.ordenarPorABO);
        this.mapaMetodos.set(response['nacional'], this.ordenarPorTipoDoadorNacional);
        this.mapaMetodos.set(response['internacional'], this.ordenarPorTipoDoadorInternacional);
        // this.mapaMetodos.set(response['qualificacao'], this.ordenarPorQualificacao);
    }

    private ordenarPorWmda(match1 :IFiltrosOrdenacaoBuscaDTO, match2 :IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any){

      if(match1.ordenacaoWmdaMatch == match2.ordenacaoWmdaMatch) {
          let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
          if (metodo) {
              try {
                  return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
              } catch (e) {
                  console.log(e)
              }
          }
      }else if (match1.ordenacaoWmdaMatch < match2.ordenacaoWmdaMatch) {
          return -1;
      }else {
          return 1;
      }
    }

    public ordernarListaMatch(lista: IFiltrosOrdenacaoBuscaDTO[], aboPaciente: string, classificacoesABO: ClassificacaoABO[]): MatchDTO[] {

        lista.sort((match1, match2) => {
            return this.ordenarPorFase(match1, match2, aboPaciente, this.atributosOrdenacao, -1, this.getMetodoOrdenacaoPorNumero, this.mapaMetodos, classificacoesABO, this.ordenarPorQualificacao);
        })
        return [...lista] as MatchDTO[];
    }

    private ordenarPorSexo(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {
        if (match1.sexo == match2.sexo) {
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
            }
        } else if (match1.sexo == 'M') {
            return -1;
        } else {
            return 1;
        }
    }

    private ordenarPorIdade(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {
        if (match1.dataNascimento.getTime() == match2.dataNascimento.getTime()) {
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
            }
        }
        return match2.dataNascimento.getTime() - match1.dataNascimento.getTime();
    }

    private ordenarPorMismatch(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {

        if (match1.mismatch == match2.mismatch) {
                try {
                    return ordenarPorQualificacao(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
        } else if (!match1.mismatch) {
            return -1;
        } else {
            return 1;
        }
    }

    private ordenarPorQualificacao(match1 :IFiltrosOrdenacaoBuscaDTO, match2 :IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any){

        if(match1.somaQualificacao == match2.somaQualificacao) {
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
            }
        }else if (match1.somaQualificacao > match2.somaQualificacao) {
            return -1;
        }else {
            return 1;
        }
    }

    private ordenarPorABO(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {
        let abo1: string = match1.abo ? match1.abo : '';
        let abo2: string = match2.abo ? match2.abo : '';

        if (!abo1 && abo2) {
            return 1;
        }

        if (abo1 && !abo2) {
            return -1;
        }

        let classificacoesDoPaciente: ClassificacaoABO[] = classificacoesABO.filter((classificacaoABO: ClassificacaoABO) => {
            return classificacaoABO.id.abo == aboPaciente
        });
        let prioridadeDoador1: number = classificacoesDoPaciente.filter((classificacaoABO: ClassificacaoABO) => {
            return classificacaoABO.id.aboRelacionado == abo1
        })[0].prioridade;

        let prioridadeDoador2: number = classificacoesDoPaciente.filter((classificacaoABO: ClassificacaoABO) => {
            return classificacaoABO.id.aboRelacionado == abo2
        })[0].prioridade;

        if(prioridadeDoador1 == prioridadeDoador2){
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
            }
        }

        if (prioridadeDoador1 < prioridadeDoador2) {
            return -1;
        } else if (prioridadeDoador1 > prioridadeDoador2) {
            return 1;
        }

        return 0;

    }

    private ordenarPorDataUltimaAtualizacao(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {
        let data1 = match1.dataAtualizacao ? match1.dataAtualizacao.getTime() : 0;
        let data2 = match2.dataAtualizacao ? match2.dataAtualizacao.getTime() : 0;
        if (data1 == data2) {
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
            }
        }

        return data2 - data1;
    }

    private ordenarPorPeso(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {
        if (match1.peso == match2.peso) {
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
            }
        }
        if (match1.peso > match2.peso) {
            return -1;
        }
        return 1;
    }


    private ordenarPorTipoDoadorNacional(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {
        if (match1.tipoDoador == match2.tipoDoador) {
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
            }
        }
        if (OrdenacaoMatchUtil.nacional(match1.tipoDoador) && OrdenacaoMatchUtil.internacional(match2.tipoDoador)) {
            return -1;
        }

        return 1;
    }

    private ordenarPorTipoDoadorInternacional(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {
        if (match1.tipoDoador == match2.tipoDoador) {
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e)
                }
            }
        }
        if (OrdenacaoMatchUtil.internacional(match1.tipoDoador) && OrdenacaoMatchUtil.nacional(match2.tipoDoador)) {
            return -1;
        }

        return 1;
    }

    private ordenarPorFase(match1: IFiltrosOrdenacaoBuscaDTO, match2: IFiltrosOrdenacaoBuscaDTO, aboPaciente: string, atributosOrdenacao: string[], numeroParametro: number, getMetodoOrdenacaoPorNumero: any, mapaMetodos: Map<string, any>, classificacoesABO: ClassificacaoABO[], ordenarPorQualificacao: any) {

        if (!match1.fase && !match2.fase) {
            return -1;
        }

        let prioridade1Fase = OrdenacaoMatchUtil.prioridadeFase(match1.fase);
        let prioridade2Fase = OrdenacaoMatchUtil.prioridadeFase(match2.fase);

        if (match1.fase == match2.fase) {
            let metodo = getMetodoOrdenacaoPorNumero(atributosOrdenacao, numeroParametro + 1, mapaMetodos);
            if (metodo) {
                try {
                    return metodo(match1, match2, aboPaciente, atributosOrdenacao, numeroParametro + 1, getMetodoOrdenacaoPorNumero, mapaMetodos, classificacoesABO, ordenarPorQualificacao);
                } catch (e) {
                    console.log(e);
                }
            }
        }

        if (prioridade1Fase < prioridade2Fase) {
            return 1;
        } else if (prioridade1Fase > prioridade2Fase) {
            return -1;
        }
        return 0;
    }

    private static nacional(tipoDoador: TiposDoador): boolean {
        return tipoDoador === TiposDoador.NACIONAL || tipoDoador === TiposDoador.CORDAO_NACIONAL;
    }

    private static internacional(tipoDoador: TiposDoador): boolean {
        return tipoDoador === TiposDoador.INTERNACIONAL || tipoDoador === TiposDoador.CORDAO_INTERNACIONAL;
    }

    private static prioridadeFase(fase: string): number {

        if (fase === 'F1' ||  fase === 'Fase 1') {
            return  1;
        }
        else if (fase === 'F2' ||  fase === 'Fase 2') {
            return  2;
        }
        else if (fase === 'F3' ||  fase === 'Fase 3') {
            return  3;
        }
    }

    private getMetodoOrdenacaoPorNumero(ordenacao: string[], numeroParametro: number, mapaMetodos: Map<string, any>): any {

        let nomeAtributo = ordenacao[numeroParametro];
        return mapaMetodos.get(nomeAtributo);
    }

}
