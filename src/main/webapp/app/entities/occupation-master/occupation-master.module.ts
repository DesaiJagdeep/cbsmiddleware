import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OccupationMasterComponent } from './list/occupation-master.component';
import { OccupationMasterDetailComponent } from './detail/occupation-master-detail.component';
import { OccupationMasterUpdateComponent } from './update/occupation-master-update.component';
import { OccupationMasterDeleteDialogComponent } from './delete/occupation-master-delete-dialog.component';
import { OccupationMasterRoutingModule } from './route/occupation-master-routing.module';

@NgModule({
  imports: [SharedModule, OccupationMasterRoutingModule],
  declarations: [
    OccupationMasterComponent,
    OccupationMasterDetailComponent,
    OccupationMasterUpdateComponent,
    OccupationMasterDeleteDialogComponent,
  ],
})
export class OccupationMasterModule {}
