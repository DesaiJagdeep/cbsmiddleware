import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CbsMiddlewareSharedModule } from '../../shared';
import {
    KmCropsService,
    KmCropsPopupService,
    KmCropsComponent,
    KmCropsDetailComponent,
    KmCropsDialogComponent,
    KmCropsPopupComponent,
    KmCropsDeletePopupComponent,
    KmCropsDeleteDialogComponent,
    kmCropsRoute,
    kmCropsPopupRoute,
    KmCropsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...kmCropsRoute,
    ...kmCropsPopupRoute,
];

@NgModule({
    imports: [
        CbsMiddlewareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KmCropsComponent,
        KmCropsDetailComponent,
        KmCropsDialogComponent,
        KmCropsDeleteDialogComponent,
        KmCropsPopupComponent,
        KmCropsDeletePopupComponent,
    ],
    entryComponents: [
        KmCropsComponent,
        KmCropsDialogComponent,
        KmCropsPopupComponent,
        KmCropsDeleteDialogComponent,
        KmCropsDeletePopupComponent,
    ],
    providers: [
        KmCropsService,
        KmCropsPopupService,
        KmCropsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CbsMiddlewareKmCropsModule {}
