import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KamalSocietyComponent } from './list/kamal-society.component';
import { KamalSocietyDetailComponent } from './detail/kamal-society-detail.component';
import { KamalSocietyUpdateComponent } from './update/kamal-society-update.component';
import { KamalSocietyDeleteDialogComponent } from './delete/kamal-society-delete-dialog.component';
import { KamalSocietyRoutingModule } from './route/kamal-society-routing.module';

@NgModule({
  imports: [SharedModule, KamalSocietyRoutingModule],
  declarations: [KamalSocietyComponent, KamalSocietyDetailComponent, KamalSocietyUpdateComponent, KamalSocietyDeleteDialogComponent],
})
export class KamalSocietyModule {}
