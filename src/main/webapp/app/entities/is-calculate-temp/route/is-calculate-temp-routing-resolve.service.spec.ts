import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IIsCalculateTemp } from '../is-calculate-temp.model';
import { IsCalculateTempService } from '../service/is-calculate-temp.service';

import { IsCalculateTempRoutingResolveService } from './is-calculate-temp-routing-resolve.service';

describe('IsCalculateTemp routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: IsCalculateTempRoutingResolveService;
  let service: IsCalculateTempService;
  let resultIsCalculateTemp: IIsCalculateTemp | null | undefined;

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
    routingResolveService = TestBed.inject(IsCalculateTempRoutingResolveService);
    service = TestBed.inject(IsCalculateTempService);
    resultIsCalculateTemp = undefined;
  });

  describe('resolve', () => {
    it('should return IIsCalculateTemp returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIsCalculateTemp = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIsCalculateTemp).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIsCalculateTemp = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultIsCalculateTemp).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IIsCalculateTemp>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIsCalculateTemp = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIsCalculateTemp).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
