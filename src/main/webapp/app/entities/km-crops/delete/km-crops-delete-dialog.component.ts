import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKmCrops } from '../km-crops.model';
import { KmCropsService } from '../service/km-crops.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './km-crops-delete-dialog.component.html',
})
export class KmCropsDeleteDialogComponent {
  kmCrops?: IKmCrops;

  constructor(protected kmCropsService: KmCropsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmCropsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
