import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RelativeMasterComponent } from './list/relative-master.component';
import { RelativeMasterDetailComponent } from './detail/relative-master-detail.component';
import { RelativeMasterUpdateComponent } from './update/relative-master-update.component';
import { RelativeMasterDeleteDialogComponent } from './delete/relative-master-delete-dialog.component';
import { RelativeMasterRoutingModule } from './route/relative-master-routing.module';

@NgModule({
  imports: [SharedModule, RelativeMasterRoutingModule],
  declarations: [
    RelativeMasterComponent,
    RelativeMasterDetailComponent,
    RelativeMasterUpdateComponent,
    RelativeMasterDeleteDialogComponent,
  ],
})
export class RelativeMasterModule {}
