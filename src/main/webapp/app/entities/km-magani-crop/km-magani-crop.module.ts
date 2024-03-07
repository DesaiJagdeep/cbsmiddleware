import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KmMaganiCropComponent } from './list/km-magani-crop.component';
import { KmMaganiCropDetailComponent } from './detail/km-magani-crop-detail.component';
import { KmMaganiCropUpdateComponent } from './update/km-magani-crop-update.component';
import { KmMaganiCropDeleteDialogComponent } from './delete/km-magani-crop-delete-dialog.component';
import { KmMaganiCropRoutingModule } from './route/km-magani-crop-routing.module';

@NgModule({
  imports: [SharedModule, KmMaganiCropRoutingModule],
  declarations: [KmMaganiCropComponent, KmMaganiCropDetailComponent, KmMaganiCropUpdateComponent, KmMaganiCropDeleteDialogComponent],
})
export class KmMaganiCropModule {}
