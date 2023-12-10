import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFactoryMaster } from '../factory-master.model';
import { FactoryMasterService } from '../service/factory-master.service';

@Component({
  standalone: true,
  templateUrl: './factory-master-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FactoryMasterDeleteDialogComponent {
  factoryMaster?: IFactoryMaster;

  constructor(
    protected factoryMasterService: FactoryMasterService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.factoryMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
