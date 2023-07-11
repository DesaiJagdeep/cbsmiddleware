import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PacsMasterComponent } from './list/pacs-master.component';
import { PacsMasterDetailComponent } from './detail/pacs-master-detail.component';
import { PacsMasterUpdateComponent } from './update/pacs-master-update.component';
import { PacsMasterDeleteDialogComponent } from './delete/pacs-master-delete-dialog.component';
import { PacsMasterRoutingModule } from './route/pacs-master-routing.module';

@NgModule({
  imports: [SharedModule, PacsMasterRoutingModule],
  declarations: [PacsMasterComponent, PacsMasterDetailComponent, PacsMasterUpdateComponent, PacsMasterDeleteDialogComponent],
})
export class PacsMasterModule {}
