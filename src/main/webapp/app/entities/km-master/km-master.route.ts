import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { KmMasterComponent } from './km-master.component';
import { KmMasterDetailComponent } from './km-master-detail.component';
import { KmMasterPopupComponent } from './km-master-dialog.component';
import { KmMasterDeletePopupComponent } from './km-master-delete-dialog.component';

@Injectable()
export class KmMasterResolvePagingParams implements Resolve<any> {

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

export const kmMasterRoute: Routes = [
    {
        path: 'km-master',
        component: KmMasterComponent,
        resolve: {
            'pagingParams': KmMasterResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmMaster.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'km-master/:id',
        component: KmMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmMaster.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kmMasterPopupRoute: Routes = [
    {
        path: 'km-master-new',
        component: KmMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'km-master/:id/edit',
        component: KmMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'km-master/:id/delete',
        component: KmMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.kmMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
