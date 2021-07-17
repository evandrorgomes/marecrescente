import { routing } from './../app.routers';
import { ExportTranslateModule } from '../shared/export.translate.module';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { Injectable } from '@angular/core';
import { Login } from './login';
import {LoginComponent} from './login.component';


/**
 * Classe Module utilizada para disponiblizar a classe LoginComponent 
 * para as demais funcionalidades do projeto 
 * @author Filipe Paes
 */
@NgModule({
  imports: [CommonModule, FormsModule, ExportTranslateModule, routing],
  declarations: [ LoginComponent],
  exports: [LoginComponent]
})
export class LoginModule {}