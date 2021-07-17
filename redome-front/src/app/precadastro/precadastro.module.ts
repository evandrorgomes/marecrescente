import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { routing } from 'app/app.routers';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { MedicoService } from 'app/shared/service/medico.service';
import { RecaptchaModule } from 'ng-recaptcha';
import { RecaptchaFormsModule } from 'ng-recaptcha/forms';
import { ExportTranslateModule } from '../shared/export.translate.module';
import { EmailContatoModule } from './../shared/component/email/email.contato.module';
import { EnderecoContatoModule } from './../shared/component/endereco/endereco.contato.module';
import { ContatoTelefoneModule } from './../shared/component/telefone/contato.telefone.module';
import { UploadArquivoModule } from './../shared/upload/upload.arquivo.module';
import { PreCadastroComponent } from './precadastro.component';


/**
 * Classe Module utilizada para disponiblizar a classe PreCadastroComponent 
 * para as demais funcionalidades do projeto 
 * @author bruno.sousa
 */
@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ExportTranslateModule, InputModule, 
      EnderecoContatoModule, ContatoTelefoneModule, EmailContatoModule, 
    UploadArquivoModule, RecaptchaModule, RecaptchaFormsModule, routing],
  declarations: [ PreCadastroComponent ],
  providers: [MedicoService],
  exports: [ PreCadastroComponent ]
})
export class PreCadastroModule {}