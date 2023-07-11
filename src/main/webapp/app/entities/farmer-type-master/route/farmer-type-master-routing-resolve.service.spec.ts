import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IFarmerTypeMaster } from '../farmer-type-master.model';
import { FarmerTypeMasterService } from '../service/farmer-type-master.service';

import { FarmerTypeMasterRoutingResolveService } from './farmer-type-master-routing-resolve.service';

describe('FarmerTypeMaster routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FarmerTypeMasterRoutingResolveService;
  let service: FarmerTypeMasterService;
  let resultFarmerTypeMaster: IFarmerTypeMaster | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(FarmerTypeMasterRoutingResolveService);
    service = TestBed.inject(FarmerTypeMasterService);
    resultFarmerTypeMaster = undefined;
  });

  describe('resolve', () => {
    it('should return IFarmerTypeMaster returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFarmerTypeMaster = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFarmerTypeMaster).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFarmerTypeMaster = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFarmerTypeMaster).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFarmerTypeMaster>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFarmerTypeMaster = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFarmerTypeMaster).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
