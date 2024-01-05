import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IKmCrops } from '../km-crops.model';
import { KmCropsService } from '../service/km-crops.service';

@Component({
  standalone: true,
  templateUrl: './km-crops-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class KmCropsDeleteDialogComponent {
  kmCrops?: IKmCrops;

  constructor(
    protected kmCropsService: KmCropsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmCropsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
