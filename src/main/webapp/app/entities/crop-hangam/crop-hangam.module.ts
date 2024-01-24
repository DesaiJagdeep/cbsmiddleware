import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CropHangamComponent } from './list/crop-hangam.component';
import { CropHangamDetailComponent } from './detail/crop-hangam-detail.component';
import { CropHangamUpdateComponent } from './update/crop-hangam-update.component';
import { CropHangamDeleteDialogComponent } from './delete/crop-hangam-delete-dialog.component';
import { CropHangamRoutingModule } from './route/crop-hangam-routing.module';

@NgModule({
  imports: [SharedModule, CropHangamRoutingModule],
  declarations: [CropHangamComponent, CropHangamDetailComponent, CropHangamUpdateComponent, CropHangamDeleteDialogComponent],
})
export class CropHangamModule {}
