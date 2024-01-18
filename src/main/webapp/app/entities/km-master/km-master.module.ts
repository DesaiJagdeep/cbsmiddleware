import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KmMasterComponent } from './list/km-master.component';
import { KmMasterDetailComponent } from './detail/km-master-detail.component';
import { KmMasterUpdateComponent } from './update/km-master-update.component';
import { KmMasterDeleteDialogComponent } from './delete/km-master-delete-dialog.component';
import { KmMasterRoutingModule } from './route/km-master-routing.module';

@NgModule({
  imports: [SharedModule, KmMasterRoutingModule],
  declarations: [KmMasterComponent, KmMasterDetailComponent, KmMasterUpdateComponent, KmMasterDeleteDialogComponent],
})
export class KmMasterModule {}
