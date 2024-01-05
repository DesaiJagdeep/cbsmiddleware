import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CropMasterComponent } from './crop-master.component';
import { CropMasterDetailComponent } from './crop-master-detail.component';
import { CropMasterPopupComponent } from './crop-master-dialog.component';
import { CropMasterDeletePopupComponent } from './crop-master-delete-dialog.component';

@Injectable()
export class CropMasterResolvePagingParams implements Resolve<any> {

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

export const cropMasterRoute: Routes = [
    {
        path: 'crop-master',
        component: CropMasterComponent,
        resolve: {
            'pagingParams': CropMasterResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.cropMaster.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'crop-master/:id',
        component: CropMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.cropMaster.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cropMasterPopupRoute: Routes = [
    {
        path: 'crop-master-new',
        component: CropMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.cropMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'crop-master/:id/edit',
        component: CropMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.cropMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'crop-master/:id/delete',
        component: CropMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cbsMiddlewareApp.cropMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
