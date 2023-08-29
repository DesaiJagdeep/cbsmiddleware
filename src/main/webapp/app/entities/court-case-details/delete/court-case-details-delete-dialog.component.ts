import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICourtCaseDetails } from '../court-case-details.model';
import { CourtCaseDetailsService } from '../service/court-case-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './court-case-details-delete-dialog.component.html',
})
export class CourtCaseDetailsDeleteDialogComponent {
  courtCaseDetails?: ICourtCaseDetails;

  constructor(protected courtCaseDetailsService: CourtCaseDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courtCaseDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
