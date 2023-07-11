import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITalukaMaster } from '../taluka-master.model';
import { TalukaMasterService } from '../service/taluka-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './taluka-master-delete-dialog.component.html',
})
export class TalukaMasterDeleteDialogComponent {
  talukaMaster?: ITalukaMaster;

  constructor(protected talukaMasterService: TalukaMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.talukaMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
