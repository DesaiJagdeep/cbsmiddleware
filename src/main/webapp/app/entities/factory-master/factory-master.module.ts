import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FactoryMasterComponent } from './list/factory-master.component';
import { FactoryMasterDetailComponent } from './detail/factory-master-detail.component';
import { FactoryMasterUpdateComponent } from './update/factory-master-update.component';
import { FactoryMasterDeleteDialogComponent } from './delete/factory-master-delete-dialog.component';
import { FactoryMasterRoutingModule } from './route/factory-master-routing.module';

@NgModule({
  imports: [SharedModule, FactoryMasterRoutingModule],
  declarations: [FactoryMasterComponent, FactoryMasterDetailComponent, FactoryMasterUpdateComponent, FactoryMasterDeleteDialogComponent],
})
export class FactoryMasterModule {}
