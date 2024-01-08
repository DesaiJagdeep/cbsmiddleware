import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VillageMasterComponent } from './list/village-master.component';
import { VillageMasterDetailComponent } from './detail/village-master-detail.component';
import { VillageMasterUpdateComponent } from './update/village-master-update.component';
import { VillageMasterDeleteDialogComponent } from './delete/village-master-delete-dialog.component';
import { VillageMasterRoutingModule } from './route/village-master-routing.module';

@NgModule({
  imports: [SharedModule, VillageMasterRoutingModule],
  declarations: [VillageMasterComponent, VillageMasterDetailComponent, VillageMasterUpdateComponent, VillageMasterDeleteDialogComponent],
})
export class VillageMasterModule {}
