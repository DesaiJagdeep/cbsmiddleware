import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from '../service/karkhana-vasuli-file.service';

import { KarkhanaVasuliFileRoutingResolveService } from './karkhana-vasuli-file-routing-resolve.service';

describe('KarkhanaVasuliFile routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: KarkhanaVasuliFileRoutingResolveService;
  let service: KarkhanaVasuliFileService;
  let resultKarkhanaVasuliFile: IKarkhanaVasuliFile | null | undefined;

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
    routingResolveService = TestBed.inject(KarkhanaVasuliFileRoutingResolveService);
    service = TestBed.inject(KarkhanaVasuliFileService);
    resultKarkhanaVasuliFile = undefined;
  });

  describe('resolve', () => {
    it('should return IKarkhanaVasuliFile returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultKarkhanaVasuliFile = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultKarkhanaVasuliFile).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultKarkhanaVasuliFile = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultKarkhanaVasuliFile).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IKarkhanaVasuliFile>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultKarkhanaVasuliFile = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultKarkhanaVasuliFile).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
