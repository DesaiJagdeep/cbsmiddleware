import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKmMagani } from '../km-magani.model';
import { KmMaganiService } from '../service/km-magani.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './km-magani-delete-dialog.component.html',
})
export class KmMaganiDeleteDialogComponent {
  kmMagani?: IKmMagani;

  constructor(protected kmMaganiService: KmMaganiService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmMaganiService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
