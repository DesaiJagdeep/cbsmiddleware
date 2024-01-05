/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CbsMiddlewareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CropMasterDetailComponent } from '../../../../../../main/webapp/app/entities/crop-master/crop-master-detail.component';
import { CropMasterService } from '../../../../../../main/webapp/app/entities/crop-master/crop-master.service';
import { CropMaster } from '../../../../../../main/webapp/app/entities/crop-master/crop-master.model';

describe('Component Tests', () => {

    describe('CropMaster Management Detail Component', () => {
        let comp: CropMasterDetailComponent;
        let fixture: ComponentFixture<CropMasterDetailComponent>;
        let service: CropMasterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CbsMiddlewareTestModule],
                declarations: [CropMasterDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CropMasterService,
                    JhiEventManager
                ]
            }).overrideTemplate(CropMasterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CropMasterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CropMasterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CropMaster(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cropMaster).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
