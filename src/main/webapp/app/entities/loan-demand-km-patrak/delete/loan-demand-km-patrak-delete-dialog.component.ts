import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanDemandKMPatrak } from '../loan-demand-km-patrak.model';
import { LoanDemandKMPatrakService } from '../service/loan-demand-km-patrak.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-demand-km-patrak-delete-dialog.component.html',
})
export class LoanDemandKMPatrakDeleteDialogComponent {
  loanDemandKMPatrak?: ILoanDemandKMPatrak;

  constructor(protected loanDemandKMPatrakService: LoanDemandKMPatrakService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanDemandKMPatrakService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
