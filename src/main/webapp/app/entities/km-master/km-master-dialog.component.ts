import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KmMaster } from './km-master.model';
import { KmMasterPopupService } from './km-master-popup.service';
import { KmMasterService } from './km-master.service';

@Component({
    selector: 'jhi-km-master-dialog',
    templateUrl: './km-master-dialog.component.html'
})
export class KmMasterDialogComponent implements OnInit {

    kmMaster: KmMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private kmMasterService: KmMasterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.kmMaster.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kmMasterService.update(this.kmMaster));
        } else {
            this.subscribeToSaveResponse(
                this.kmMasterService.create(this.kmMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<KmMaster>) {
        result.subscribe((res: KmMaster) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: KmMaster) {
        this.eventManager.broadcast({ name: 'kmMasterListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-km-master-popup',
    template: ''
})
export class KmMasterPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kmMasterPopupService: KmMasterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kmMasterPopupService
                    .open(KmMasterDialogComponent as Component, params['id']);
            } else {
                this.kmMasterPopupService
                    .open(KmMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
