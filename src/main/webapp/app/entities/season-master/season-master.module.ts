import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SeasonMasterComponent } from './list/season-master.component';
import { SeasonMasterDetailComponent } from './detail/season-master-detail.component';
import { SeasonMasterUpdateComponent } from './update/season-master-update.component';
import { SeasonMasterDeleteDialogComponent } from './delete/season-master-delete-dialog.component';
import { SeasonMasterRoutingModule } from './route/season-master-routing.module';

@NgModule({
  imports: [SharedModule, SeasonMasterRoutingModule],
  declarations: [SeasonMasterComponent, SeasonMasterDetailComponent, SeasonMasterUpdateComponent, SeasonMasterDeleteDialogComponent],
})
export class SeasonMasterModule {}
