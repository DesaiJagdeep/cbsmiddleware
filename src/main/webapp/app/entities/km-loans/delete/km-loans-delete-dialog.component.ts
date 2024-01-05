import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IKmLoans } from '../km-loans.model';
import { KmLoansService } from '../service/km-loans.service';

@Component({
  standalone: true,
  templateUrl: './km-loans-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class KmLoansDeleteDialogComponent {
  kmLoans?: IKmLoans;

  constructor(
    protected kmLoansService: KmLoansService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmLoansService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
