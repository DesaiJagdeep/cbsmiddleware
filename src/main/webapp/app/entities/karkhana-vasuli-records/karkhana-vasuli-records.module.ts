import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KarkhanaVasuliRecordsComponent } from './list/karkhana-vasuli-records.component';
import { KarkhanaVasuliRecordsDetailComponent } from './detail/karkhana-vasuli-records-detail.component';
import { KarkhanaVasuliRecordsUpdateComponent } from './update/karkhana-vasuli-records-update.component';
import { KarkhanaVasuliRecordsDeleteDialogComponent } from './delete/karkhana-vasuli-records-delete-dialog.component';
import { KarkhanaVasuliRecordsRoutingModule } from './route/karkhana-vasuli-records-routing.module';

@NgModule({
  imports: [SharedModule, KarkhanaVasuliRecordsRoutingModule],
  declarations: [
    KarkhanaVasuliRecordsComponent,
    KarkhanaVasuliRecordsDetailComponent,
    KarkhanaVasuliRecordsUpdateComponent,
    KarkhanaVasuliRecordsDeleteDialogComponent,
  ],
})
export class KarkhanaVasuliRecordsModule {}
