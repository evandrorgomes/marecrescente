import { AfterViewInit, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { fromEvent } from 'rxjs/observable/fromEvent';
import { Subscription } from 'rxjs/Subscription';
import { NavBarItem } from './navbar.item';


@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy, AfterViewInit { 

	private numeroDeIcones = 6;

	private resizeObservable$: Observable<Event>;
	private resizeSubscription$: Subscription;

	@Input()
	public nomeUsuario: String = "";

	@Input()
	public usuarioItens: NavBarItem[] = [];

	@Input()
	private itens: NavBarItem[] = [];

	@Input()
	private totalIconesParaExibir: number = this.numeroDeIcones;

	@Input()
	private maxWidth: number = 1024;

	@Input()
	private minWidth: number = 320;

	public _navBarItens: NavBarItem[];
	public _nabBarButtonItens: NavBarItem[];

	public _exibirBotaoMenu = false;
	public _exibirDropdownUsuario = false;
	public _temUsuarioItens = false;

	public isCollapsed = true;

	constructor(private router: Router) {		
	}

	ngOnInit() {

		
	}

	ngAfterViewInit() {		
		/* setTimeout(() => {			
			this._temUsuarioItens = this.usuarioItens.length !== 0;
			this.doNavBar(this.window.innerWidth);
		}, 1000); */
		this.resizeObservable$ = fromEvent(window, 'resize');
		this.resizeSubscription$ = this.resizeObservable$.subscribe(evt => {

			const width = (<Window>evt.target).innerWidth;

			this.doNavBar(width);

		});

	}

	ngOnDestroy() {
		this.resizeSubscription$.unsubscribe()
	}

	private doNavBar(width: number) {
		if (width <= this.minWidth) {
			this._nabBarButtonItens = this.itens;
			this._navBarItens = [];
			this._exibirBotaoMenu = true;
			this._exibirDropdownUsuario = false;
		} else if (width > this.minWidth) {

			if (!this._nabBarButtonItens) {
				this._nabBarButtonItens = [];
			}

			let totalParaRemover = Math.floor((width - 175) / 86);
			const qtdeItens = this.itens.length > this.totalIconesParaExibir ? this.totalIconesParaExibir : this.itens.length;
			if (totalParaRemover <= qtdeItens) {
				totalParaRemover -= 2;
			} else {
				totalParaRemover = qtdeItens;
			}

			if (totalParaRemover > 0) {
				this._nabBarButtonItens = this.itens.slice(totalParaRemover, this.itens.length);
				this._navBarItens = this.itens.slice(0, totalParaRemover);
			}

			this._exibirBotaoMenu = this._nabBarButtonItens.length > 0;

			if (!this._navBarItens) {
				this._exibirDropdownUsuario = true;	
			} else {
				if (width < this.maxWidth && this._nabBarButtonItens.length > 0) {
					this._exibirDropdownUsuario = false;	
				}
				else {
					this._exibirDropdownUsuario = true;	
				}

			}
			//this._exibirDropdownUsuario = this._navBarItens ? this._navBarItens.length > 0 : true;

		}

	}


	public executeClick(item: NavBarItem): boolean {
		if (item.uri) {
			this.router.navigateByUrl(item.uri.toString());
		} else if (item.metodoClick) {
			item.metodoClick();
		}
		if (!this.isCollapsed) {
			this.isCollapsed = true;
		}
		return false;

	}


	public update(usuarioItens: NavBarItem[], itens: NavBarItem[], nomeUsuario: string): void {

		if (usuarioItens) {
			this.usuarioItens = usuarioItens
		}

		if (itens) {
			this.itens = itens;
		}

		this._temUsuarioItens = this.usuarioItens && this.usuarioItens.length !== 0;

		this.nomeUsuario = nomeUsuario;
	
		this.doNavBar(window.innerWidth);
	}



}
