import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BankMasterComponent } from './list/bank-master.component';
import { BankMasterDetailComponent } from './detail/bank-master-detail.component';
import { BankMasterUpdateComponent } from './update/bank-master-update.component';
import { BankMasterDeleteDialogComponent } from './delete/bank-master-delete-dialog.component';
import { BankMasterRoutingModule } from './route/bank-master-routing.module';

@NgModule({
  imports: [SharedModule, BankMasterRoutingModule],
  declarations: [BankMasterComponent, BankMasterDetailComponent, BankMasterUpdateComponent, BankMasterDeleteDialogComponent],
})
export class BankMasterModule {}
