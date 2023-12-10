import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';

@Component({
  standalone: true,
  selector: 'jhi-karkhana-vasuli-file-detail',
  templateUrl: './karkhana-vasuli-file-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class KarkhanaVasuliFileDetailComponent {
  @Input() karkhanaVasuliFile: IKarkhanaVasuliFile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
