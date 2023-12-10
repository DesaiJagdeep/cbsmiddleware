import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';

@Component({
  standalone: true,
  selector: 'jhi-karkhana-vasuli-records-detail',
  templateUrl: './karkhana-vasuli-records-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class KarkhanaVasuliRecordsDetailComponent {
  @Input() karkhanaVasuliRecords: IKarkhanaVasuliRecords | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
