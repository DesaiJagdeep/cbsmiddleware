import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CbsMiddlewareSharedModule } from '../../shared';
import {
    KmMasterService,
    KmMasterPopupService,
    KmMasterComponent,
    KmMasterDetailComponent,
    KmMasterDialogComponent,
    KmMasterPopupComponent,
    KmMasterDeletePopupComponent,
    KmMasterDeleteDialogComponent,
    kmMasterRoute,
    kmMasterPopupRoute,
    KmMasterResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...kmMasterRoute,
    ...kmMasterPopupRoute,
];

@NgModule({
    imports: [
        CbsMiddlewareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KmMasterComponent,
        KmMasterDetailComponent,
        KmMasterDialogComponent,
        KmMasterDeleteDialogComponent,
        KmMasterPopupComponent,
        KmMasterDeletePopupComponent,
    ],
    entryComponents: [
        KmMasterComponent,
        KmMasterDialogComponent,
        KmMasterPopupComponent,
        KmMasterDeleteDialogComponent,
        KmMasterDeletePopupComponent,
    ],
    providers: [
        KmMasterService,
        KmMasterPopupService,
        KmMasterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CbsMiddlewareKmMasterModule {}
