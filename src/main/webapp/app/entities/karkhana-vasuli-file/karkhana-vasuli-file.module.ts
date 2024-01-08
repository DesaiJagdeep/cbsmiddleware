import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KarkhanaVasuliFileComponent } from './list/karkhana-vasuli-file.component';
import { KarkhanaVasuliFileDetailComponent } from './detail/karkhana-vasuli-file-detail.component';
import { KarkhanaVasuliFileUpdateComponent } from './update/karkhana-vasuli-file-update.component';
import { KarkhanaVasuliFileDeleteDialogComponent } from './delete/karkhana-vasuli-file-delete-dialog.component';
import { KarkhanaVasuliFileRoutingModule } from './route/karkhana-vasuli-file-routing.module';

@NgModule({
  imports: [SharedModule, KarkhanaVasuliFileRoutingModule],
  declarations: [
    KarkhanaVasuliFileComponent,
    KarkhanaVasuliFileDetailComponent,
    KarkhanaVasuliFileUpdateComponent,
    KarkhanaVasuliFileDeleteDialogComponent,
  ],
})
export class KarkhanaVasuliFileModule {}
