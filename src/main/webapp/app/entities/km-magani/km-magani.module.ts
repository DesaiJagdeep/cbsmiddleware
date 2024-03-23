import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KmMaganiComponent } from './list/km-magani.component';
import { KmMaganiDetailComponent } from './detail/km-magani-detail.component';
import { KmMaganiUpdateComponent } from './update/km-magani-update.component';
import { KmMaganiDeleteDialogComponent } from './delete/km-magani-delete-dialog.component';
import { KmMaganiRoutingModule } from './route/km-magani-routing.module';

@NgModule({
  imports: [SharedModule, KmMaganiRoutingModule],
  declarations: [KmMaganiComponent, KmMaganiDetailComponent, KmMaganiUpdateComponent, KmMaganiDeleteDialogComponent],
})
export class KmMaganiModule {}
