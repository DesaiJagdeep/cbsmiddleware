import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CbsMiddlewareSharedModule } from '../../shared';
import {
    KmLoansService,
    KmLoansPopupService,
    KmLoansComponent,
    KmLoansDetailComponent,
    KmLoansDialogComponent,
    KmLoansPopupComponent,
    KmLoansDeletePopupComponent,
    KmLoansDeleteDialogComponent,
    kmLoansRoute,
    kmLoansPopupRoute,
    KmLoansResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...kmLoansRoute,
    ...kmLoansPopupRoute,
];

@NgModule({
    imports: [
        CbsMiddlewareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KmLoansComponent,
        KmLoansDetailComponent,
        KmLoansDialogComponent,
        KmLoansDeleteDialogComponent,
        KmLoansPopupComponent,
        KmLoansDeletePopupComponent,
    ],
    entryComponents: [
        KmLoansComponent,
        KmLoansDialogComponent,
        KmLoansPopupComponent,
        KmLoansDeleteDialogComponent,
        KmLoansDeletePopupComponent,
    ],
    providers: [
        KmLoansService,
        KmLoansPopupService,
        KmLoansResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CbsMiddlewareKmLoansModule {}
