import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';
import { KamalMaryadaPatrakService } from '../service/kamal-maryada-patrak.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './kamal-maryada-patrak-delete-dialog.component.html',
})
export class KamalMaryadaPatrakDeleteDialogComponent {
  kamalMaryadaPatrak?: IKamalMaryadaPatrak;

  constructor(protected kamalMaryadaPatrakService: KamalMaryadaPatrakService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kamalMaryadaPatrakService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
