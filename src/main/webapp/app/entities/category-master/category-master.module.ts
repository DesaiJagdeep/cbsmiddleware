import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategoryMasterComponent } from './list/category-master.component';
import { CategoryMasterDetailComponent } from './detail/category-master-detail.component';
import { CategoryMasterUpdateComponent } from './update/category-master-update.component';
import { CategoryMasterDeleteDialogComponent } from './delete/category-master-delete-dialog.component';
import { CategoryMasterRoutingModule } from './route/category-master-routing.module';

@NgModule({
  imports: [SharedModule, CategoryMasterRoutingModule],
  declarations: [
    CategoryMasterComponent,
    CategoryMasterDetailComponent,
    CategoryMasterUpdateComponent,
    CategoryMasterDeleteDialogComponent,
  ],
})
export class CategoryMasterModule {}
