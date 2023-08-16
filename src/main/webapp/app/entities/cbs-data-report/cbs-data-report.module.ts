import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CbsDataReportComponent } from './list/cbs-data-report.component';
import { CbsDataReportDetailComponent } from './detail/cbs-data-report-detail.component';
import { CbsDataReportUpdateComponent } from './update/cbs-data-report-update.component';
import { CbsDataReportDeleteDialogComponent } from './delete/cbs-data-report-delete-dialog.component';
import { CbsDataReportRoutingModule } from './route/cbs-data-report-routing.module';

@NgModule({
  imports: [SharedModule, CbsDataReportRoutingModule],
  declarations: [CbsDataReportComponent, CbsDataReportDetailComponent, CbsDataReportUpdateComponent, CbsDataReportDeleteDialogComponent],
})
export class CbsDataReportModule {}
