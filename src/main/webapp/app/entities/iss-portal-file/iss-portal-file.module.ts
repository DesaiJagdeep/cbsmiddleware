import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssPortalFileComponent } from './list/iss-portal-file.component';
import { IssPortalFileDetailComponent } from './detail/iss-portal-file-detail.component';
import { IssPortalFileUpdateComponent } from './update/iss-portal-file-update.component';
import { IssPortalFileDeleteDialogComponent } from './delete/iss-portal-file-delete-dialog.component';
import { IssPortalFileRoutingModule } from './route/iss-portal-file-routing.module';

@NgModule({
  imports: [SharedModule, IssPortalFileRoutingModule],
  declarations: [IssPortalFileComponent, IssPortalFileDetailComponent, IssPortalFileUpdateComponent, IssPortalFileDeleteDialogComponent],
})
export class IssPortalFileModule {}
