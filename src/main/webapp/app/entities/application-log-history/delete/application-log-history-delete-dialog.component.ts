import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IApplicationLogHistory } from '../application-log-history.model';
import { ApplicationLogHistoryService } from '../service/application-log-history.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './application-log-history-delete-dialog.component.html',
})
export class ApplicationLogHistoryDeleteDialogComponent {
  applicationLogHistory?: IApplicationLogHistory;

  constructor(protected applicationLogHistoryService: ApplicationLogHistoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicationLogHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
