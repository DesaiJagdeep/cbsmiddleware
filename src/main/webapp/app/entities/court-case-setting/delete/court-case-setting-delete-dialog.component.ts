import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICourtCaseSetting } from '../court-case-setting.model';
import { CourtCaseSettingService } from '../service/court-case-setting.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './court-case-setting-delete-dialog.component.html',
})
export class CourtCaseSettingDeleteDialogComponent {
  courtCaseSetting?: ICourtCaseSetting;

  constructor(protected courtCaseSettingService: CourtCaseSettingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courtCaseSettingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
