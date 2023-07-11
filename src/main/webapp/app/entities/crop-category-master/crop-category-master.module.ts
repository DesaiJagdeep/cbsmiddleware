import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CropCategoryMasterComponent } from './list/crop-category-master.component';
import { CropCategoryMasterDetailComponent } from './detail/crop-category-master-detail.component';
import { CropCategoryMasterUpdateComponent } from './update/crop-category-master-update.component';
import { CropCategoryMasterDeleteDialogComponent } from './delete/crop-category-master-delete-dialog.component';
import { CropCategoryMasterRoutingModule } from './route/crop-category-master-routing.module';

@NgModule({
  imports: [SharedModule, CropCategoryMasterRoutingModule],
  declarations: [
    CropCategoryMasterComponent,
    CropCategoryMasterDetailComponent,
    CropCategoryMasterUpdateComponent,
    CropCategoryMasterDeleteDialogComponent,
  ],
})
export class CropCategoryMasterModule {}
