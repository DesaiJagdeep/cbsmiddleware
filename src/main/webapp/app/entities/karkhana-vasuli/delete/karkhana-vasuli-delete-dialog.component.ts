import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKarkhanaVasuli } from '../karkhana-vasuli.model';
import { KarkhanaVasuliService } from '../service/karkhana-vasuli.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './karkhana-vasuli-delete-dialog.component.html',
})
export class KarkhanaVasuliDeleteDialogComponent {
  karkhanaVasuli?: IKarkhanaVasuli;

  constructor(protected karkhanaVasuliService: KarkhanaVasuliService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.karkhanaVasuliService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
