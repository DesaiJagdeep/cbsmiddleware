import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKMPUpload } from '../kmp-upload.model';
import { KMPUploadService } from '../service/kmp-upload.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './kmp-upload-delete-dialog.component.html',
})
export class KMPUploadDeleteDialogComponent {
  kMPUpload?: IKMPUpload;

  constructor(protected kMPUploadService: KMPUploadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kMPUploadService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
