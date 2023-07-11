import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DistrictMasterComponent } from './list/district-master.component';
import { DistrictMasterDetailComponent } from './detail/district-master-detail.component';
import { DistrictMasterUpdateComponent } from './update/district-master-update.component';
import { DistrictMasterDeleteDialogComponent } from './delete/district-master-delete-dialog.component';
import { DistrictMasterRoutingModule } from './route/district-master-routing.module';

@NgModule({
  imports: [SharedModule, DistrictMasterRoutingModule],
  declarations: [
    DistrictMasterComponent,
    DistrictMasterDetailComponent,
    DistrictMasterUpdateComponent,
    DistrictMasterDeleteDialogComponent,
  ],
})
export class DistrictMasterModule {}
