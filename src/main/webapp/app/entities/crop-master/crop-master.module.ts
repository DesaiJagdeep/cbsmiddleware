import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CbsMiddlewareSharedModule } from '../../shared';
import {
    CropMasterService,
    CropMasterPopupService,
    CropMasterComponent,
    CropMasterDetailComponent,
    CropMasterDialogComponent,
    CropMasterPopupComponent,
    CropMasterDeletePopupComponent,
    CropMasterDeleteDialogComponent,
    cropMasterRoute,
    cropMasterPopupRoute,
    CropMasterResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...cropMasterRoute,
    ...cropMasterPopupRoute,
];

@NgModule({
    imports: [
        CbsMiddlewareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CropMasterComponent,
        CropMasterDetailComponent,
        CropMasterDialogComponent,
        CropMasterDeleteDialogComponent,
        CropMasterPopupComponent,
        CropMasterDeletePopupComponent,
    ],
    entryComponents: [
        CropMasterComponent,
        CropMasterDialogComponent,
        CropMasterPopupComponent,
        CropMasterDeleteDialogComponent,
        CropMasterDeletePopupComponent,
    ],
    providers: [
        CropMasterService,
        CropMasterPopupService,
        CropMasterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CbsMiddlewareCropMasterModule {}
