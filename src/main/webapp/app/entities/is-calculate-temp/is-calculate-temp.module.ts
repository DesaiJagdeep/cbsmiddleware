import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IsCalculateTempComponent } from './list/is-calculate-temp.component';
import { IsCalculateTempDetailComponent } from './detail/is-calculate-temp-detail.component';
import { IsCalculateTempUpdateComponent } from './update/is-calculate-temp-update.component';
import { IsCalculateTempDeleteDialogComponent } from './delete/is-calculate-temp-delete-dialog.component';
import { IsCalculateTempRoutingModule } from './route/is-calculate-temp-routing.module';

@NgModule({
  imports: [SharedModule, IsCalculateTempRoutingModule],
  declarations: [
    IsCalculateTempComponent,
    IsCalculateTempDetailComponent,
    IsCalculateTempUpdateComponent,
    IsCalculateTempDeleteDialogComponent,
  ],
})
export class IsCalculateTempModule {}
