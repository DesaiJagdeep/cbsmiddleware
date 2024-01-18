import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KmCropsComponent } from './list/km-crops.component';
import { KmCropsDetailComponent } from './detail/km-crops-detail.component';
import { KmCropsUpdateComponent } from './update/km-crops-update.component';
import { KmCropsDeleteDialogComponent } from './delete/km-crops-delete-dialog.component';
import { KmCropsRoutingModule } from './route/km-crops-routing.module';

@NgModule({
  imports: [SharedModule, KmCropsRoutingModule],
  declarations: [KmCropsComponent, KmCropsDetailComponent, KmCropsUpdateComponent, KmCropsDeleteDialogComponent],
})
export class KmCropsModule {}
