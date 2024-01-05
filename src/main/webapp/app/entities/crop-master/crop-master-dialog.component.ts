import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CropMaster } from './crop-master.model';
import { CropMasterPopupService } from './crop-master-popup.service';
import { CropMasterService } from './crop-master.service';

@Component({
    selector: 'jhi-crop-master-dialog',
    templateUrl: './crop-master-dialog.component.html'
})
export class CropMasterDialogComponent implements OnInit {

    cropMaster: CropMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private cropMasterService: CropMasterService,
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
        if (this.cropMaster.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cropMasterService.update(this.cropMaster));
        } else {
            this.subscribeToSaveResponse(
                this.cropMasterService.create(this.cropMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<CropMaster>) {
        result.subscribe((res: CropMaster) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CropMaster) {
        this.eventManager.broadcast({ name: 'cropMasterListModification', content: 'OK'});
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
    selector: 'jhi-crop-master-popup',
    template: ''
})
export class CropMasterPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cropMasterPopupService: CropMasterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cropMasterPopupService
                    .open(CropMasterDialogComponent as Component, params['id']);
            } else {
                this.cropMasterPopupService
                    .open(CropMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
