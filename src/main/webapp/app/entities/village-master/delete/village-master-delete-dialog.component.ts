import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVillageMaster } from '../village-master.model';
import { VillageMasterService } from '../service/village-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './village-master-delete-dialog.component.html',
})
export class VillageMasterDeleteDialogComponent {
  villageMaster?: IVillageMaster;

  constructor(protected villageMasterService: VillageMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.villageMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
