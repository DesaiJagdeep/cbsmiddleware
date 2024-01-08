import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KmDetails } from './km-details.model';
import { KmDetailsPopupService } from './km-details-popup.service';
import { KmDetailsService } from './km-details.service';
import { KmMaster, KmMasterService } from '../km-master';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-km-details-dialog',
    templateUrl: './km-details-dialog.component.html'
})
export class KmDetailsDialogComponent implements OnInit {

    kmDetails: KmDetails;
    isSaving: boolean;

    kmmasters: KmMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private kmDetailsService: KmDetailsService,
        private kmMasterService: KmMasterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.kmMasterService
            .query({filter: 'kmdetails-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.kmDetails.kmMaster || !this.kmDetails.kmMaster.id) {
                    this.kmmasters = res.json;
                } else {
                    this.kmMasterService
                        .find(this.kmDetails.kmMaster.id)
                        .subscribe((subRes: KmMaster) => {
                            this.kmmasters = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.kmDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kmDetailsService.update(this.kmDetails));
        } else {
            this.subscribeToSaveResponse(
                this.kmDetailsService.create(this.kmDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<KmDetails>) {
        result.subscribe((res: KmDetails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: KmDetails) {
        this.eventManager.broadcast({ name: 'kmDetailsListModification', content: 'OK'});
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

    trackKmMasterById(index: number, item: KmMaster) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-km-details-popup',
    template: ''
})
export class KmDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kmDetailsPopupService: KmDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kmDetailsPopupService
                    .open(KmDetailsDialogComponent as Component, params['id']);
            } else {
                this.kmDetailsPopupService
                    .open(KmDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
