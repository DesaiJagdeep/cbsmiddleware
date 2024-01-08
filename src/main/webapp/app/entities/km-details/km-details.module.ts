import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CbsMiddlewareSharedModule } from '../../shared';
import {
    KmDetailsService,
    KmDetailsPopupService,
    KmDetailsComponent,
    KmDetailsDetailComponent,
    KmDetailsDialogComponent,
    KmDetailsPopupComponent,
    KmDetailsDeletePopupComponent,
    KmDetailsDeleteDialogComponent,
    kmDetailsRoute,
    kmDetailsPopupRoute,
    KmDetailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...kmDetailsRoute,
    ...kmDetailsPopupRoute,
];

@NgModule({
    imports: [
        CbsMiddlewareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KmDetailsComponent,
        KmDetailsDetailComponent,
        KmDetailsDialogComponent,
        KmDetailsDeleteDialogComponent,
        KmDetailsPopupComponent,
        KmDetailsDeletePopupComponent,
    ],
    entryComponents: [
        KmDetailsComponent,
        KmDetailsDialogComponent,
        KmDetailsPopupComponent,
        KmDetailsDeleteDialogComponent,
        KmDetailsDeletePopupComponent,
    ],
    providers: [
        KmDetailsService,
        KmDetailsPopupService,
        KmDetailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CbsMiddlewareKmDetailsModule {}
