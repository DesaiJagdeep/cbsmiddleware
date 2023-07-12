import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ApplicationLogHistoryComponent } from './list/application-log-history.component';
import { ApplicationLogHistoryDetailComponent } from './detail/application-log-history-detail.component';
import { ApplicationLogHistoryUpdateComponent } from './update/application-log-history-update.component';
import { ApplicationLogHistoryDeleteDialogComponent } from './delete/application-log-history-delete-dialog.component';
import { ApplicationLogHistoryRoutingModule } from './route/application-log-history-routing.module';

@NgModule({
  imports: [SharedModule, ApplicationLogHistoryRoutingModule],
  declarations: [
    ApplicationLogHistoryComponent,
    ApplicationLogHistoryDetailComponent,
    ApplicationLogHistoryUpdateComponent,
    ApplicationLogHistoryDeleteDialogComponent,
  ],
})
export class ApplicationLogHistoryModule {}
