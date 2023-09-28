import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KMPUploadComponent } from './list/kmp-upload.component';
import { KMPUploadDetailComponent } from './detail/kmp-upload-detail.component';
import { KMPUploadUpdateComponent } from './update/kmp-upload-update.component';
import { KMPUploadDeleteDialogComponent } from './delete/kmp-upload-delete-dialog.component';
import { KMPUploadRoutingModule } from './route/kmp-upload-routing.module';

@NgModule({
  imports: [SharedModule, KMPUploadRoutingModule],
  declarations: [KMPUploadComponent, KMPUploadDetailComponent, KMPUploadUpdateComponent, KMPUploadDeleteDialogComponent],
})
export class KMPUploadModule {}
