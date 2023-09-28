import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KamalMaryadaPatrakComponent } from './list/kamal-maryada-patrak.component';
import { KamalMaryadaPatrakDetailComponent } from './detail/kamal-maryada-patrak-detail.component';
import { KamalMaryadaPatrakUpdateComponent } from './update/kamal-maryada-patrak-update.component';
import { KamalMaryadaPatrakDeleteDialogComponent } from './delete/kamal-maryada-patrak-delete-dialog.component';
import { KamalMaryadaPatrakRoutingModule } from './route/kamal-maryada-patrak-routing.module';

@NgModule({
  imports: [SharedModule, KamalMaryadaPatrakRoutingModule],
  declarations: [
    KamalMaryadaPatrakComponent,
    KamalMaryadaPatrakDetailComponent,
    KamalMaryadaPatrakUpdateComponent,
    KamalMaryadaPatrakDeleteDialogComponent,
  ],
})
export class KamalMaryadaPatrakModule {}
