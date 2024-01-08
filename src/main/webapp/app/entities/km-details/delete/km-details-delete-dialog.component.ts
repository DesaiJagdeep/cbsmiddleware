import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IKmDetails } from '../km-details.model';
import { KmDetailsService } from '../service/km-details.service';

@Component({
  standalone: true,
  templateUrl: './km-details-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class KmDetailsDeleteDialogComponent {
  kmDetails?: IKmDetails;

  constructor(
    protected kmDetailsService: KmDetailsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
