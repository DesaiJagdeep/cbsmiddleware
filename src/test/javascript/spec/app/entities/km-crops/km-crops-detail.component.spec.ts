/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CbsMiddlewareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KmCropsDetailComponent } from '../../../../../../main/webapp/app/entities/km-crops/km-crops-detail.component';
import { KmCropsService } from '../../../../../../main/webapp/app/entities/km-crops/km-crops.service';
import { KmCrops } from '../../../../../../main/webapp/app/entities/km-crops/km-crops.model';

describe('Component Tests', () => {

    describe('KmCrops Management Detail Component', () => {
        let comp: KmCropsDetailComponent;
        let fixture: ComponentFixture<KmCropsDetailComponent>;
        let service: KmCropsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CbsMiddlewareTestModule],
                declarations: [KmCropsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KmCropsService,
                    JhiEventManager
                ]
            }).overrideTemplate(KmCropsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KmCropsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KmCropsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new KmCrops(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.kmCrops).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
