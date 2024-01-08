import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IKmMaster } from '../km-master.model';
import { KmMasterService } from '../service/km-master.service';

@Component({
  standalone: true,
  templateUrl: './km-master-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class KmMasterDeleteDialogComponent {
  kmMaster?: IKmMaster;

  constructor(
    protected kmMasterService: KmMasterService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
