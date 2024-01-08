import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { KmDetailsComponent } from './km-details.component';
import { KmDetailsDetailComponent } from './km-details-detail.component';
import { KmDetailsPopupComponent } from './km-details-dialog.component';
import { KmDetailsDeletePopupComponent } from './km-details-delete-dialog.component';

@Injectable()
export class KmDetailsResolvePagingParams implements Resolve<any> {

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

export const kmDetailsRoute: Routes = [
    {
        path: 'km-details',
        component: KmDetailsComponent,
        resolve: {
            'pagingParams': KmDetailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'km-details/:id',
        component: KmDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kmDetailsPopupRoute: Routes = [
    {
        path: 'km-details-new',
        component: KmDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'km-details/:id/edit',
        component: KmDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'km-details/:id/delete',
        component: KmDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
