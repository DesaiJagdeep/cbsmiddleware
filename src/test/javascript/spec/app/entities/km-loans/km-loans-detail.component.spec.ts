/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CbsMiddlewareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KmLoansDetailComponent } from '../../../../../../main/webapp/app/entities/km-loans/km-loans-detail.component';
import { KmLoansService } from '../../../../../../main/webapp/app/entities/km-loans/km-loans.service';
import { KmLoans } from '../../../../../../main/webapp/app/entities/km-loans/km-loans.model';

describe('Component Tests', () => {

    describe('KmLoans Management Detail Component', () => {
        let comp: KmLoansDetailComponent;
        let fixture: ComponentFixture<KmLoansDetailComponent>;
        let service: KmLoansService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CbsMiddlewareTestModule],
                declarations: [KmLoansDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KmLoansService,
                    JhiEventManager
                ]
            }).overrideTemplate(KmLoansDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KmLoansDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KmLoansService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new KmLoans(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.kmLoans).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
