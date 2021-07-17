import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { FileUploadModule } from 'ng2-file-upload';

@NgModule({
  imports: [FileUploadModule], 
  declarations: [],
  providers: [],
  exports: [FileUploadModule]
})
export class ExportFileUploaderDirectiveModule {}