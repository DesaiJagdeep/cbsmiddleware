import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DesignationMasterComponent } from './list/designation-master.component';
import { DesignationMasterDetailComponent } from './detail/designation-master-detail.component';
import { DesignationMasterUpdateComponent } from './update/designation-master-update.component';
import { DesignationMasterDeleteDialogComponent } from './delete/designation-master-delete-dialog.component';
import { DesignationMasterRoutingModule } from './route/designation-master-routing.module';

@NgModule({
  imports: [SharedModule, DesignationMasterRoutingModule],
  declarations: [
    DesignationMasterComponent,
    DesignationMasterDetailComponent,
    DesignationMasterUpdateComponent,
    DesignationMasterDeleteDialogComponent,
  ],
})
export class DesignationMasterModule {}
