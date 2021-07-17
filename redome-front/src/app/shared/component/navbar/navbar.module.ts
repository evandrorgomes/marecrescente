import { CommonModule } from '@angular/common';
import { InjectionToken, NgModule } from '@angular/core';
import { routing } from 'app/app.routers';
import { BsDropdownModule, CollapseModule } from 'ngx-bootstrap';
import { NavbarComponent } from './navbar.component';
export const WINDOW           = new InjectionToken( 'WINDOW' );

@NgModule({
  imports: [
    CommonModule,
    BsDropdownModule,
    routing,
    CollapseModule
  ],
  declarations: [NavbarComponent],
  exports: [NavbarComponent]
})
export class NavbarModule { }
