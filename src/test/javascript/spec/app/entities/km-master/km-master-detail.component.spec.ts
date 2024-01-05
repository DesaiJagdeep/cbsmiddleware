/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CbsMiddlewareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KmMasterDetailComponent } from '../../../../../../main/webapp/app/entities/km-master/km-master-detail.component';
import { KmMasterService } from '../../../../../../main/webapp/app/entities/km-master/km-master.service';
import { KmMaster } from '../../../../../../main/webapp/app/entities/km-master/km-master.model';

describe('Component Tests', () => {

    describe('KmMaster Management Detail Component', () => {
        let comp: KmMasterDetailComponent;
        let fixture: ComponentFixture<KmMasterDetailComponent>;
        let service: KmMasterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CbsMiddlewareTestModule],
                declarations: [KmMasterDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KmMasterService,
                    JhiEventManager
                ]
            }).overrideTemplate(KmMasterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KmMasterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KmMasterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new KmMaster(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.kmMaster).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
