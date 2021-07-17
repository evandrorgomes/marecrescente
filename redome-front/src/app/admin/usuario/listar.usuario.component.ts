import { Component, ComponentFactory, ComponentFactoryResolver, ComponentRef, ViewChild, ViewContainerRef, AfterContentInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CrudConsultaComponent } from 'app/admin/crud/consulta/crud.consulta.component';
import { UsuarioService } from 'app/admin/usuario/usuario.service';
import { ColunaDto } from 'app/shared/dto/coluna.dto';
import { TiposColuna } from 'app/shared/enums/tipos.coluna';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { FiltroConsulta } from 'app/admin/crud/consulta/filtro/filtro.consulta';
import { UrlParametro } from '../../shared/url.parametro';
import { ArrayUtil } from '../../shared/util/array.util';
import { UsuarioLogado } from '../../shared/dominio/usuario.logado';

@Component({
  selector: 'listar-usuario',
  templateUrl: './listar.usuario.component.html',
  styleUrls: ['./listar.usuario.component.css']
})
export class ListarUsuarioComponent implements PermissaoRotaComponente {
  
  @ViewChild('consulta', { read: ViewContainerRef }) container: ViewContainerRef;
  private componentRef: ComponentRef<CrudConsultaComponent>;

  @ViewChild(FiltroConsulta)
  private filtroConsulta: FiltroConsulta;

  public filtrarPorLogin: string;
  public filtrarPorNome: string;
  public filtrarPorEmail: string;


  constructor(private router: Router,
    private translate: TranslateService, 
    private resolver: ComponentFactoryResolver,
    private usuarioService: UsuarioService) {

  }

  ngOnInit(): void {
    this.translate.get('manterUsuario').subscribe(res => {
      //this.container.clear();
      const factory: ComponentFactory<CrudConsultaComponent> = this.resolver.resolveComponentFactory(CrudConsultaComponent);
      this.componentRef = this.container.createComponent(factory);
      this.componentRef.instance.colunas = [
        new ColunaDto('id', TiposColuna.NUMBER, true, false),
        new ColunaDto('username', TiposColuna.STRING),
        new ColunaDto('nome', TiposColuna.STRING),
        new ColunaDto('email', TiposColuna.STRING)
      ];
      this.componentRef.instance.crudData = res;
      this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
        return this.usuarioService.listarUsuarios(pagina, quantidadeRegistros);
      };
      this.componentRef.instance.metodoClicarLinha = (value: any): void => {
        let usuario: UsuarioLogado = value;
        this.router.navigateByUrl("/admin/usuarios/" + usuario.id);
      };
      this.componentRef.instance.mostrarBotaoNovo();
      this.componentRef.instance.metodoClicarNovo = (): void => {
        this.router.navigateByUrl("/admin/usuarios/novo");
      };
      
      this.componentRef.instance.filtroConsulta = this.filtroConsulta;

    });

  }

  ngOnDestroy() {
    this.componentRef.destroy();
  }

  /**
   * @description Chama o lista de usuários utilizando algum dos filtros.
   * @author Pizão
   */
  public listarUtilizandoFiltros(): void{
    let filtros: UrlParametro[] = [];

    if(this.filtrarPorNome){
      filtros.push(new UrlParametro("nome", this.filtrarPorNome));
    }

    if(this.filtrarPorEmail){
      filtros.push(new UrlParametro("email", this.filtrarPorEmail));
    }

    if(this.filtrarPorLogin){
      filtros.push(new UrlParametro("login", this.filtrarPorLogin));
    }

    this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
      return this.usuarioService.listarUsuarios(
          pagina, quantidadeRegistros, ArrayUtil.isNotEmpty(filtros) ? filtros : null);
    };
    this.componentRef.instance.ngOnInit();
  }

  nomeComponente(): string {
    return "ListarUsuarioComponent";
  }

}