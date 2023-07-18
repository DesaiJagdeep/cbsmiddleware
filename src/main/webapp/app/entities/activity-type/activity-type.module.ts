import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ActivityTypeComponent } from './list/activity-type.component';
import { ActivityTypeDetailComponent } from './detail/activity-type-detail.component';
import { ActivityTypeUpdateComponent } from './update/activity-type-update.component';
import { ActivityTypeDeleteDialogComponent } from './delete/activity-type-delete-dialog.component';
import { ActivityTypeRoutingModule } from './route/activity-type-routing.module';

@NgModule({
  imports: [SharedModule, ActivityTypeRoutingModule],
  declarations: [ActivityTypeComponent, ActivityTypeDetailComponent, ActivityTypeUpdateComponent, ActivityTypeDeleteDialogComponent],
})
export class ActivityTypeModule {}
