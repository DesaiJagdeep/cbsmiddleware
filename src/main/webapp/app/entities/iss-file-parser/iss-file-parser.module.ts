import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssFileParserComponent } from './list/iss-file-parser.component';
import { IssFileParserDetailComponent } from './detail/iss-file-parser-detail.component';
import { IssFileParserUpdateComponent } from './update/iss-file-parser-update.component';
import { IssFileParserDeleteDialogComponent } from './delete/iss-file-parser-delete-dialog.component';
import { IssFileParserRoutingModule } from './route/iss-file-parser-routing.module';

@NgModule({
  imports: [SharedModule, IssFileParserRoutingModule],
  declarations: [IssFileParserComponent, IssFileParserDetailComponent, IssFileParserUpdateComponent, IssFileParserDeleteDialogComponent],
})
export class IssFileParserModule {}
