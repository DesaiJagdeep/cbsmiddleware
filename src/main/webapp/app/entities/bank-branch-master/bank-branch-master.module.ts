import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BankBranchMasterComponent } from './list/bank-branch-master.component';
import { BankBranchMasterDetailComponent } from './detail/bank-branch-master-detail.component';
import { BankBranchMasterUpdateComponent } from './update/bank-branch-master-update.component';
import { BankBranchMasterDeleteDialogComponent } from './delete/bank-branch-master-delete-dialog.component';
import { BankBranchMasterRoutingModule } from './route/bank-branch-master-routing.module';

@NgModule({
  imports: [SharedModule, BankBranchMasterRoutingModule],
  declarations: [
    BankBranchMasterComponent,
    BankBranchMasterDetailComponent,
    BankBranchMasterUpdateComponent,
    BankBranchMasterDeleteDialogComponent,
  ],
})
export class BankBranchMasterModule {}
