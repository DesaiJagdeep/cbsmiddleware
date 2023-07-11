import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TalukaMasterComponent } from './list/taluka-master.component';
import { TalukaMasterDetailComponent } from './detail/taluka-master-detail.component';
import { TalukaMasterUpdateComponent } from './update/taluka-master-update.component';
import { TalukaMasterDeleteDialogComponent } from './delete/taluka-master-delete-dialog.component';
import { TalukaMasterRoutingModule } from './route/taluka-master-routing.module';

@NgModule({
  imports: [SharedModule, TalukaMasterRoutingModule],
  declarations: [TalukaMasterComponent, TalukaMasterDetailComponent, TalukaMasterUpdateComponent, TalukaMasterDeleteDialogComponent],
})
export class TalukaMasterModule {}
