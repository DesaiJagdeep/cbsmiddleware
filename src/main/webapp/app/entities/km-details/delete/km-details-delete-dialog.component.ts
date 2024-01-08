import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKmDetails } from '../km-details.model';
import { KmDetailsService } from '../service/km-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './km-details-delete-dialog.component.html',
})
export class KmDetailsDeleteDialogComponent {
  kmDetails?: IKmDetails;

  constructor(protected kmDetailsService: KmDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
