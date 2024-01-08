import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { KmLoansComponent } from './km-loans.component';
import { KmLoansDetailComponent } from './km-loans-detail.component';
import { KmLoansPopupComponent } from './km-loans-dialog.component';
import { KmLoansDeletePopupComponent } from './km-loans-delete-dialog.component';

@Injectable()
export class KmLoansResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const kmLoansRoute: Routes = [
    {
        path: 'km-loans',
        component: KmLoansComponent,
        resolve: {
            'pagingParams': KmLoansResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmLoans.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'km-loans/:id',
        component: KmLoansDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmLoans.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kmLoansPopupRoute: Routes = [
    {
        path: 'km-loans-new',
        component: KmLoansPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmLoans.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'km-loans/:id/edit',
        component: KmLoansPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmLoans.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'km-loans/:id/delete',
        component: KmLoansDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmLoans.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
