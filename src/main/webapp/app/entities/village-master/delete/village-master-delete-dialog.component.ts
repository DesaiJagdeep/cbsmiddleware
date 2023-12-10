import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVillageMaster } from '../village-master.model';
import { VillageMasterService } from '../service/village-master.service';

@Component({
  standalone: true,
  templateUrl: './village-master-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VillageMasterDeleteDialogComponent {
  villageMaster?: IVillageMaster;

  constructor(
    protected villageMasterService: VillageMasterService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.villageMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
