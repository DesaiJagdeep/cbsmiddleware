import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KmLoansComponent } from './list/km-loans.component';
import { KmLoansDetailComponent } from './detail/km-loans-detail.component';
import { KmLoansUpdateComponent } from './update/km-loans-update.component';
import { KmLoansDeleteDialogComponent } from './delete/km-loans-delete-dialog.component';
import { KmLoansRoutingModule } from './route/km-loans-routing.module';

@NgModule({
  imports: [SharedModule, KmLoansRoutingModule],
  declarations: [KmLoansComponent, KmLoansDetailComponent, KmLoansUpdateComponent, KmLoansDeleteDialogComponent],
})
export class KmLoansModule {}
