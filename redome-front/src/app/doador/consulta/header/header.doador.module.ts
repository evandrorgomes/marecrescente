import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HeaderDoadorComponent } from './header.doador.component';

@NgModule({
  imports: [BrowserModule, CommonModule, ExportTranslateModule], 
  declarations: [HeaderDoadorComponent],
  providers: [],
  exports: [HeaderDoadorComponent]
})
export class HeaderDoadorModule {}