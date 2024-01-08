import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KmLoans } from './km-loans.model';
import { KmLoansPopupService } from './km-loans-popup.service';
import { KmLoansService } from './km-loans.service';

@Component({
    selector: 'jhi-km-loans-delete-dialog',
    templateUrl: './km-loans-delete-dialog.component.html'
})
export class KmLoansDeleteDialogComponent {

    kmLoans: KmLoans;

    constructor(
        private kmLoansService: KmLoansService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kmLoansService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kmLoansListModification',
                content: 'Deleted an kmLoans'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-km-loans-delete-popup',
    template: ''
})
export class KmLoansDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kmLoansPopupService: KmLoansPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kmLoansPopupService
                .open(KmLoansDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
