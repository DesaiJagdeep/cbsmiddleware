import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistrictMaster } from '../district-master.model';
import { DistrictMasterService } from '../service/district-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './district-master-delete-dialog.component.html',
})
export class DistrictMasterDeleteDialogComponent {
  districtMaster?: IDistrictMaster;

  constructor(protected districtMasterService: DistrictMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.districtMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
