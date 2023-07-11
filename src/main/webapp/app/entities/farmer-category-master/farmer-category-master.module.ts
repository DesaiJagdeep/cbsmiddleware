import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FarmerCategoryMasterComponent } from './list/farmer-category-master.component';
import { FarmerCategoryMasterDetailComponent } from './detail/farmer-category-master-detail.component';
import { FarmerCategoryMasterUpdateComponent } from './update/farmer-category-master-update.component';
import { FarmerCategoryMasterDeleteDialogComponent } from './delete/farmer-category-master-delete-dialog.component';
import { FarmerCategoryMasterRoutingModule } from './route/farmer-category-master-routing.module';

@NgModule({
  imports: [SharedModule, FarmerCategoryMasterRoutingModule],
  declarations: [
    FarmerCategoryMasterComponent,
    FarmerCategoryMasterDetailComponent,
    FarmerCategoryMasterUpdateComponent,
    FarmerCategoryMasterDeleteDialogComponent,
  ],
})
export class FarmerCategoryMasterModule {}
