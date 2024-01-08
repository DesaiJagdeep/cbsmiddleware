import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { KmCropsComponent } from './km-crops.component';
import { KmCropsDetailComponent } from './km-crops-detail.component';
import { KmCropsPopupComponent } from './km-crops-dialog.component';
import { KmCropsDeletePopupComponent } from './km-crops-delete-dialog.component';

@Injectable()
export class KmCropsResolvePagingParams implements Resolve<any> {

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

export const kmCropsRoute: Routes = [
    {
        path: 'km-crops',
        component: KmCropsComponent,
        resolve: {
            'pagingParams': KmCropsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmCrops.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'km-crops/:id',
        component: KmCropsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmCrops.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kmCropsPopupRoute: Routes = [
    {
        path: 'km-crops-new',
        component: KmCropsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmCrops.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'km-crops/:id/edit',
        component: KmCropsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmCrops.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'km-crops/:id/delete',
        component: KmCropsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmCrops.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
