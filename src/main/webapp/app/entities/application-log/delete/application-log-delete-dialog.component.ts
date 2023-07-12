import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IApplicationLog } from '../application-log.model';
import { ApplicationLogService } from '../service/application-log.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './application-log-delete-dialog.component.html',
})
export class ApplicationLogDeleteDialogComponent {
  applicationLog?: IApplicationLog;

  constructor(protected applicationLogService: ApplicationLogService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicationLogService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
