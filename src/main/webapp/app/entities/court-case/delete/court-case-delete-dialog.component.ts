import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICourtCase } from '../court-case.model';
import { CourtCaseService } from '../service/court-case.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './court-case-delete-dialog.component.html',
})
export class CourtCaseDeleteDialogComponent {
  courtCase?: ICourtCase;

  constructor(protected courtCaseService: CourtCaseService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courtCaseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
