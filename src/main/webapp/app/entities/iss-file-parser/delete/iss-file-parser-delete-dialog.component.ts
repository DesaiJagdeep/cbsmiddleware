import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssFileParser } from '../iss-file-parser.model';
import { IssFileParserService } from '../service/iss-file-parser.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './iss-file-parser-delete-dialog.component.html',
})
export class IssFileParserDeleteDialogComponent {
  issFileParser?: IIssFileParser;

  constructor(protected issFileParserService: IssFileParserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issFileParserService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
