import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CropMasterComponent } from './list/crop-master.component';
import { CropMasterDetailComponent } from './detail/crop-master-detail.component';
import { CropMasterUpdateComponent } from './update/crop-master-update.component';
import { CropMasterDeleteDialogComponent } from './delete/crop-master-delete-dialog.component';
import { CropMasterRoutingModule } from './route/crop-master-routing.module';

@NgModule({
  imports: [SharedModule, CropMasterRoutingModule],
  declarations: [CropMasterComponent, CropMasterDetailComponent, CropMasterUpdateComponent, CropMasterDeleteDialogComponent],
})
export class CropMasterModule {}
