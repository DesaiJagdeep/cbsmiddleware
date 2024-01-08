/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CbsMiddlewareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KmDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/km-details/km-details-detail.component';
import { KmDetailsService } from '../../../../../../main/webapp/app/entities/km-details/km-details.service';
import { KmDetails } from '../../../../../../main/webapp/app/entities/km-details/km-details.model';

describe('Component Tests', () => {

    describe('KmDetails Management Detail Component', () => {
        let comp: KmDetailsDetailComponent;
        let fixture: ComponentFixture<KmDetailsDetailComponent>;
        let service: KmDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CbsMiddlewareTestModule],
                declarations: [KmDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KmDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(KmDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KmDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KmDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new KmDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.kmDetails).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
