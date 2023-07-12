import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ApplicationLogComponent } from './list/application-log.component';
import { ApplicationLogDetailComponent } from './detail/application-log-detail.component';
import { ApplicationLogUpdateComponent } from './update/application-log-update.component';
import { ApplicationLogDeleteDialogComponent } from './delete/application-log-delete-dialog.component';
import { ApplicationLogRoutingModule } from './route/application-log-routing.module';

@NgModule({
  imports: [SharedModule, ApplicationLogRoutingModule],
  declarations: [
    ApplicationLogComponent,
    ApplicationLogDetailComponent,
    ApplicationLogUpdateComponent,
    ApplicationLogDeleteDialogComponent,
  ],
})
export class ApplicationLogModule {}
