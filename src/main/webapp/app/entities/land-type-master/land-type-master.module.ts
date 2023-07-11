import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LandTypeMasterComponent } from './list/land-type-master.component';
import { LandTypeMasterDetailComponent } from './detail/land-type-master-detail.component';
import { LandTypeMasterUpdateComponent } from './update/land-type-master-update.component';
import { LandTypeMasterDeleteDialogComponent } from './delete/land-type-master-delete-dialog.component';
import { LandTypeMasterRoutingModule } from './route/land-type-master-routing.module';

@NgModule({
  imports: [SharedModule, LandTypeMasterRoutingModule],
  declarations: [
    LandTypeMasterComponent,
    LandTypeMasterDetailComponent,
    LandTypeMasterUpdateComponent,
    LandTypeMasterDeleteDialogComponent,
  ],
})
export class LandTypeMasterModule {}
