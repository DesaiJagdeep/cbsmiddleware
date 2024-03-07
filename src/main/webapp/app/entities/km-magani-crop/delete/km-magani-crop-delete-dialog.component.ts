import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKmMaganiCrop } from '../km-magani-crop.model';
import { KmMaganiCropService } from '../service/km-magani-crop.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './km-magani-crop-delete-dialog.component.html',
})
export class KmMaganiCropDeleteDialogComponent {
  kmMaganiCrop?: IKmMaganiCrop;

  constructor(protected kmMaganiCropService: KmMaganiCropService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmMaganiCropService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
