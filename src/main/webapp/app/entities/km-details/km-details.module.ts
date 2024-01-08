import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KmDetailsComponent } from './list/km-details.component';
import { KmDetailsDetailComponent } from './detail/km-details-detail.component';
import { KmDetailsUpdateComponent } from './update/km-details-update.component';
import { KmDetailsDeleteDialogComponent } from './delete/km-details-delete-dialog.component';
import { KmDetailsRoutingModule } from './route/km-details-routing.module';

@NgModule({
  imports: [SharedModule, KmDetailsRoutingModule],
  declarations: [KmDetailsComponent, KmDetailsDetailComponent, KmDetailsUpdateComponent, KmDetailsDeleteDialogComponent],
})
export class KmDetailsModule {}
