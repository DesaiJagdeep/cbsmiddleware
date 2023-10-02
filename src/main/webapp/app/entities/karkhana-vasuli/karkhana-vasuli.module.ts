import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KarkhanaVasuliComponent } from './list/karkhana-vasuli.component';
import { KarkhanaVasuliDetailComponent } from './detail/karkhana-vasuli-detail.component';
import { KarkhanaVasuliUpdateComponent } from './update/karkhana-vasuli-update.component';
import { KarkhanaVasuliDeleteDialogComponent } from './delete/karkhana-vasuli-delete-dialog.component';
import { KarkhanaVasuliRoutingModule } from './route/karkhana-vasuli-routing.module';

@NgModule({
  imports: [SharedModule, KarkhanaVasuliRoutingModule],
  declarations: [
    KarkhanaVasuliComponent,
    KarkhanaVasuliDetailComponent,
    KarkhanaVasuliUpdateComponent,
    KarkhanaVasuliDeleteDialogComponent,
  ],
})
export class KarkhanaVasuliModule {}
