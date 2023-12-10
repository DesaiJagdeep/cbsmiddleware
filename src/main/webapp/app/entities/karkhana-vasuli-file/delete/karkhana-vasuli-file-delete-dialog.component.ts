import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from '../service/karkhana-vasuli-file.service';

@Component({
  standalone: true,
  templateUrl: './karkhana-vasuli-file-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class KarkhanaVasuliFileDeleteDialogComponent {
  karkhanaVasuliFile?: IKarkhanaVasuliFile;

  constructor(
    protected karkhanaVasuliFileService: KarkhanaVasuliFileService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.karkhanaVasuliFileService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
