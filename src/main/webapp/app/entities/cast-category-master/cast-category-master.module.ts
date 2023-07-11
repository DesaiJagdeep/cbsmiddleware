import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CastCategoryMasterComponent } from './list/cast-category-master.component';
import { CastCategoryMasterDetailComponent } from './detail/cast-category-master-detail.component';
import { CastCategoryMasterUpdateComponent } from './update/cast-category-master-update.component';
import { CastCategoryMasterDeleteDialogComponent } from './delete/cast-category-master-delete-dialog.component';
import { CastCategoryMasterRoutingModule } from './route/cast-category-master-routing.module';

@NgModule({
  imports: [SharedModule, CastCategoryMasterRoutingModule],
  declarations: [
    CastCategoryMasterComponent,
    CastCategoryMasterDetailComponent,
    CastCategoryMasterUpdateComponent,
    CastCategoryMasterDeleteDialogComponent,
  ],
})
export class CastCategoryMasterModule {}
