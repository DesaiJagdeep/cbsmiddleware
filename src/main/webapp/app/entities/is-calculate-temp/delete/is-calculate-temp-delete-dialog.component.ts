import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIsCalculateTemp } from '../is-calculate-temp.model';
import { IsCalculateTempService } from '../service/is-calculate-temp.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './is-calculate-temp-delete-dialog.component.html',
})
export class IsCalculateTempDeleteDialogComponent {
  isCalculateTemp?: IIsCalculateTemp;

  constructor(protected isCalculateTempService: IsCalculateTempService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.isCalculateTempService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
