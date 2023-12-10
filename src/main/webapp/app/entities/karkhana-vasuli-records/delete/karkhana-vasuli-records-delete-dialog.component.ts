import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';
import { KarkhanaVasuliRecordsService } from '../service/karkhana-vasuli-records.service';

@Component({
  standalone: true,
  templateUrl: './karkhana-vasuli-records-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class KarkhanaVasuliRecordsDeleteDialogComponent {
  karkhanaVasuliRecords?: IKarkhanaVasuliRecords;

  constructor(
    protected karkhanaVasuliRecordsService: KarkhanaVasuliRecordsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.karkhanaVasuliRecordsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
