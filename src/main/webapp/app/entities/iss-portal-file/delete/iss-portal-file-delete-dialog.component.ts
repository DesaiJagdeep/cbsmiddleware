import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssPortalFile } from '../iss-portal-file.model';
import { IssPortalFileService } from '../service/iss-portal-file.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './iss-portal-file-delete-dialog.component.html',
})
export class IssPortalFileDeleteDialogComponent {
  issPortalFile?: IIssPortalFile;

  constructor(protected issPortalFileService: IssPortalFileService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issPortalFileService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
