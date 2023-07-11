import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AccountHolderMasterComponent } from './list/account-holder-master.component';
import { AccountHolderMasterDetailComponent } from './detail/account-holder-master-detail.component';
import { AccountHolderMasterUpdateComponent } from './update/account-holder-master-update.component';
import { AccountHolderMasterDeleteDialogComponent } from './delete/account-holder-master-delete-dialog.component';
import { AccountHolderMasterRoutingModule } from './route/account-holder-master-routing.module';

@NgModule({
  imports: [SharedModule, AccountHolderMasterRoutingModule],
  declarations: [
    AccountHolderMasterComponent,
    AccountHolderMasterDetailComponent,
    AccountHolderMasterUpdateComponent,
    AccountHolderMasterDeleteDialogComponent,
  ],
})
export class AccountHolderMasterModule {}
