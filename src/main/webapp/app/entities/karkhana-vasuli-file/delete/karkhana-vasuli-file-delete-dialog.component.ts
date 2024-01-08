import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from '../service/karkhana-vasuli-file.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './karkhana-vasuli-file-delete-dialog.component.html',
})
export class KarkhanaVasuliFileDeleteDialogComponent {
  karkhanaVasuliFile?: IKarkhanaVasuliFile;

  constructor(protected karkhanaVasuliFileService: KarkhanaVasuliFileService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.karkhanaVasuliFileService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
