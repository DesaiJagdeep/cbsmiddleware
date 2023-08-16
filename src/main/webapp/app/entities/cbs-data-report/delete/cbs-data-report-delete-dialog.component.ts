import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICbsDataReport } from '../cbs-data-report.model';
import { CbsDataReportService } from '../service/cbs-data-report.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './cbs-data-report-delete-dialog.component.html',
})
export class CbsDataReportDeleteDialogComponent {
  cbsDataReport?: ICbsDataReport;

  constructor(protected cbsDataReportService: CbsDataReportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbsDataReportService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
