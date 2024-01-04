import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CropMasterComponent } from './list/crop-master.component';
import { CropMasterDetailComponent } from './detail/crop-master-detail.component';
import { CropMasterUpdateComponent } from './update/crop-master-update.component';
import CropMasterResolve from './route/crop-master-routing-resolve.service';

const cropMasterRoute: Routes = [
  {
    path: '',
    component: CropMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CropMasterDetailComponent,
    resolve: {
      cropMaster: CropMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CropMasterUpdateComponent,
    resolve: {
      cropMaster: CropMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CropMasterUpdateComponent,
    resolve: {
      cropMaster: CropMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cropMasterRoute;
