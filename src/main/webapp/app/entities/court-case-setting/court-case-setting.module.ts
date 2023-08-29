import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CourtCaseSettingComponent } from './list/court-case-setting.component';
import { CourtCaseSettingDetailComponent } from './detail/court-case-setting-detail.component';
import { CourtCaseSettingUpdateComponent } from './update/court-case-setting-update.component';
import { CourtCaseSettingDeleteDialogComponent } from './delete/court-case-setting-delete-dialog.component';
import { CourtCaseSettingRoutingModule } from './route/court-case-setting-routing.module';

@NgModule({
  imports: [SharedModule, CourtCaseSettingRoutingModule],
  declarations: [
    CourtCaseSettingComponent,
    CourtCaseSettingDetailComponent,
    CourtCaseSettingUpdateComponent,
    CourtCaseSettingDeleteDialogComponent,
  ],
})
export class CourtCaseSettingModule {}
