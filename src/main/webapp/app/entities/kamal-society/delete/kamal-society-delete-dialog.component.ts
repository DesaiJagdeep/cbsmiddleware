import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKamalSociety } from '../kamal-society.model';
import { KamalSocietyService } from '../service/kamal-society.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './kamal-society-delete-dialog.component.html',
})
export class KamalSocietyDeleteDialogComponent {
  kamalSociety?: IKamalSociety;

  constructor(protected kamalSocietyService: KamalSocietyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kamalSocietyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
