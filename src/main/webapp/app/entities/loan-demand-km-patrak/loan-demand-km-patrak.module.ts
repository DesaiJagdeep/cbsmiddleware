import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanDemandKMPatrakComponent } from './list/loan-demand-km-patrak.component';
import { LoanDemandKMPatrakDetailComponent } from './detail/loan-demand-km-patrak-detail.component';
import { LoanDemandKMPatrakUpdateComponent } from './update/loan-demand-km-patrak-update.component';
import { LoanDemandKMPatrakDeleteDialogComponent } from './delete/loan-demand-km-patrak-delete-dialog.component';
import { LoanDemandKMPatrakRoutingModule } from './route/loan-demand-km-patrak-routing.module';

@NgModule({
  imports: [SharedModule, LoanDemandKMPatrakRoutingModule],
  declarations: [
    LoanDemandKMPatrakComponent,
    LoanDemandKMPatrakDetailComponent,
    LoanDemandKMPatrakUpdateComponent,
    LoanDemandKMPatrakDeleteDialogComponent,
  ],
})
export class LoanDemandKMPatrakModule {}
