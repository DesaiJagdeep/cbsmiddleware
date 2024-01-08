import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KmLoans } from './km-loans.model';
import { KmLoansPopupService } from './km-loans-popup.service';
import { KmLoansService } from './km-loans.service';
import { KmDetails, KmDetailsService } from '../km-details';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-km-loans-dialog',
    templateUrl: './km-loans-dialog.component.html'
})
export class KmLoansDialogComponent implements OnInit {

    kmLoans: KmLoans;
    isSaving: boolean;

    kmdetails: KmDetails[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private kmLoansService: KmLoansService,
        private kmDetailsService: KmDetailsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.kmDetailsService.query()
            .subscribe((res: ResponseWrapper) => { this.kmdetails = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.kmLoans.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kmLoansService.update(this.kmLoans));
        } else {
            this.subscribeToSaveResponse(
                this.kmLoansService.create(this.kmLoans));
        }
    }

    private subscribeToSaveResponse(result: Observable<KmLoans>) {
        result.subscribe((res: KmLoans) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: KmLoans) {
        this.eventManager.broadcast({ name: 'kmLoansListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackKmDetailsById(index: number, item: KmDetails) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-km-loans-popup',
    template: ''
})
export class KmLoansPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kmLoansPopupService: KmLoansPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kmLoansPopupService
                    .open(KmLoansDialogComponent as Component, params['id']);
            } else {
                this.kmLoansPopupService
                    .open(KmLoansDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
