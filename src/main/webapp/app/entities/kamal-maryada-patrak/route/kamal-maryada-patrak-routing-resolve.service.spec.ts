import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';
import { KamalMaryadaPatrakService } from '../service/kamal-maryada-patrak.service';

import { KamalMaryadaPatrakRoutingResolveService } from './kamal-maryada-patrak-routing-resolve.service';

describe('KamalMaryadaPatrak routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: KamalMaryadaPatrakRoutingResolveService;
  let service: KamalMaryadaPatrakService;
  let resultKamalMaryadaPatrak: IKamalMaryadaPatrak | null | undefined;

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
    routingResolveService = TestBed.inject(KamalMaryadaPatrakRoutingResolveService);
    service = TestBed.inject(KamalMaryadaPatrakService);
    resultKamalMaryadaPatrak = undefined;
  });

  describe('resolve', () => {
    it('should return IKamalMaryadaPatrak returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultKamalMaryadaPatrak = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultKamalMaryadaPatrak).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultKamalMaryadaPatrak = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultKamalMaryadaPatrak).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IKamalMaryadaPatrak>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultKamalMaryadaPatrak = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultKamalMaryadaPatrak).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
