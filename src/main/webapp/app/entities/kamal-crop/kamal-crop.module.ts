import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KamalCropComponent } from './list/kamal-crop.component';
import { KamalCropDetailComponent } from './detail/kamal-crop-detail.component';
import { KamalCropUpdateComponent } from './update/kamal-crop-update.component';
import { KamalCropDeleteDialogComponent } from './delete/kamal-crop-delete-dialog.component';
import { KamalCropRoutingModule } from './route/kamal-crop-routing.module';

@NgModule({
  imports: [SharedModule, KamalCropRoutingModule],
  declarations: [KamalCropComponent, KamalCropDetailComponent, KamalCropUpdateComponent, KamalCropDeleteDialogComponent],
})
export class KamalCropModule {}
