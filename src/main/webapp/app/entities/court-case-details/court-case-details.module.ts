import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CourtCaseDetailsComponent } from './list/court-case-details.component';
import { CourtCaseDetailsDetailComponent } from './detail/court-case-details-detail.component';
import { CourtCaseDetailsUpdateComponent } from './update/court-case-details-update.component';
import { CourtCaseDetailsDeleteDialogComponent } from './delete/court-case-details-delete-dialog.component';
import { CourtCaseDetailsRoutingModule } from './route/court-case-details-routing.module';

@NgModule({
  imports: [SharedModule, CourtCaseDetailsRoutingModule],
  declarations: [
    CourtCaseDetailsComponent,
    CourtCaseDetailsDetailComponent,
    CourtCaseDetailsUpdateComponent,
    CourtCaseDetailsDeleteDialogComponent,
  ],
})
export class CourtCaseDetailsModule {}
