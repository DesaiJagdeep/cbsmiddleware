import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IActivityType } from '../activity-type.model';
import { ActivityTypeService } from '../service/activity-type.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './activity-type-delete-dialog.component.html',
})
export class ActivityTypeDeleteDialogComponent {
  activityType?: IActivityType;

  constructor(protected activityTypeService: ActivityTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activityTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
