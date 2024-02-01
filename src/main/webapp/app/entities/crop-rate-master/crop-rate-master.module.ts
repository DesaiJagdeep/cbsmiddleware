import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CropRateMasterComponent } from './list/crop-rate-master.component';
import { CropRateMasterDetailComponent } from './detail/crop-rate-master-detail.component';
import { CropRateMasterUpdateComponent } from './update/crop-rate-master-update.component';
import { CropRateMasterDeleteDialogComponent } from './delete/crop-rate-master-delete-dialog.component';
import { CropRateMasterRoutingModule } from './route/crop-rate-master-routing.module';

@NgModule({
  imports: [SharedModule, CropRateMasterRoutingModule],
  declarations: [
    CropRateMasterComponent,
    CropRateMasterDetailComponent,
    CropRateMasterUpdateComponent,
    CropRateMasterDeleteDialogComponent,
  ],
})
export class CropRateMasterModule {}
