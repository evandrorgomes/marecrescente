import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HeaderDoadorInternacionalComponent } from './header.doador.internacional.component';
import { ExportTranslateModule } from '../../shared/export.translate.module';

@NgModule({
  imports: [BrowserModule, CommonModule, ExportTranslateModule], 
  declarations: [HeaderDoadorInternacionalComponent],
  providers: [],
  exports: [HeaderDoadorInternacionalComponent]
})
export class HeaderDoadorInternacionalModule {}