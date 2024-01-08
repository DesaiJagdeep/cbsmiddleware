import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFactoryMaster } from '../factory-master.model';
import { FactoryMasterService } from '../service/factory-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './factory-master-delete-dialog.component.html',
})
export class FactoryMasterDeleteDialogComponent {
  factoryMaster?: IFactoryMaster;

  constructor(protected factoryMasterService: FactoryMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.factoryMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
