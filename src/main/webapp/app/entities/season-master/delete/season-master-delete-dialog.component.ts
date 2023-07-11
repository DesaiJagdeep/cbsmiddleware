import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISeasonMaster } from '../season-master.model';
import { SeasonMasterService } from '../service/season-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './season-master-delete-dialog.component.html',
})
export class SeasonMasterDeleteDialogComponent {
  seasonMaster?: ISeasonMaster;

  constructor(protected seasonMasterService: SeasonMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.seasonMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
