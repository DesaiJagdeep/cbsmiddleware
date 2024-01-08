import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';
import { KarkhanaVasuliRecordsService } from '../service/karkhana-vasuli-records.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './karkhana-vasuli-records-delete-dialog.component.html',
})
export class KarkhanaVasuliRecordsDeleteDialogComponent {
  karkhanaVasuliRecords?: IKarkhanaVasuliRecords;

  constructor(protected karkhanaVasuliRecordsService: KarkhanaVasuliRecordsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.karkhanaVasuliRecordsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
