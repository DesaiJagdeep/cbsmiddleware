import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KmCrops } from './km-crops.model';
import { KmCropsPopupService } from './km-crops-popup.service';
import { KmCropsService } from './km-crops.service';
import { KmDetails, KmDetailsService } from '../km-details';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-km-crops-dialog',
    templateUrl: './km-crops-dialog.component.html'
})
export class KmCropsDialogComponent implements OnInit {

    kmCrops: KmCrops;
    isSaving: boolean;

    kmdetails: KmDetails[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private kmCropsService: KmCropsService,
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
        if (this.kmCrops.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kmCropsService.update(this.kmCrops));
        } else {
            this.subscribeToSaveResponse(
                this.kmCropsService.create(this.kmCrops));
        }
    }

    private subscribeToSaveResponse(result: Observable<KmCrops>) {
        result.subscribe((res: KmCrops) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: KmCrops) {
        this.eventManager.broadcast({ name: 'kmCropsListModification', content: 'OK'});
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
    selector: 'jhi-km-crops-popup',
    template: ''
})
export class KmCropsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kmCropsPopupService: KmCropsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kmCropsPopupService
                    .open(KmCropsDialogComponent as Component, params['id']);
            } else {
                this.kmCropsPopupService
                    .open(KmCropsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
