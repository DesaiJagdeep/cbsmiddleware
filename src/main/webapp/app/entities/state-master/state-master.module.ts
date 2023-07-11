import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StateMasterComponent } from './list/state-master.component';
import { StateMasterDetailComponent } from './detail/state-master-detail.component';
import { StateMasterUpdateComponent } from './update/state-master-update.component';
import { StateMasterDeleteDialogComponent } from './delete/state-master-delete-dialog.component';
import { StateMasterRoutingModule } from './route/state-master-routing.module';

@NgModule({
  imports: [SharedModule, StateMasterRoutingModule],
  declarations: [StateMasterComponent, StateMasterDetailComponent, StateMasterUpdateComponent, StateMasterDeleteDialogComponent],
})
export class StateMasterModule {}
