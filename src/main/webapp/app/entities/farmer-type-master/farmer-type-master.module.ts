import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FarmerTypeMasterComponent } from './list/farmer-type-master.component';
import { FarmerTypeMasterDetailComponent } from './detail/farmer-type-master-detail.component';
import { FarmerTypeMasterUpdateComponent } from './update/farmer-type-master-update.component';
import { FarmerTypeMasterDeleteDialogComponent } from './delete/farmer-type-master-delete-dialog.component';
import { FarmerTypeMasterRoutingModule } from './route/farmer-type-master-routing.module';

@NgModule({
  imports: [SharedModule, FarmerTypeMasterRoutingModule],
  declarations: [
    FarmerTypeMasterComponent,
    FarmerTypeMasterDetailComponent,
    FarmerTypeMasterUpdateComponent,
    FarmerTypeMasterDeleteDialogComponent,
  ],
})
export class FarmerTypeMasterModule {}
